package com.ms.books.Consumers;

import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.ms.books.Controller.DTO.BookRecordDTO;

import java.util.UUID;

@Component
public class BooksConsumer {

    private UUID userId; // Para armazenar temporariamente o userId recebido
    private String emailTo; // Para armazenar temporariamente o emailTo recebido
    private String name;

    @RabbitListener(queues = "${broker.queue.books.name}")
    public void ListenBookQueue(@Payload  BookRecordDTO bookRecordDTO) {
        System.out.println("Mensagem recebida: " + bookRecordDTO);
        this.userId = bookRecordDTO.userId();
        this.emailTo = bookRecordDTO.emailTo();
        this.name = bookRecordDTO.name();
    }

    public UUID getUserId() {
        return userId;
    }

    public void setUserId(UUID userId) {
        this.userId = userId;
    }

    public String getEmailTo() {
        return emailTo;
    }

    public void setEmailTo(String emailTo) {
        this.emailTo = emailTo;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}