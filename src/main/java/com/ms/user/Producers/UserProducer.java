package com.ms.user.Producers;

import java.util.UUID;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.stereotype.Component;

import com.ms.user.Controllers.DTO.BooksDTO;
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

  @Value(value = "${broker.queue.books.name}")
  private String routingKeyBooks;



  public void publishMessageEmail(UserModel userModel) {
    var emailDto = new EmailDto();
    emailDto.setUserId(userModel.getUserId());
    emailDto.setEmailTo(userModel.getEmail());
    emailDto.setSubject("Cadastro realizado com sucesso");
    emailDto.setText("Olá " + userModel.getName() + ",\n\n" +
             "Seja bem-vindo à Bookstore! Estamos muito felizes em tê-lo conosco.\n" +
             "Seu cadastro foi realizado com sucesso e agora você pode aproveitar todas as vantagens que oferecemos.\n\n" +
             "Atenciosamente,\n" +
             "Equipe Bookstore");

    rabbitTemplate.convertAndSend("", routingKey, emailDto);

  }

  public void sendInfoBooks(UserModel userModel) {
    System.out.println("Enviando informações de livros para o usuário: " + userModel.getName());
    var booksDto = new BooksDTO();
    booksDto.setUserId(userModel.getUserId());
    booksDto.setName(userModel.getName());
    booksDto.setEmailTo(userModel.getEmail());
    System.out.println("Teste: " + booksDto);

    rabbitTemplate.convertAndSend("", routingKeyBooks, booksDto);
  }
  
}
