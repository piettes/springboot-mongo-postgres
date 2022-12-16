package org.example.service;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.example.model.author.Author;
import org.example.model.author.repository.AuthorRepository;
import org.example.model.book.Book;
import org.example.model.book.repository.BookRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BookAndAuthorService {

    private final BookRepository bookRepository;
    private final AuthorRepository authorRepository;

    public List<BookDTO> getBooksAndAuthors() {
        List<Book> books = bookRepository.findAll();
        List<Author> authors = authorRepository.findAll();

        return books.stream().map(book -> {
            List<AuthorDTO> authorsDTO = authors.stream().
                    filter(author -> author.getBooks().contains(book.getId()))
                    .map(author -> new AuthorDTO(author.getName(), author.getId()))
                    .toList();
            return new BookDTO(book.getId(), book.getName(), authorsDTO);
        }).toList();
    }

    @Data
    @AllArgsConstructor
    public static class BookDTO {
        Long id;
        String name;
        List<AuthorDTO> authors;
    }

    @Data
    @AllArgsConstructor
    public static class AuthorDTO {
        String name;
        Long id;
    }

}
