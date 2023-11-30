package by.bsuir.webapp.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import java.util.Properties;

@Configuration
@ConfigurationProperties(prefix = "mail")
public class MailSenderConfig {
    private String username;
    private String password;
    private String host;
    private String auth;
    @Value("${mail.starttls.enable}")
    private String starttlsEnable;
    private String debug;
    private Integer port;

    @Bean
    public JavaMailSender getJavaMailSenderBean() {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(host);
        mailSender.setPort(port);

        mailSender.setUsername(username);
        mailSender.setPassword(password);

        Properties props = mailSender.getJavaMailProperties();
        props.put("mail.debug", debug);
        props.put("mail.smtp.auth", auth);
        props.put("mail.smtp.starttls.enable",starttlsEnable);
        props.put("mail.smtp.ssl.protocols", "TLSv1.2");
        props.put("mail.smtp.ssl.trust", host);

        return mailSender;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public void setHost(String host) {
        this.host = host;
    }

    public void setAuth(String auth) {
        this.auth = auth;
    }

    public void setStarttlsEnable(String starttlsEnable) {
        this.starttlsEnable = starttlsEnable;
    }

    public void setDebug(String debug) {
        this.debug = debug;
    }

    public void setPort(Integer port) {
        this.port = port;
    }
}
