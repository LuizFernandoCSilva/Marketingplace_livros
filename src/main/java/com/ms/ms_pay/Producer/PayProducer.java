package com.ms.ms_pay.Producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ms.ms_pay.Consumers.PayConsumer;
import com.ms.ms_pay.Controllers.DTO.EmailDTO;

@Component
public class PayProducer {

  @Autowired PayConsumer payConsumer;
  
  @Autowired
  private RabbitTemplate rabbitTemplate;

  @Value("${broker.queue.pay-email.name}")
  private String routingKey;

  public void sendEmail(){
    var emailDTO = new EmailDTO();
    emailDTO.setPrice(payConsumer.getPrice());
    emailDTO.setName(payConsumer.getName());
    emailDTO.setTitle(payConsumer.getTitle());
    emailDTO.setEmailTo(payConsumer.getEmailTo());
    emailDTO.setSubject("Compra realizada com sucesso!");
    emailDTO.setText("Olá " + emailDTO.getName() + ",\n\n" +
             "Sua compra do livro \"" + emailDTO.getTitle() + "\" foi realizada com sucesso!\n" +
             "O valor total da compra foi de R$" + emailDTO.getPrice() + ",00.\n\n" +
             "Obrigado por comprar conosco!\n\n" +
             "Atenciosamente,\n" +
             "Equipe de vendas da Bookstore.");
    rabbitTemplate.convertAndSend("",routingKey, emailDTO);
  }
}
