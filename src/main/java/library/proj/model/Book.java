package library.proj.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private int id;
    @Getter
    private String title;
    @Getter
    private String author;
    @Getter
    private String cover;
    @Getter
    private String contents;
    @Getter
    private int status;
    @Getter
    private int views;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Rental> rentals;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL)
    private List<Rating> ratings;

    public Book() {}

    public Book(String title, String author, String cover, String contents, int status) {
        this.title = title;
        this.author = author;
        this.cover = cover;
        this.contents = contents;
        this.status = status;
        this.views = 0;
    }

    public void updateViews(int views) {this.views = views;}

    @Override
    public String toString() {
        return title + author + "   |   " + status;
    }

    public void registerRental(Rental rental) {
        if (rentals == null)
            rentals = new ArrayList<>();
        rentals.add(rental);
    }

    void addRating(Rating rating) {
        if (ratings == null)
            ratings = new ArrayList<>();
        ratings.add(rating);
    }
}
