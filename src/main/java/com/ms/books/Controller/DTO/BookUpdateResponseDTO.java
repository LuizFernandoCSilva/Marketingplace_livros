package com.ms.books.Controller.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class BookUpdateResponseDTO {
  
  private String title;
  
  private String author;

  private String genre;
 
  private String description;

  private String language;

  private int publicationYear;

  private int price;
  
}
