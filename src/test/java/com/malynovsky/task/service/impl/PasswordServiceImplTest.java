package com.malynovsky.task.service.impl;

import com.malynovsky.task.entity.User;
import com.malynovsky.task.exception.ExpiredPasswordException;
import com.malynovsky.task.service.EmailService;
import com.malynovsky.task.service.UserService;
import com.malynovsky.task.util.OtpLimits;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class PasswordServiceImplTest {
    private static final String EMAIL = "random@email";
    private static final String PASS = "somePass";

    @Mock
    private UserService userService;
    @Mock
    private EmailService emailService;
    @Mock
    private OtpLimits limits;

    @InjectMocks
    private PasswordServiceImpl service;

    @Test
    void generateToken_noSuchPassword() {
        assertThrows(ExpiredPasswordException.class, () -> service.generateToken(PASS));
    }

    @Test
    void generateToken_passwordExists() {
        when(userService.getUserByEmail(eq(EMAIL))).thenReturn(Optional.of(new User()));
        when(limits.getLeftLimit()).thenReturn(97);
        when(limits.getRightLimit()).thenReturn(122);
        when(limits.getMaxSize()).thenReturn(7);

        String otp = service.getPasswordByEmail(EMAIL);

        service.generateToken(otp);

        verify(userService, times(1)).getUserByEmail(eq(EMAIL));
        verify(emailService, times(1)).sendToken(eq(EMAIL), anyString());
    }
}