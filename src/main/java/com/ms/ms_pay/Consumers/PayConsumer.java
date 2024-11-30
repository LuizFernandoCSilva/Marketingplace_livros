package com.ms.ms_pay.Consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.ms_pay.Controllers.DTO.ConsumerBookPayDTO;

@Component
public class PayConsumer {
  
  private Integer price;
  private String name;
  private String title;
  private String emailTo;

  @RabbitListener(queues = "${broker.queue.pay.name}")
  public void receiveMessage(@Payload ConsumerBookPayDTO consumerBookPayDTO) {
    price = consumerBookPayDTO.getPrice();
    name = consumerBookPayDTO.getName();
    title = consumerBookPayDTO.getTitle();
    emailTo = consumerBookPayDTO.getEmailTo();
    System.out.println("Received message: " + consumerBookPayDTO);
  }

  public Integer getPrice() {
    return price;
  }

  public String getName() {
    return name;
  }

  public String getTitle() {
    return title;
  }

  public String getEmailTo() {
    return emailTo;
  }
}
