package com.proyecto.account.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.FileSystemResource;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;
import org.springframework.util.ResourceUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.FileNotFoundException;

@Component
public class EmailServiceImpl implements EmailService{
    private static final Logger logger = LoggerFactory
            .getLogger(EmailServiceImpl.class);
    //Metodo de envio email simple
    @Autowired
    public JavaMailSender javaMailSender;
    @Override
    public void sendSimpleEmail(String toAddress, String subject, String message) {
        logger.info("Simple Email sending start");
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();

//        SimpleMailMessage simpleMessage = new SimpleMailMessage();
//        simpleMessage.setTo(toAddress);
//        simpleMessage.setSubject( subject +"Spring Boot=> Sending simple email");
//        simpleMessage.setText("Dear Dhirendra, Hope you are doing well." + message);

        //javaMailSender.send(simpleMessage);

        try {

            // Set multipart mime message true
            MimeMessageHelper mimeMessageHelper = new MimeMessageHelper(mimeMessage,
                    true);

            mimeMessageHelper.setTo(toAddress);
            mimeMessageHelper.setSubject(subject + "Spring Boot=> Sending HTML email");

            String html = "<h3>Dear Manish</h3></br>"
                    + "<p>Many many congratulation for joining "
                    + "<strong>Websparrow.org Team</strong>.</p>" + "</br></br>"
                    + "<p>You are entitled for <code>Rs.5000</code> "
                    + "as joning bonus.</p></br>" + message;
            mimeMessageHelper.setText(html, true);

            javaMailSender.send(mimeMessage);

        } catch (MessagingException e) {
            logger.error("Exeception=>sendHTMLEmail ", e);
        }

        logger.info("HTML email sent");

    }

    //Adjunto
    @Override
    public void sendEmailWithAttachment(String toAddress, String subject, String message, String attachment) throws MessagingException, FileNotFoundException {
        MimeMessage mimeMessage = javaMailSender.createMimeMessage();
        MimeMessageHelper messageHelper = new MimeMessageHelper(mimeMessage, true);
        messageHelper.setTo(toAddress);
        messageHelper.setSubject(subject);
        messageHelper.setText(message);
        FileSystemResource file = new FileSystemResource(ResourceUtils.getFile(attachment));
        messageHelper.addAttachment("Purchase Order", file);
        javaMailSender.send(mimeMessage);
    }
}
