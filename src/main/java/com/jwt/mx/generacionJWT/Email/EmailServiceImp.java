package com.jwt.mx.generacionJWT.Email;

import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.mail.MailSender;
import org.springframework.mail.javamail.JavaMailSender;

import org.slf4j.Logger;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;


@Service
public class EmailServiceImp implements EmailSender{

    private final static Logger LOGGER = LoggerFactory.getLogger(EmailServiceImp.class);
    @Autowired
    private JavaMailSender emailSender;

    public void send(String to,String email)
    {
        try {
            MimeMessage mimeMessage = emailSender.createMimeMessage();

            MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");

            helper.setText(email, true);
            helper.setText(to);
            helper.setSubject("Confirm your account");
            helper.setFrom("Empresa de donde viene el mensaje");



        }catch (MessagingException e)
        {
            LOGGER.error("Error it could not posible to send the email: "+e);

            throw new IllegalStateException("error to send email");
        }
    }


}

