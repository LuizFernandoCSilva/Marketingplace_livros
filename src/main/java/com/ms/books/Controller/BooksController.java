package com.ms.books.Controller;

import com.ms.books.Controller.DTO.BookRequestDTO;
import com.ms.books.Controller.DTO.BookUpdateRequestDTO;
import com.ms.books.Controller.DTO.BookUpdateResponseDTO;
import com.ms.books.Entities.BookItem;
import com.ms.books.Service.BookService;
import com.ms.books.Consumers.BooksConsumer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/books")
public class BooksController {

    @Autowired
    private BookService bookService;

    @Autowired
    private BooksConsumer booksConsumer; // Injeção do consumidor para obter o userId

    // Endpoint para adicionar um livro
    @PostMapping("/add")
    public ResponseEntity<Object> addBook(@RequestBody BookRequestDTO bookRequestDTO) {
        try {
            // Criar uma nova instância de BookItem e copiar as propriedades do DTO
            BookItem bookItem = new BookItem();
            BeanUtils.copyProperties(bookRequestDTO, bookItem);

            // Obter o userId do consumidor
            java.util.UUID userId = booksConsumer.getUserId();
            if (userId == null) {
                return ResponseEntity.badRequest().build();
            }

            // Associar o userId ao livro
            bookItem.setUserId(userId);

            // Chamar o serviço para adicionar o livro
            bookItem = bookService.addBook(bookItem);
            
            return ResponseEntity.ok(bookItem.getId());
        } catch (Exception e) {
            // Logar a mensagem do erro
            return ResponseEntity.badRequest().body("Erro ao adicionar livro: " + e.getMessage());
        }
    }
    // Endpoint para atualizar um livro
   @PutMapping("/update/{id}")
    public ResponseEntity<?> updateBook(@PathVariable UUID id, @RequestBody BookUpdateRequestDTO bookUpdateRequestDTO) {
        try {
            Optional<BookUpdateResponseDTO> updatedBook = bookService.updateBook(id, bookUpdateRequestDTO);

            if (updatedBook.isPresent()) {
                // Se o livro for encontrado e atualizado
                return ResponseEntity.ok(updatedBook.get());
            } else {
                // Se o livro não for encontrado
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Livro com o ID " + id + " não encontrado.");
            }
        } catch (Exception e) {
            // Se ocorrer algum erro durante a atualização
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao atualizar o livro: " + e.getMessage());
        }
    }
    // Endpoint para buscar livros por título, gênero e/ou preço
    @GetMapping("/filter")
    public ResponseEntity<?> filterBooks(
        @RequestParam(required = false) String title,
        @RequestParam(required = false) String genre,
        @RequestParam(required = false) Integer price) {
        try {
        // Chamar o serviço para filtrar livros pelos critérios fornecidos
        List<BookItem> books = bookService.filterBooks(title, genre, price);
        return ResponseEntity.ok(books);
        } catch (Exception e) {
        return ResponseEntity.badRequest().body("Erro ao filtrar livros");
        }
    }

    // Endpoint para deletar um livro
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteBook(@PathVariable UUID id) {
        try {
            boolean isDeleted = bookService.deleteBook(id);

            if (isDeleted) {
                return ResponseEntity.ok("Livro deletado com sucesso");
            } else {
                return ResponseEntity.status(HttpStatus.NOT_FOUND)
                        .body("Livro com o ID " + id + " não encontrado.");
            }
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body("Erro ao deletar o livro: " + e.getMessage());
        }
    }
}
