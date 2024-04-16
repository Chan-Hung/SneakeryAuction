package com.hung.sneakery.service;

import com.hung.sneakery.entity.Product;
import com.hung.sneakery.entity.User;

import javax.mail.MessagingException;
import java.io.IOException;

public interface MailService {

    /**
     * Send remind bidder email to user
     *
     * @param user         the user
     * @param product      the product
     * @param subject      the subject
     * @param templatePath the template path
     * @throws MessagingException if an error occurs
     * @throws IOException        if an error occurs
     */
    void sendEmail(String subject, String templatePath, User user, Product product) throws MessagingException, IOException;
}

