package com.ms.user.Producers;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;


import com.ms.user.Controllers.DTO.EmailDto;
import com.ms.user.Entities.UserModel;

import org.springframework.beans.factory.annotation.Value;

@Component
public class UserProducer {

  final RabbitTemplate rabbitTemplate;

  public UserProducer(RabbitTemplate rabbitTemplate) {
    this.rabbitTemplate = rabbitTemplate;
  }

  @Value(value = "${broker.queue.email.name}")
  private String routingKey;

  public void publishMessageEmail(UserModel userModel) {
    var emailDto = new EmailDto();
    emailDto.setUserId(userModel.getUserId());
    emailDto.setEmailTo(userModel.getEmail());
    emailDto.setSubject("Cadastro realizado com sucesso");
    emailDto.setText("Seja bem-vindo ao nosso sistema de cadastro, " + userModel.getName());

    rabbitTemplate.convertAndSend("", routingKey, emailDto);

  }
}
