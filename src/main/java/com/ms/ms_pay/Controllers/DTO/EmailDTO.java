package com.ms.ms_pay.Controllers.DTO;

import lombok.Data;

@Data
public class EmailDTO {
  private Integer price;
  private String name;
  private String title;
  private String emailTo;
  private String subject;
  private String text;

}
