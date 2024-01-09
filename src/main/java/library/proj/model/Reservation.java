package library.proj.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDate;

@Entity
public class Reservation {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
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
    private LocalDate startDate;
    @Temporal(TemporalType.DATE)
    @Getter
    @Setter
    private LocalDate endDate;
    @Getter
    @Setter
    private boolean active;

    public Reservation() {
    }

    public Reservation(Person person, Book book, LocalDate startDate, LocalDate endDate) {
        this.person = person;
        this.book = book;
        this.startDate = startDate;
        this.endDate = endDate;
        this.active = true;
        person.addReservation(this);
        book.addReservation(this);
    }

    public boolean overlaps(LocalDate from, LocalDate to){
         return !(from.isAfter(endDate) || to.isBefore(startDate)) ;
    }

}
