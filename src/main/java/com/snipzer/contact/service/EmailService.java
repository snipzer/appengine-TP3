package com.snipzer.contact.service;

import com.snipzer.contact.entity.Email;
import com.snipzer.contact.util.StringUtil;

import javax.mail.*;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;
import javax.servlet.http.HttpServletRequest;
import java.util.Properties;
import java.util.logging.Logger;

public class EmailService {
    private static Logger LOG = Logger.getLogger(EmailService.class.getName());

    private static EmailService INSTANCE = new EmailService();
    public static EmailService getInstance() {
        return INSTANCE;
    }
    private EmailService() {}

    public void logEmail(HttpServletRequest request) {
        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties, null);
        try{
            MimeMessage message = new MimeMessage(session, request.getInputStream());
            LOG.warning("Subject: " + message.getSubject());
            Multipart multipart = (Multipart) message.getContent();
            BodyPart part = multipart.getBodyPart(0);
            LOG.warning("Body: "+ part.getContent());
            for(Address adresse: message.getFrom()) {
                LOG.warning("From: "+adresse.toString());
            }
        }catch(Exception e) {
            LOG.warning(e.getMessage());
        }
    }

    public void sendEmail(Email email) {
        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties, null);
        try {
            Message msg = new MimeMessage(session);
            msg.setFrom(new InternetAddress(
                    AuthenticationService.getInstance().getUser().getEmail(),
                    AuthenticationService.getInstance().getUsername()));
            msg.addRecipient(Message.RecipientType.TO, new InternetAddress(email.to, email.toName));
            msg.setReplyTo(new Address[] {
                    new InternetAddress(StringUtil.EMAIL_APPLICATION, StringUtil.SNIP_APP)
            });
            msg.setSubject(email.subject);
            msg.setText(email.body);
            Transport.send(msg);
        } catch(Exception e) {
            LOG.warning(e.getMessage());
        }
    }
}
