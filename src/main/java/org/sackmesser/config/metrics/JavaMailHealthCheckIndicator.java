package org.sackmesser.config.metrics;

import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.javamail.JavaMailSenderImpl;

import javax.mail.MessagingException;

/**
 * SpringBoot Actuator HealthIndicator check for JavaMail.
 */
@Slf4j
public class JavaMailHealthCheckIndicator extends HealthCheckIndicator {

    public static final String EMAIL_HEALTH_INDICATOR = "email";

    private JavaMailSenderImpl javaMailSender;

    public JavaMailHealthCheckIndicator() {
    }

    public void setJavaMailSender(JavaMailSenderImpl javaMailSender) {
        this.javaMailSender = javaMailSender;
    }

    @Override
    protected String getHealthCheckIndicatorName() {
        return EMAIL_HEALTH_INDICATOR;
    }

    @Override
    protected Result check() throws Exception {
        log.debug("Initializing JavaMail health indicator");

        try {
            javaMailSender.getSession().getTransport().connect(javaMailSender.getHost(),
                    javaMailSender.getUsername(),
                    javaMailSender.getPassword());

            return healthy();

        } catch (MessagingException e) {
            log.debug("Cannot connect to e-mail server.", e);
            return unhealthy("Cannot connect to e-mail server", e);
        }
    }
}
