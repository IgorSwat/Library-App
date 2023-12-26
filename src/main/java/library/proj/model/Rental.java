package library.proj.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Entity
public class Rental {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "person_id")
    @Getter
    @Setter
    private Person person;
    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "book_id")
    @Getter
    @Setter
    private Book book;
    @Temporal(TemporalType.DATE)
    @Getter
    private Date rentalDate;
    @Temporal(TemporalType.DATE)
    @Getter
    @Setter
    private Date returnDate;

    public Rental() {}

    public Rental(Person person, Book book, Date rentalDate) {
        this.person = person;
        this.book = book;
        this.rentalDate = rentalDate;
        person.registerRental(this);
        book.registerRental(this);
    }
}
