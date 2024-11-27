package com.ms.books.Controller.DTO;

import java.util.UUID;

public record BookRecordDTO(UUID userId, String name, String emailTo) {}
