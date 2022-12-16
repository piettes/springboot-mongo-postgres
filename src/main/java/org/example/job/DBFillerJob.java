package org.example.job;

import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.example.model.author.Author;
import org.example.model.author.repository.AuthorRepository;
import org.example.model.book.Book;
import org.example.model.book.repository.BookRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;


/**
 * If the databases are empty, fill them with some authors and books at application start
 */
@AllArgsConstructor
@Slf4j
@Component
public class DBFillerJob {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    @EventListener(ApplicationReadyEvent.class)
    public void fillDB() {

        log.info("Number of books: {}", bookRepository.findAll().size());
        log.info("Number of authors: {}", authorRepository.findAll().size());

        if (bookRepository.findAll().size() == 0 && authorRepository.findAll().size() == 0) {
            log.info("Filling DB");
            Book book1 = new Book();
            book1.setName("Book 1");
            bookRepository.save(book1);

            Book book2 = new Book();
            book2.setName("Book 2");
            bookRepository.save(book2);

            Book book3 = new Book();
            book3.setName("Book 3");
            bookRepository.save(book3);

            authorRepository.deleteById(1L);

            Author author1 = new Author();
            author1.setName("Author 1");
            author1.setId(1L);
            author1.setBooks(List.of(book1.getId()));
            authorRepository.save(author1);

            Author author2 = new Author();
            author2.setName("Author 2");
            author2.setId(2L);
            author2.setBooks(List.of(book1.getId(), book2.getId()));
            authorRepository.save(author2);

            Author author3 = new Author();
            author3.setName("Author 3");
            author3.setId(3L);
            author3.setBooks(Collections.emptyList());
            authorRepository.save(author1);
        }
    }
}
