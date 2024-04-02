package com.hung.sneakery.service.impl;

import com.hung.sneakery.entity.Product;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.service.MailService;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.util.ResourceUtils;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.File;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.nio.file.Files;

@Service
public class MailServiceImpl implements MailService {

    @Resource
    private JavaMailSender mailSender;

    @Override
    public void sendRemindBidderEmail(User user, Product product) throws MessagingException, IOException {
        String fromAddress = "sneakeryauction@gmail.com";
        String senderName = "Shopping-Platform";
        String subject = "[Lời nhắc] Đấu giá của bạn đang diễn ra";
        String templatePath = "classpath:email-templates/reminder.html";

        String content = readEmailTemplate(templatePath);
        String productLink = "https://sneakery.vercel.app/products/" + product.getId();
        content = content.replace("[[URL]]", productLink);
        content = content.replace("[[NAME]]", user.getUsername());

        sendEmail(user.getEmail(), fromAddress, senderName, subject, content);
    }

    private String readEmailTemplate(String templatePath) throws IOException {
        File file = ResourceUtils.getFile(templatePath);
        return new String(Files.readAllBytes(file.toPath()));
    }

    private void sendEmail(String toAddress, String fromAddress, String senderName, String subject, String content) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom(fromAddress, senderName);
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);

        mailSender.send(message);
    }
}
