package com.malynovsky.task.service;

public interface PasswordService {

    /**
     * Returns temporary password for given email. If application could not find such email then error would be thrown.
     *
     * @param email email.
     * @return OTP.
     */
    String getPasswordByEmail(String email);

    /**
     * Generate jwt token for a given otp.
     *
     * @param otp temporary password.
     */
    void generateToken(String otp);
}
