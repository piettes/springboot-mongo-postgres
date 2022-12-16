package org.example.model.author.repository;

import org.example.model.author.Author;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface AuthorRepository  extends MongoRepository<Author, Long> {
}
