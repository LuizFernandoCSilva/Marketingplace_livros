package com.ms.ms_pay.Consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

@Component
public class PayConsumer {
  
  private Integer price;

  @RabbitListener(queues = "${broker.queue.pay.name}")
  public void receiveMessage(@Payload Integer price) {
    this.price = price;
    System.out.println("Received message: " + price);
  }

  public Integer getPrice() {
    return price;
  }
}
