package library.proj.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
public class Rating {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
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
    @Getter
    private int rating;

    public Rating() {}

    public Rating(Person person, Book book, int rating) {
        this.person = person;
        this.book = book;
        this.rating = rating;
        person.addRating(this);
        book.addRating(this);
    }

}
