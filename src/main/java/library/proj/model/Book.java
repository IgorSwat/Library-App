package library.proj.model;

import jakarta.mail.*;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.persistence.*;
import lombok.Getter;

import java.time.LocalDate;
import java.util.*;

@Entity
public class Book {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Getter
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
    @OneToMany(mappedBy = "book", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private List<Reservation> reservations;
    @OneToMany(mappedBy = "id", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<Person> notifyPersons=new LinkedHashSet<>();


    public Book() {
    }

    public Book(String title, String author, String cover, String contents, Status status, String imagePath) {
        this.title = title;
        this.author = author;
        this.cover = cover;
        this.contents = contents;
        this.status = status;
        this.views = 0;
        this.imagePath = imagePath;
    }

    public void updateViews(int views) {
        this.views = views;
    }

    public void addNotifyPerson(Person person){
        notifyPersons.add(person);
    }

    private Person getPersonToNotify(){
        Iterator<Person> iter=notifyPersons.iterator();
        if(iter.hasNext()){
            Person pers=iter.next();
            notifyPersons.remove(pers);
            return pers;
        }
        return null;
    }

    public void notifyPerson() throws MessagingException {
        Person pers=getPersonToNotify();
        if (pers!=null){
            System.out.println("wysyłam mail");
            Properties prop = new Properties();
            prop.put("mail.smtp.host", "smtp.gmail.com");
            prop.put("mail.smtp.port", "587");
            prop.put("mail.smtp.auth", "true");
            prop.put("mail.smtp.starttls.enable", "true");

            Session session = Session.getInstance(prop,
                    new Authenticator() {
                        protected PasswordAuthentication getPasswordAuthentication() {
                            return new PasswordAuthentication("username@abc.pl", "password");
                        }
                    });
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress("czytelnia@agh.com"));
            message.setRecipient(Message.RecipientType.TO, new InternetAddress(pers.getEmail()));
            message.setSubject("Dostępność książki");
            message.setText("Twoja książka "+title+ " jest już dostepna");

            Transport.send(message);
        }
    }

    public void registerRental(Rental rental) {
        if (rentals == null)
            rentals = new ArrayList<>();
        rentals.add(rental);
    }

    public void addReservation(Reservation reservation) {
        if (reservations == null)
            reservations = new ArrayList<>();
        reservations.add(reservation);
    }

    public void addRating(Rating rating) {
        if (ratings == null)
            ratings = new ArrayList<>();
        ratings.add(rating);
    }

    public double getRating() {
        if (ratings == null || ratings.isEmpty())
            return 0.0;
        double sum = 0.0;
        for (Rating rating : ratings)
            sum += rating.getRating();
        return sum / ratings.size();
    }

    public boolean isAvailable() {
        return status == Status.AVAILABLE;
    }

    public boolean isRentedNow(){
        for(Rental rental: rentals){
            if (rental.isActiveNow())
                return true;
        }
        return false;
    }

    public boolean hasOverlappingActiveReservation(LocalDate startDate, LocalDate endDate){
        for(Reservation reservation : reservations){
            if (reservation.overlaps(startDate, endDate) && reservation.isActive())
                return true;
        }
        return false;
    }

    public void setStatus(Status status) {
        this.status = status;
    }

    @Override
    public String toString() {
        return title + " " + author + "   |   " + Status.values()[status.ordinal()];
    }
}
