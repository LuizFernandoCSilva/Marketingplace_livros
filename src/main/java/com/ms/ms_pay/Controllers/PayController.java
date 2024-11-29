package com.ms.ms_pay.Controllers;

import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RestController;
import java.util.Map;
import org.springframework.beans.factory.annotation.Autowired;
import com.ms.ms_pay.Services.PayService;

@RestController
public class PayController {

  @Autowired
  private PayService payService;

  // Endpoint que cria o PaymentIntent
  @PostMapping("/create-payment-intent")
  public Map<String, Object> createPaymentIntent() {
    // Chama o método do service para criar o PaymentIntent e retorna a resposta
    return payService.createPaymentIntent();
  }
}