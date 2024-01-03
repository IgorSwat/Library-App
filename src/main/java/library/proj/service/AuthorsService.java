package library.proj.service;

import library.proj.model.Author;
import library.proj.repository.AuthorsRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AuthorsService {

    private final AuthorsRepository authorsRepository;

    public AuthorsService(AuthorsRepository authorsRepository) {this.authorsRepository = authorsRepository;}

    public List<Author> getAllAuthors() {return authorsRepository.findAll();}

    public Author getAuthor(int id) {return authorsRepository.findById(id);}

    public Author getAuthor(String firstname, String lastname) {return authorsRepository.findByFirstNameAndLastName(firstname, lastname);}

    public Author createAuthor(Author author) {return authorsRepository.save(author);}

    public Author saveAuthor(Author author){
        return authorsRepository.save(author);
    }
}
