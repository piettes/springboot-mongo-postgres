package org.example.controller;

import lombok.RequiredArgsConstructor;
import org.example.service.BookAndAuthorService;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

import static org.springframework.web.bind.annotation.RequestMethod.GET;

@RestController
@RequiredArgsConstructor
public class BookController {

    private final BookAndAuthorService bookAndAuthorService;

    @RequestMapping(path = "/books", method = GET)
    public List<BookAndAuthorService.BookDTO> getBooks() {
        return bookAndAuthorService.getBooksAndAuthors();

    }
}