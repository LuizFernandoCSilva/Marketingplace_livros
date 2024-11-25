package com.ms.email.Consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.BeanUtils;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.email.DTO.EmailRecordDto;
import com.ms.email.Models.EmailModel;
import com.ms.email.Services.EmailServices;

@Component
public class EmailConsumer {

  final EmailServices emailServices;

  public EmailConsumer(EmailServices emailServices) {
    this.emailServices = emailServices;
  }
  
  @RabbitListener(queues = "${broker.queue.email.name}")
  public void ListenEmailQueue(@Payload EmailRecordDto emailRecordDto) {
    var emailModels = new EmailModel();
    BeanUtils.copyProperties(emailRecordDto, emailModels);

    emailServices.sendEmail(emailModels);
  }
}
