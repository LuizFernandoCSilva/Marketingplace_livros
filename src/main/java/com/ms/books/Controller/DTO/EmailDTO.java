package com.ms.books.Controller.DTO;

import java.util.UUID;

import lombok.Data;

@Data
public class EmailDTO {
  private UUID bookId;
  private UUID userId;
  private String title;
  private String author;
  private Integer price;
  private String emailTo;
  private String subject;
  private String text;
}
