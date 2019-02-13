package com.snipzer.contact.service;

import javax.mail.Address;
import javax.mail.BodyPart;
import javax.mail.Multipart;
import javax.mail.Session;
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

    public void logEmail(HttpServletRequest request) {
        Properties properties = new Properties();
        Session session = Session.getDefaultInstance(properties, null);
        try{
            MimeMessage message = new MimeMessage(session, request.getInputStream());
            LOG.warning("Subject:" + message.getSubject());

            Multipart multipart = (Multipart) message.getContent();
            BodyPart part = multipart.getBodyPart(0);
            LOG.warning("Body:"+ (String) part.getContent());

            for(Address adresse: message.getFrom()) {
                LOG.warning(adresse.toString());
            }
        }catch(Exception e) {
            LOG.warning(e.getMessage());
        }
    }
}
