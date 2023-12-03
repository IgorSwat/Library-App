package library.proj.service;

import library.proj.model.Book;
import library.proj.model.Person;
import library.proj.model.Rating;
import library.proj.repository.RatingsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RatingsService {

    private final RatingsRepository ratingsRepository;

    public RatingsService(RatingsRepository ratingsRepository) {this.ratingsRepository = ratingsRepository;}

    public List<Rating> getAllRatings() {return ratingsRepository.findAll();}

    public List<Rating> getRatingsByUser(Person person) {
        List<Rating> ratings = getAllRatings();
        return ratings.stream().filter(rating -> rating.getPerson() == person).toList();
    }

    public List<Rating> getRatingsByBook(Book book) {
        List<Rating> ratings = getAllRatings();
        return ratings.stream().filter(rating -> rating.getBook() == book).toList();
    }

    public Rating createRating(Rating rating) {return ratingsRepository.save(rating);}

}
