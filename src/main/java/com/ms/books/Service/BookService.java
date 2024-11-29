package com.ms.books.Service;

import java.util.Date;
import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import com.ms.books.Controller.DTO.BookUpdateRequestDTO;
import com.ms.books.Controller.DTO.BookUpdateResponseDTO;
import com.ms.books.Entities.BookItem;

import com.ms.books.Producer.BookProducer;
import com.ms.books.Repositories.BooksRepository;
import jakarta.transaction.Transactional;

@Service
public class BookService {

    @Autowired
    private BookProducer bookProducer;

    @Autowired
    private BooksRepository booksRepository;

    @Transactional
    public BookItem addBook(BookItem bookItem) {
        if (bookItem == null) {
            throw new IllegalArgumentException("BookItem cannot be null");
        }
        try {
            BookItem book = booksRepository.save(bookItem);
            bookProducer.sendPriceToPay(book);
            bookProducer.publishMessageEmailAdd(book);
            return book; 
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adicionar livro: " + e.getMessage(), e);
        }
    }

    @Transactional
    public Optional<BookUpdateResponseDTO> updateBook(UUID id, BookUpdateRequestDTO bookUpdateRequestDTO) {
        try {
            Optional<BookItem> existingBook = booksRepository.findById(id);

            if (existingBook.isPresent()) {
                BookItem bookToUpdate = existingBook.get();

                // Atualize os campos do livro conforme necessário
                bookToUpdate.setTitle(bookUpdateRequestDTO.getTitle());
                bookToUpdate.setAuthor(bookUpdateRequestDTO.getAuthor());
                bookToUpdate.setGenre(bookUpdateRequestDTO.getGenre());
                bookToUpdate.setDescription(bookUpdateRequestDTO.getDescription());
                bookToUpdate.setLanguage(bookUpdateRequestDTO.getLanguage());
                bookToUpdate.setPublicationYear(bookUpdateRequestDTO.getPublicationYear());
                bookToUpdate.setPrice(bookUpdateRequestDTO.getPrice());
                bookToUpdate.setUpdatedAt(new Date());
                

                // Salvar as mudanças
                booksRepository.save(bookToUpdate);
                bookProducer.sendPriceToPay(bookToUpdate);
                bookProducer.publishMessageEmailUpdate(bookToUpdate);
                BookUpdateResponseDTO responseDTO = BookUpdateResponseDTO.builder()
                    .title(bookToUpdate.getTitle())
                    .author(bookToUpdate.getAuthor())
                    .genre(bookToUpdate.getGenre())
                    .description(bookToUpdate.getDescription())
                    .language(bookToUpdate.getLanguage())
                    .publicationYear(bookToUpdate.getPublicationYear())
                    .price(bookToUpdate.getPrice())
                    .build();
            return Optional.of(responseDTO);
            } else {
                return Optional.empty(); // Caso o livro não seja encontrado
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao atualizar o livro", e);
        }
    }

    @Transactional
    public List<BookItem> filterBooks(String title, String genre, int price) {
        try {
            // Filtragem baseada nos parâmetros fornecidos
            if (title != null && genre != null && price > 0) {
                return booksRepository.findByTitleAndGenreAndPrice(title, genre, price);
            } else if (title != null && genre != null) {
                return booksRepository.findByTitleAndGenre(title, genre);
            } else if (title != null && price > 0) {
                return booksRepository.findByTitleAndPrice(title, price);
            } else if (genre != null && price > 0) {
                return booksRepository.findByGenreAndPrice(genre, price);
            } else if (title != null) {
                return booksRepository.findByTitle(title);
            } else if (genre != null) {
                return booksRepository.findByGenre(genre);
            } else if (price > 0) {
                return booksRepository.findByPrice(price);
            } else {
                    return booksRepository.findAll(); // Caso não haja filtro, retorna todos os livros
                }
            } catch (Exception e) {
            throw new RuntimeException("Erro ao filtrar livros", e);
        }
    }

    @Transactional
    public boolean deleteBook(UUID id) {
        try {
            Optional<BookItem> existingBook = booksRepository.findById(id);

            if (existingBook.isPresent()) {
                booksRepository.deleteById(id);
                bookProducer.publishMessageEmailDelete(existingBook.get());
                return true;
            } else {
                return false; // Caso o livro não seja encontrado
            }
        } catch (Exception e) {
            throw new RuntimeException("Erro ao deletar o livro", e);
        }
    }
}
