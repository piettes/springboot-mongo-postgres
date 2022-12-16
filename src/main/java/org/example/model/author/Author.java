package org.example.model.author;

import lombok.Data;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.annotation.Id;

import java.util.List;

@Document
@Data
public class Author {
    @Id
    private Long id;

    private String name;

    private List<Long> books;

}
