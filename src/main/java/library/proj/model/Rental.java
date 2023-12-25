package library.proj.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;
import java.util.Date;

@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne
    @JoinColumn(name = "person_id")
    @Getter
    @Setter
    private Person person;
    @ManyToOne
    @JoinColumn(name = "book_id")
    @Getter
    @Setter
    private Book book;
    @Temporal(TemporalType.DATE)
    @Getter
    private LocalDate rentalDate;
    @Temporal(TemporalType.DATE)
    @Getter
    @Setter
    private LocalDate returnDate;

    public Rental() {}

    public Rental(Person person, Book book, LocalDate rentalDate) {
        this.person = person;
        this.book = book;
        this.rentalDate = rentalDate;
        person.registerRental(this);
        book.registerRental(this);
    }
}
