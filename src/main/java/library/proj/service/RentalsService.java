package library.proj.service;

import library.proj.model.Person;
import library.proj.model.Rental;
import library.proj.repository.RentalsRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class RentalsService {

    private final RentalsRepository rentalsRepository;

    public RentalsService(RentalsRepository rentalsRepository) {this.rentalsRepository = rentalsRepository;}

    public List<Rental> getAllRentals() {return rentalsRepository.findAll();}

    public List<Rental> getRentalsByUser(Person person) {
        List<Rental> rentals = getAllRentals();
        return rentals.stream().filter(rental -> rental.getPerson() == person).toList();
    }

    public List<Rental> getNotReturnedBooks() {
        List<Rental> rentals = getAllRentals();
        return rentals.stream().filter(rental -> rental.getReturnDate() == null).toList();
    }

    public Rental createRental(Rental rental) {return rentalsRepository.save(rental);}

}
