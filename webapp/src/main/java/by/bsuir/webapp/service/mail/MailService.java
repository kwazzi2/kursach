package by.bsuir.webapp.service.mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;

@Service
public class MailService {
    @Value("${mail.username}")
    String username;

    @Autowired
    private JavaMailSender mailSender;

    public void sendHtml(String emailTo, String subject, String message) throws MessagingException, MailException {
        MimeMessage mimeMessage = mailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(mimeMessage, "utf-8");
        helper.setText(message, true);
        helper.setTo(emailTo);
        helper.setSubject(subject);
        helper.setFrom(username);

        mailSender.send(mimeMessage);
    }
}
