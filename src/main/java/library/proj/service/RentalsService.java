package library.proj.service;

import library.proj.model.Book;
import library.proj.model.Person;
import library.proj.model.Rental;
import library.proj.repository.RentalsRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

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

//    public List<Rental> getRentalsInOneMonth(){
//        List<Rental> rentals = getAllRentals();
//        return rentals.stream().filter(Rental::isActiveNow).toList();
//    }

    private List<Rental> getRentalsInLastYear() {
        List<Rental> rentals = getAllRentals();
        LocalDate oneYearAgo = LocalDate.now().minusYears(1);

        return rentals.stream()
                .filter(rental -> rental.getRentalDate().isAfter(oneYearAgo))
                .collect(Collectors.toList());
    }

    public Map<String, Long> getRentalsCountInLastYearGroupedByMonth() {
        List<Rental> rentals = getRentalsInLastYear();

        return rentals.stream()
                .collect(Collectors.groupingBy(
                        rental -> rental.getRentalDate().format(DateTimeFormatter.ofPattern("MMMM yyyy")),
                        Collectors.counting()
                ));
    }

    public Map<String, Long> getBestReader(){
        List<Rental> rentals = getAllRentals();
        return rentals.stream().collect(Collectors.groupingBy(rental -> rental.getPerson().getFullName(),Collectors.counting()));
    }

    public long getRentedBook(){
        List<Rental> rentals=getAllRentals();
        return rentals.stream().filter(rental -> !rental.isReturned()).count();
    }

}
