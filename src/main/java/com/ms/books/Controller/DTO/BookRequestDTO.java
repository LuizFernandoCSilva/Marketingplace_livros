package com.ms.books.Controller.DTO;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class BookRequestDTO {
  
  @NotBlank
  private String title;

  @NotBlank
  private String author;

  @NotBlank
  private String genre;

  @NotBlank
  private String description;

  @NotBlank
  private String language;

  @NotBlank
  private int publicationYear;

  @NotBlank
  private int price;
}
