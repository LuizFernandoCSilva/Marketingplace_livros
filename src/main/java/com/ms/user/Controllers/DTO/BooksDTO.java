package com.ms.user.Controllers.DTO;

import java.util.UUID;

import lombok.Data;

@Data
public class BooksDTO {
  private UUID userId;
  private String name;
	private String emailTo;
}
