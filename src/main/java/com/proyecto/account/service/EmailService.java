package com.proyecto.account.service;

import javax.mail.MessagingException;
import java.io.FileNotFoundException;

public interface EmailService {
    //Envio de email simple
    void sendSimpleEmail(final String toAddress, final String subject, final String message);
    //Adjunto
    void sendEmailWithAttachment(final String toAddress, final String subject, final String message, final String attachment) throws MessagingException, FileNotFoundException;

}
