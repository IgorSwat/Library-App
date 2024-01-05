package library.proj.service;

import library.proj.model.Book;
import library.proj.model.Person;
import library.proj.model.Rental;
import library.proj.repository.RentalsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;

@Service
public class RentalsService {

    private final RentalsRepository rentalsRepository;

    public RentalsService(RentalsRepository rentalsRepository) {this.rentalsRepository = rentalsRepository;}

    public List<Rental> getAllRentals() {return rentalsRepository.findAll();}

    public List<Rental> getCurrentRentals() {
        List<Rental> rentals = getAllRentals();
        return rentals.stream().filter(Rental::isActiveNow).toList();
    }

    public List<Rental> getRentalsByUser(Person person) {
        List<Rental> rentals = getAllRentals();
        return rentals.stream().filter(rental -> rental.getPerson().getId() == person.getId()).toList();
    }

    public List<Rental> getRentalsByUser(Person person, Book book) {
        List<Rental> rentals = getRentalsByUser(person);
        return rentals.stream().filter(rental -> rental.getBook().getId() == book.getId()).toList();
    }

    public List<Rental> getNotReturnedBooks() {
        List<Rental> rentals = getAllRentals();
        return rentals.stream().filter(rental -> rental.getReturnDate() == null).toList();
    }

    public Rental createRental(Rental rental) {return rentalsRepository.save(rental);}

    public Rental updateRentalStatus(int rentalId, boolean status) {
        Rental rentalEntity = rentalsRepository.findById(rentalId);
        if (rentalEntity != null) {
            rentalEntity.setReturned(status);
            return rentalsRepository.save(rentalEntity);
        }
        return null;
    }

}
