package com.ms.books.Repositories;

import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.books.Entities.BookItem;


public interface BooksRepository extends JpaRepository<BookItem, UUID> {
   
}
