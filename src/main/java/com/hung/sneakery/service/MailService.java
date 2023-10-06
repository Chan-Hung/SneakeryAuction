package com.hung.sneakery.service;

import com.hung.sneakery.entities.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface MailService {

    void sendVerificationEmail(User user, String verificationCode) throws MessagingException, UnsupportedEncodingException;
}

