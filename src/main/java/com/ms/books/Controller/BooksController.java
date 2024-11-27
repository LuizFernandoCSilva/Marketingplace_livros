package com.ms.books.Controller;

import com.ms.books.Controller.DTO.BookRequestDTO;
import com.ms.books.Entities.BookItem;
import com.ms.books.Service.BookService;
import com.ms.books.Consumers.BooksConsumer;

import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity<?> addBook(@RequestBody BookRequestDTO bookRequestDTO) {
        try {
            // Criar uma nova instância de BookItem e copiar as propriedades do DTO
            BookItem bookItem = new BookItem();
            BeanUtils.copyProperties(bookRequestDTO, bookItem);

            // Obter o userId do consumidor
            java.util.UUID userId = booksConsumer.getUserId();
            if (userId == null) {
                return ResponseEntity.badRequest().body("UserId não recebido.");
            }

            // Associar o userId ao livro
            bookItem.setUserId(userId);

            // Chamar o serviço para adicionar o livro
            bookService.addBook(bookItem);
            
            return ResponseEntity.ok("Livro adicionado com sucesso");
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Erro ao adicionar livro");
        }
    }
}
