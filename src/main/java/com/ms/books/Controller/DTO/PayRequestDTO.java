package com.ms.books.Controller.DTO;

import lombok.Data;

@Data
public class PayRequestDTO {
  
  private Integer price;
  private String name;
  private String title;
  private String emailTo;
}
