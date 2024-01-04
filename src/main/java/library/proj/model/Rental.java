package library.proj.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

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
    private LocalDate rentalDate;
    @Temporal(TemporalType.DATE)
    @Getter
    @Setter
    private LocalDate returnDate;
    @Getter
    @Setter
    private boolean returned; // true = returned, false = not returned

    public Rental() {}

    public Rental(Person person, Book book, LocalDate returnDate) {
        this.person = person;
        this.book = book;
        this.rentalDate = LocalDate.now();
        this.returnDate = returnDate;
        this.returned = false;
        person.registerRental(this);
        book.registerRental(this);
    }

    public boolean isActiveNow() {
        return !returned && !LocalDate.now().isBefore(rentalDate);
    }
}
