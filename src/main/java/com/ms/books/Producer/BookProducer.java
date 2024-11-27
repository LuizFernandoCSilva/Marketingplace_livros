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

  @Value("${broker.queue.books.name}")
  private String routingKey;

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
      "Preço: %s\n\n" +
      "Obrigado por usar nossos serviços!\n\n" +
      "Atenciosamente,\n" +
      "Equipe BookStore",
      booksConsumer.getName(), bookItem.getTitle(), bookItem.getAuthor(),
      bookItem.getTitle(), bookItem.getAuthor(), String.valueOf(bookItem.getPrice())
    );
    emailDTO.setText(sentText);
    
    rabbitTemplate.convertAndSend("",routingKey, emailDTO);
  }

}
