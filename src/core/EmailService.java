/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package core;

import java.util.Properties;
import javax.mail.Authenticator;
import javax.mail.Message;
import javax.mail.Multipart;
import javax.mail.PasswordAuthentication;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeBodyPart;
import javax.mail.internet.MimeMessage;
import javax.mail.internet.MimeMultipart;

/**
 *
 * @author DawidOzog
 */
public class EmailService {

    private String username;
    private String password;
    private String recepient;
    private String msg;
    private String subject;
    private String path;

    private final Properties props;

    public EmailService(String host, int port, String username, String password, String recepient, String msg, String subject, String path) {
        props = new Properties();
        props.put("mail.smtp.starttls.enable", "true");
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.user", username);
        props.put("mail.smtp.password", password);
        props.put("mail.smtp.port", "587");
        props.put("mail.smtp.auth", "true");

        this.username = username;
        this.password = password;
        this.recepient = recepient;
        this.msg = msg;
        this.subject = subject;
        this.path = path;
    }

    public EmailService(String host, int port) {
        props = new Properties();
        props.put("mail.smtp.host", host);
        props.put("mail.smtp.port", port);
    }

    public void sendMail() throws Exception {

        Session session = Session.getInstance(props, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(username, password);
            }
        });

        MimeMessage message = new MimeMessage(session);
        message.setFrom(new InternetAddress("Author"));
        message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(recepient));
        message.setSubject(this.subject, "utf-8");

        String msg = this.msg;

        MimeBodyPart mimeBodyPart = new MimeBodyPart();

        mimeBodyPart.setContent(msg, "text/html; charset=utf-8");


        if (path != null) {

            MimeBodyPart attachmentBodyPart = new MimeBodyPart();

            attachmentBodyPart.attachFile(path);

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            multipart.addBodyPart(attachmentBodyPart);

            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Done");
        } else {

            Multipart multipart = new MimeMultipart();
            multipart.addBodyPart(mimeBodyPart);
            
            message.setContent(multipart);

            Transport.send(message);

            System.out.println("Done");

        }
    }

}
