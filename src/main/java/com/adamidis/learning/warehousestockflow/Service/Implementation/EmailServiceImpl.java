package com.adamidis.learning.warehousestockflow.Service.Implementation;

import com.adamidis.learning.warehousestockflow.Enum.VerificationType;
import com.adamidis.learning.warehousestockflow.Exception.ApiException;
import com.adamidis.learning.warehousestockflow.Service.EmailService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
@Slf4j
public class EmailServiceImpl implements EmailService {
    private final JavaMailSender mailSender;

    @Override
    public void sendVerificationEmail(String firstName, String email, String verificationUrl, VerificationType verificationType) {
        try {
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom("${MAIL_USERNAME}");
            message.setTo(email);
            message.setText(getEmailMessage(firstName, verificationUrl, verificationType));
            message.setSubject(String.format("Warehouse StockFlow - %s Verification Email", StringUtils.capitalize(verificationType.getType())));
            mailSender.send(message);
            log.info("Verification email sent to {}", firstName);
        } catch (Exception exception) {
            log.error(exception.getMessage());
        }
    }

    private String getEmailMessage(String firstName, String verificationUrl, VerificationType verificationType) {
        switch (verificationType) {
            case PASSWORD -> { return  "Hello " + firstName + "\n\nReset password request. Please click the link below to reset your password. \n\n" + verificationUrl + "\n\nThe Support Team";}
            case ACCOUNT -> { return  "Hello " + firstName + "\n\nYour new account has been created. Please click the link below to verify your account. \n\n" + verificationUrl + "\n\nThe Support Team";}
            default -> throw new ApiException("Unable to send verification email. Email type unknown.");
        }
    }
}
