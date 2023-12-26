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
    private Status status;
    @Getter
    private int views;
    @Getter
    private String imagePath;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Rental> rentals;
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Rating> ratings;

    public Book() {}

    public Book(String title, String author, String cover, String contents, Status status, String imagePath) {
        this.title = title;
        this.author = author;
        this.cover = cover;
        this.contents = contents;
        this.status = status;
        this.views = 0;
        this.imagePath=imagePath;
    }

    public void updateViews(int views) {this.views = views;}

    public void registerRental(Rental rental) {
        if (rentals == null)
            rentals = new ArrayList<>();
        rentals.add(rental);
    }

    public void addRating(Rating rating) {
        if (ratings == null)
            ratings = new ArrayList<>();
        ratings.add(rating);
    }

    public boolean isAvailable() {
        return status == Status.AVAILABLE;
    }

    @Override
    public String toString() {
        return title + " " + author + "   |   " + Status.values()[status.ordinal()];
    }
}
