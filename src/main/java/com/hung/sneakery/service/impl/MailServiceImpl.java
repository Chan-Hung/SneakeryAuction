package com.hung.sneakery.service.impl;

import com.hung.sneakery.entity.Media;
import com.hung.sneakery.entity.Product;
import com.hung.sneakery.entity.User;
import com.hung.sneakery.repository.ProductRepository;
import com.hung.sneakery.service.MailService;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.*;
import java.text.NumberFormat;
import java.util.Locale;

@Service
public class MailServiceImpl implements MailService {
    private static final Logger LOGGER = LoggerFactory.getLogger(MailServiceImpl.class);

    @Resource
    private JavaMailSender mailSender;

    @Resource
    private ProductRepository productRepository;

    @Override
    public void sendEmail(final String subject, final String templatePath, final User user, final Product product, final Long currentPrice) throws MessagingException, IOException {
        String content = readEmailTemplate(templatePath);
        String productLink = "https://sneakery.vercel.app/products/" + product.getId();
        content = content.replace("[[URL]]", productLink);
        content = content.replace("[[NAME]]", user.getUsername());
        content = content.replace("[[PRODUCT_NAME]]", product.getName());
        content = content.replace("[[HOLDER]]", product.getBid().getHolder().getUsername());

        String thumbnailPath = product.getImages().stream()
                .filter(image -> Boolean.TRUE.equals(image.getIsThumbnail()))
                .findFirst()
                .map(Media::getPath)
                .orElse(StringUtils.EMPTY);

        content = content.replace("[[THUMBNAIL_URL]]", thumbnailPath);

        NumberFormat currencyFormatter = NumberFormat.getCurrencyInstance(Locale.US);
        currencyFormatter.setMinimumFractionDigits(0);
        String formattedCurrentPrice = currencyFormatter.format(currentPrice);
        content = content.replace("[[CURRENT_PRICE]]", formattedCurrentPrice);

        sendEmail(subject, user.getEmail(), content);
    }

    private String readEmailTemplate(final String templatePath) throws IOException {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream(templatePath);
        if (inputStream == null) {
            throw new FileNotFoundException("Template file not found: " + templatePath);
        }
        try (BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream))) {
            StringBuilder content = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                content.append(line).append("\n");
            }
            return content.toString();
        } catch (Exception e) {
            LOGGER.error("Error reading email template", e);
            return StringUtils.EMPTY;
        }
    }

    private void sendEmail(final String subject, final String toAddress, final String content) throws MessagingException, UnsupportedEncodingException {
        MimeMessage message = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message);

        helper.setFrom("sneakeryauction@gmail.com", "Shopping-Platform");
        helper.setTo(toAddress);
        helper.setSubject(subject);
        helper.setText(content, true);
        mailSender.send(message);
    }
}
