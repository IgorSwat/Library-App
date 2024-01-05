package library.proj.service;

import library.proj.model.Book;
import library.proj.model.Person;
import library.proj.model.Rating;
import library.proj.repository.RatingsRepository;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class RatingsService {

    private final RatingsRepository ratingsRepository;

    public RatingsService(RatingsRepository ratingsRepository) {this.ratingsRepository = ratingsRepository;}

    public List<Rating> getAllRatings() {return ratingsRepository.findAll();}

    public List<Rating> getRatingsByBook(Book book) {return ratingsRepository.findByBook(book);}

    public Rating getRating(Person person, Book book) {
        return ratingsRepository.findByPersonAndBook(person, book);
    }

    public Rating createRating(Rating rating) {return ratingsRepository.save(rating);}

    public void removeRating(Person person, Book book) {ratingsRepository.deleteByPersonAndBook(person, book);}

}
