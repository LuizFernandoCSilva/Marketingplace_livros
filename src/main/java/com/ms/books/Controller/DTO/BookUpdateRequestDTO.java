package com.ms.books.Controller.DTO;

import lombok.Data;

@Data
public class BookUpdateRequestDTO {
 
  private String title;
  
  private String author;

  private String genre;
 
  private String description;

  private String language;

  private int publicationYear;

  private int price;
}
