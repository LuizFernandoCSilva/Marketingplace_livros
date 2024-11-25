package com.ms.email.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.email.Models.EmailModel;

public interface EmailRepository extends JpaRepository<EmailModel, UUID> {
  
 
  
}
