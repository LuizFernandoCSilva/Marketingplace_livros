package com.ms.email.Services;

import java.time.LocalDateTime;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.MailException;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import com.ms.email.Enums.StatusEmail;
import com.ms.email.Models.EmailModel;
import com.ms.email.Repositories.EmailRepository;

import jakarta.transaction.Transactional;


@Service
public class EmailServices {

  final EmailRepository emailRepository;
  final JavaMailSender emailSender;

  public EmailServices(EmailRepository emailRepository, JavaMailSender emailSender) {
    this.emailRepository = emailRepository;
    this.emailSender = emailSender;
  }

  @Value(value = "${spring.mail.username}")
  private String emailFrom;

  @SuppressWarnings("finally")
  @Transactional
  public EmailModel sendEmail(EmailModel emailModel) {
    System.out.println("Enviando email de: " + emailFrom + " para " + emailModel.getEmailTo());
    try {
        emailModel.setSendDateEmail(LocalDateTime.now());
        emailModel.setEmailFrom(emailFrom);

        SimpleMailMessage message = new SimpleMailMessage();
        message.setTo(emailModel.getEmailTo());
        message.setSubject(emailModel.getSubject());
        message.setText(emailModel.getText());
        emailSender.send(message);

        emailModel.setStatusEmail(StatusEmail.SENT);
        System.out.println("E-mail enviado com sucesso para: " + emailModel.getEmailTo());
    } catch (MailException e) {
        e.printStackTrace();  // Log para captura do erro
        emailModel.setStatusEmail(StatusEmail.ERROR);
    } finally {
        return emailRepository.save(emailModel);
    }
  }
  
}
