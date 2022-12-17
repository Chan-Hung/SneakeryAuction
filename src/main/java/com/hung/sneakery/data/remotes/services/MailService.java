package com.hung.sneakery.data.remotes.services;

import com.hung.sneakery.data.models.entities.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface MailService {
    void sendVerificationEmail(User user, String verificationCode) throws MessagingException, UnsupportedEncodingException;
}

