package com.ms.books.Repositories;

import java.util.List;
import java.util.UUID;

import org.springframework.data.jpa.repository.JpaRepository;

import com.ms.books.Entities.BookItem;


public interface BooksRepository extends JpaRepository<BookItem, UUID> {
  // Método para buscar livros pelo título
  List<BookItem> findByTitle(String title);

  // Método para buscar livros pelo gênero
  List<BookItem> findByGenre(String genre);

  // Método para buscar livros pelo preço
  List<BookItem> findByPrice(Integer  price);

  // Método para buscar livros pelo título e gênero
  List<BookItem> findByTitleAndGenre(String title, String genre);

  // Método para buscar livros pelo título e preço
  List<BookItem> findByTitleAndPrice(String title, Integer  price);

  // Método para buscar livros pelo gênero e preço
  List<BookItem> findByGenreAndPrice(String genre, Integer  price);

  // Método para buscar livros pelo título, gênero e preço
  List<BookItem> findByTitleAndGenreAndPrice(String title, String genre, Integer price);
}
