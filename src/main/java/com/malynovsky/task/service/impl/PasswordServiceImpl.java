package com.malynovsky.task.service.impl;

import com.google.common.cache.CacheBuilder;
import com.google.common.cache.CacheLoader;
import com.google.common.cache.LoadingCache;
import com.malynovsky.task.exception.ExpiredPasswordException;
import com.malynovsky.task.exception.UserNotFoundException;
import com.malynovsky.task.service.EmailService;
import com.malynovsky.task.service.PasswordService;
import com.malynovsky.task.service.UserService;
import com.malynovsky.task.util.Constants;
import com.malynovsky.task.util.OtpLimits;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.SignatureAlgorithm;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.AuthorityUtils;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.Random;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

@Service
public class PasswordServiceImpl implements PasswordService {

    private final LoadingCache<String, String> validPasswords = CacheBuilder.newBuilder()
            .concurrencyLevel(4)
            .expireAfterWrite(5, TimeUnit.MINUTES)
            .build(new CacheLoader<>() {
                @Override
                public String load(String otp) {
                    return null;
                }
            });

    private final Logger logger = org.slf4j.LoggerFactory.getLogger(PasswordServiceImpl.class);

    private final UserService userService;
    private final EmailService emailService;
    private final OtpLimits limits;

    @Autowired
    public PasswordServiceImpl(UserService userService, EmailService emailService, OtpLimits limits) {
        this.userService = userService;
        this.emailService = emailService;
        this.limits = limits;
    }

    @Override
    public String getPasswordByEmail(String email) {
        logger.debug("Received request for OTP for {} email", email);
        if (userService.getUserByEmail(email).isPresent()) {
            final String result = generateOTP();
            validPasswords.put(result, email);
            return result;
        }

        throw new UserNotFoundException();
    }

    @Override
    public void generateToken(String otp) {
        Optional<String> email = Optional.ofNullable(validPasswords.getIfPresent(otp));
        if (email.isEmpty()) {
            throw new ExpiredPasswordException();
        }
        emailService.sendToken(email.get(), generateJwtToken(otp));
    }

    private String generateOTP() {
        return new Random().ints(limits.getLeftLimit(), limits.getRightLimit() + 1)
                .limit(limits.getMaxSize())
                .collect(StringBuilder::new, StringBuilder::appendCodePoint, StringBuilder::append)
                .toString();
    }

    private String generateJwtToken(String otp) {
        List<GrantedAuthority> grantedAuthorities = AuthorityUtils
                .commaSeparatedStringToAuthorityList("ROLE_USER");

        return Constants.PREFIX + Jwts
                .builder()
                .setSubject(otp)
                .claim(Constants.AUTHORITIES,
                        grantedAuthorities.stream()
                                .map(GrantedAuthority::getAuthority)
                                .collect(Collectors.toList()))
                .setIssuedAt(new Date(System.currentTimeMillis()))
                .setExpiration(new Date(System.currentTimeMillis() + TimeUnit.MINUTES.toMillis(5)))
                .signWith(SignatureAlgorithm.HS512,
                        Constants.SECRET.getBytes()).compact();
    }
}
