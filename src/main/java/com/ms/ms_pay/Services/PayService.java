package com.ms.ms_pay.Services;

import com.ms.ms_pay.Consumers.PayConsumer;
import com.ms.ms_pay.Producer.PayProducer;
import com.stripe.Stripe;
import com.stripe.exception.StripeException;
import com.stripe.model.PaymentIntent;
import com.stripe.param.PaymentIntentCreateParams;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import javax.annotation.PostConstruct;
import java.util.HashMap;
import java.util.Map;

@Service
public class PayService {

  @Autowired
  private PayConsumer payConsumer; // Injetando o PayConsumer

  @Autowired PayProducer payProducer;

  @Value("${stripe.api.key}")
  private String stripeApiKey;

  @PostConstruct
  public void init() {
    // Inicializa a chave da API do Stripe
    Stripe.apiKey = stripeApiKey;
  }

  public Map<String, Object> createPaymentIntent() {
    Map<String, Object> responseData = new HashMap<>();
    try {
        // Obtem o valor de price do PayConsumer
        Integer price = payConsumer.getPrice(); // Obtém o preço recebido do consumer

        if (price == null) {
          responseData.put("error", "Price not received yet.");
          return responseData;
        }

        price = price * 100; // Converte o preço para centavos

        // Cria um PaymentIntent com os parâmetros recebidos
        PaymentIntentCreateParams params = PaymentIntentCreateParams.builder()
            .setAmount(price.longValue()) // Utiliza o valor de price (em centavos)
            .setCurrency("brl") // Moeda, ex: "brl" (pode ser passado por parâmetro, se necessário)
            .addPaymentMethodType("card") // Método de pagamento usando cartão
            .build();

        // Cria o PaymentIntent
        PaymentIntent paymentIntent = PaymentIntent.create(params);

        payProducer.sendEmail(); // Envia o email
        // Retorna o client secret para o front-end
        responseData.put("clientSecret", paymentIntent.getClientSecret());

    } catch (StripeException e) {
        responseData.put("error", e.getMessage()); // Em caso de erro, retorna a mensagem de erro
    }
    return responseData;
  }
}
