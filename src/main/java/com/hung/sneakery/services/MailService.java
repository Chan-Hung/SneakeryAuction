package com.hung.sneakery.services;

import com.hung.sneakery.model.User;

import javax.mail.MessagingException;
import java.io.UnsupportedEncodingException;

public interface MailService {
    void sendVerificationEmail(User user) throws MessagingException, UnsupportedEncodingException;
}

