package com.malynovsky.task.service;

public interface EmailService {

    /**
     * Asynchronously send email with generated token.
     *
     * @param email to
     * @param token token to send
     */
    void sendToken(String email, String token);
}
