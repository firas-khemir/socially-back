//package com.legion.feedservice.controllers;
//
//import com.legion.feedservice.entities.example.Author;
//import com.legion.feedservice.services.example.AuthorService;
//import org.springframework.beans.factory.annotation.Autowired;
//import org.springframework.web.bind.annotation.*;
//
//import java.util.List;
//
//@RestController
//@RequestMapping("author")
//public class AuthorController {
//
//    private final AuthorService authorService;
//
//    @Autowired
//    public AuthorController(AuthorService authorService) {
//        this.authorService = authorService;
//    }
//
//    @GetMapping
//    public List<Author> getAllAuthors(){
//        return authorService.getAllAuthors();
//    }
//
//    @PostMapping
//    public Author addAuthor(@RequestBody Author author){
//        return authorService.addAuthor(author);
//    }
//
//    @PutMapping
//    public Author updateAuthor(@RequestBody Author author)  {
//        return authorService.updateAuthor(author);
//    }
//
//    @DeleteMapping("/author/{authorId}")
//    public void deleteAuthor(@PathVariable Long authorId){
//        authorService.deleteAuthor(authorId);
//    }
//
//}
