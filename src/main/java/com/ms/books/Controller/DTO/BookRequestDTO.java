package com.ms.books.Controller.DTO;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
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

  @NotNull // Use NotNull para tipos numéricos
  private Integer publicationYear;

  @NotNull // Use NotNull para tipos numéricos
  private Integer price;
}
