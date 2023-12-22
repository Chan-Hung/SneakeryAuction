package com.hung.sneakery.service;

import com.hung.sneakery.entity.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface MailService {

    /**
     * Send Verification mail
     *
     * @param user             User
     * @param verificationCode String
     * @throws MessagingException           MessagingException
     * @throws UnsupportedEncodingException UnsupportedEncodingException
     */
    void sendVerificationEmail(User user, String verificationCode) throws MessagingException, UnsupportedEncodingException;
}

