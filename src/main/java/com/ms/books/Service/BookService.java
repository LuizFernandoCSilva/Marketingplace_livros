package com.ms.books.Service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
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
    public void addBook(BookItem bookItem) {
        try {
            BookItem book = booksRepository.save(bookItem);
            bookProducer.publishMessageEmailAdd(book);
            
        } catch (Exception e) {
            throw new RuntimeException("Erro ao adicionar livro", e);
        }
    }
}
