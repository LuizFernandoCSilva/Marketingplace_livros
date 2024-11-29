package com.ms.books.Producer;

import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import com.ms.books.Consumers.BooksConsumer;
import com.ms.books.Controller.DTO.EmailDTO;
import com.ms.books.Entities.BookItem;

@Component
public class BookProducer {
  
  @Autowired 
  private RabbitTemplate rabbitTemplate;

  @Autowired BooksConsumer booksConsumer;

  @Value("${broker.queue.books-email.name}")
  private String routingKey;

  @Value("${broker.queue.pay.name}")
  private String routingKeyPay;

  public void sendPriceToPay(BookItem bookItem) {
    rabbitTemplate.convertAndSend("", routingKeyPay, bookItem.getPrice());
  }

  public void publishMessageEmailAdd(BookItem bookItem) {
  var emailDTO = new EmailDTO();
  emailDTO.setBookId(bookItem.getId());
  emailDTO.setUserId(booksConsumer.getUserId());
  emailDTO.setTitle(bookItem.getTitle());
  emailDTO.setAuthor(bookItem.getAuthor());
  emailDTO.setPrice(String.valueOf(bookItem.getPrice()));
  emailDTO.setEmailTo(booksConsumer.getEmailTo());
  emailDTO.setSubject("Livro " + bookItem.getTitle());
  emailDTO.setText("O livro " + bookItem.getTitle() + " foi adicionado com sucesso para venda.");
  String sentText = String.format(
    "Olá %s,\n\n" +
    "Temos o prazer de informar que o livro '%s' de %s foi adicionado com sucesso para venda.\n" +
    "Detalhes do livro:\n" +
    "Título: %s\n" +
    "Autor: %s\n" +
    "Preço: R$ %d \n\n" +
    "Obrigado por usar nossos serviços!\n\n" +
    "Atenciosamente,\n" +
    "Equipe BookStore",
    booksConsumer.getName(), bookItem.getTitle(), bookItem.getAuthor(),
    bookItem.getTitle(), bookItem.getAuthor(), bookItem.getPrice()
  );
  emailDTO.setText(sentText);
  
  rabbitTemplate.convertAndSend("",routingKey, emailDTO);
  }

  public void publishMessageEmailUpdate(BookItem bookItem) {
  var emailDTO = new EmailDTO();
  emailDTO.setBookId(bookItem.getId());
  emailDTO.setUserId(booksConsumer.getUserId());
  emailDTO.setTitle(bookItem.getTitle());
  emailDTO.setAuthor(bookItem.getAuthor());
  emailDTO.setPrice(String.valueOf(bookItem.getPrice()));
  emailDTO.setEmailTo(booksConsumer.getEmailTo());
  emailDTO.setSubject("Atualização do livro: " + bookItem.getTitle());
  
  String sentText = String.format(
    "Olá %s,\n\n" +
    "Informamos que o livro '%s' de %s foi atualizado com sucesso.\n" +
    "Confira os detalhes atualizados do livro:\n" +
    "Título: %s\n" +
    "Autor: %s\n" +
    "Preço: R$ %d\n\n" +
    "Obrigado por acompanhar nossas atualizações!\n\n" +
    "Atenciosamente,\n" +
    "Equipe BookStore",
    booksConsumer.getName(), bookItem.getTitle(), bookItem.getAuthor(),
    bookItem.getTitle(), bookItem.getAuthor(), bookItem.getPrice()
  );
  
  emailDTO.setText(sentText);
  rabbitTemplate.convertAndSend("", routingKey, emailDTO);
  }

  public void publishMessageEmailDelete(BookItem bookItem) {
  var emailDTO = new EmailDTO();
  emailDTO.setBookId(bookItem.getId());
  emailDTO.setUserId(booksConsumer.getUserId());
  emailDTO.setTitle(bookItem.getTitle());
  emailDTO.setAuthor(bookItem.getAuthor());
  emailDTO.setPrice(String.valueOf(bookItem.getPrice()));
  emailDTO.setEmailTo(booksConsumer.getEmailTo());
  emailDTO.setSubject("Livro excluído: " + bookItem.getTitle());

  String sentText = String.format(
    "Olá %s,\n\n" +
    "Lamentamos informar que o livro '%s' de %s foi excluído de nossa plataforma.\n" +
    "Abaixo estão os detalhes do livro excluído:\n" +
    "Título: %s\n" +
    "Autor: %s\n" +
    "Preço: R$ %d\n\n" +
    "Agradecemos por ter acompanhado o livro. Se precisar de assistência, não hesite em nos contatar.\n\n" +
    "Atenciosamente,\n" +
    "Equipe BookStore",
    booksConsumer.getName(), bookItem.getTitle(), bookItem.getAuthor(),
    bookItem.getTitle(), bookItem.getAuthor(), bookItem.getPrice()
  );

  emailDTO.setText(sentText);
  rabbitTemplate.convertAndSend("", routingKey, emailDTO);
  }

}
