package com.legion.feedservice.services.example;

import com.legion.feedservice.entities.example.Author;
import com.legion.feedservice.repositories.example.AuthorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


import java.util.List;
import java.util.Optional;

@Service
public class AuthorService {
    private final AuthorRepository authorRepository;

    @Autowired
    public AuthorService(AuthorRepository authorRepository) {
        this.authorRepository = authorRepository;
    }

    public List<Author> getAllAuthors(){
        return authorRepository.findAll();
    }

    public Author addAuthor(Author author){
        return authorRepository.save(author);
    }

    public Author updateAuthor(Author author)  {
        Optional<Author> authorFromDB=  authorRepository.findById(author.getId());
        if(authorFromDB.isPresent()){
            Author authorFromDBVal = authorFromDB.get();
            authorFromDBVal.setBooks(author.getBooks());
            authorFromDBVal.setName(author.getName());
            authorRepository.save(authorFromDBVal);
        }else{
            return null;
        }
        return author;
    }

    public void deleteAuthor(Long id) {
        authorRepository.deleteById(id);
    }
}
