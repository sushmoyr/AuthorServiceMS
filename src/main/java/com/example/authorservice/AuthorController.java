package com.example.authorservice;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/authors")
@RequiredArgsConstructor
@Slf4j
public class AuthorController {

    private final AuthorRepository repository;

    @PostMapping
    public Author createAuthor(@RequestBody CreateAuthorRequest body) {
        Author author = new Author();
        author.setName(body.getName());
        author.setEmail(body.getEmail());
        author.setBio(body.getBio());
        return repository.save(author);
    }

    @GetMapping
    public List<Author> getAuthors() {
        return repository.findAll();
    }

    @GetMapping("/{id}")
    public Author getAuthor(@PathVariable Long id) {
        return repository.findById(id).orElseThrow();
    }

}
