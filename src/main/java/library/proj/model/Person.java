package library.proj.model;

import jakarta.persistence.*;
import lombok.Getter;

import java.util.ArrayList;
import java.util.List;

@Entity
public class Person {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
    private int id;
    @Getter
    private String firstName;
    @Getter
    private String lastName;
    @Getter
    @Column(unique=true)
    private String email;
    @Getter
    private String password;
    private int permissions;
    @Getter
    //    TODO: przy lazy ładowaniu wywala błędy, na razie dałem eager, ale może da się to jakoś mądrzej zrobić
    @OneToMany(fetch = FetchType.EAGER, mappedBy = "person", cascade = CascadeType.ALL)
    private List<Rental> rentals;
    @OneToMany(mappedBy = "person", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Rating> ratings;

    public Person() {}

    public Person(String firstName, String lastName, String email, String password, Permissions permissions) {
        this.firstName = firstName;
        this.lastName = lastName;
        this.email = email;
        this.password = password;
        this.permissions = permissions.ordinal();
    }

    public String getFullName() { return firstName + " " + lastName; }

    public Permissions getPermissions() {return Permissions.values()[permissions];}

    public String toString() {
        return getFullName() + "   |   " + Permissions.values()[permissions];
    }

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
}
