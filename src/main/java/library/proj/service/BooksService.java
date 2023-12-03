package library.proj.service;

import library.proj.model.Book;
import library.proj.repository.BooksRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class BooksService {

    private final BooksRepository booksRepository;

    public BooksService(BooksRepository booksRepository) {this.booksRepository = booksRepository;}

    public List<Book> getAllBooks() {return booksRepository.findAll();}

    public Book getBook(int id) {return booksRepository.findById(id);}

    public Book getBook(String author, String title) {return booksRepository.findByTitleAndAuthor(title, author);}

    public Book createBook(Book book) {return booksRepository.save(book);}
}
