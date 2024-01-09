package library.proj.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.DatePicker;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.stage.Stage;
import library.proj.gui.events.ChangeSceneEvent;
import library.proj.gui.scenes.BookListCreator;
import library.proj.model.Book;
import library.proj.model.Person;
import library.proj.model.Rental;
import library.proj.model.Status;
import library.proj.service.BooksService;
import library.proj.service.PersonService;
import library.proj.service.RentalsService;
import org.springframework.context.ConfigurableApplicationContext;

import java.time.LocalDate;

public class ReserveBookController {
    private final Stage primaryStage;
    private final Stage stage;
    private final ConfigurableApplicationContext context;
    private final BooksService booksService;
    private final RentalsService rentalsService;
    private final PersonService personService;
    private final Book book;
    @FXML
    private Label titleLabel;
    @FXML
    private Label authorLabel;
    @FXML
    ImageView coverImage;
    @FXML
    TextField customerEmail;
    @FXML
    DatePicker startDate;
    @FXML
    DatePicker endDate;
    @FXML
    Label errorLabel;
    @FXML
    Label availabilityLabel;

    public ReserveBookController(Stage primaryStage, Stage stage, ConfigurableApplicationContext context, Book book) {
        this.primaryStage = primaryStage;
        this.stage = stage;
        this.context = context;
        this.book = book;
        this.booksService = context.getBean(BooksService.class);
        this.rentalsService = context.getBean(RentalsService.class);
        this.personService = context.getBean(PersonService.class);
    }

    @FXML
    public void handleReserveClick() {
        String email = customerEmail.getText();
        Person person = personService.getPerson(email);
//        if (!validateReservationRequirements(person, date))
//            return;
//        book.setStatus(Status.NOT_AVAILABLE);
//        Rental rental = new Rental(LoginController.loggedAccount, book, date);
//        rentalsService.createRental(rental);
//        booksService.saveBook(book);
//        context.publishEvent(new ChangeSceneEvent(primaryStage, context, new BookListCreator()));
//        stage.close();
        System.out.println(validateReservationRequirements(person, startDate.getValue(), endDate.getValue()));
    }

    private boolean validateReservationRequirements(Person person, LocalDate startDate, LocalDate endDate) {
        if (startDate == null || endDate == null) {
            errorLabel.setText("Podaj datę rezerwacji");
            return false;
        }
        if (startDate.isBefore(LocalDate.now())) {
            errorLabel.setText("Nie można podać daty z przeszłości");
            return false;
        }
        if(endDate.isBefore(startDate)){
            errorLabel.setText("Data rozpoczęcia rezerwacji musi być wcześniejsza niż data zakończenia");
            return false;
        }
        if (!book.isAvailable()) {
            errorLabel.setText("Wybrana książka jest niedostępna");
            return false;
        }
        if(book.isRentedNow()){
            errorLabel.setText("Książka jest wypożyczona przez kogoś innego");
            return false;
        }
        if(book.hasOverlappingReservation(startDate, endDate)){
            errorLabel.setText("Książka ma nakładającą się rezerwację");
            return false;
        }
        if (person == null) {
            errorLabel.setText("Osoba o podanym e-mailu nie istnieje");
            return false;
        }
        return true;
    }

    public void initialize() {
        titleLabel.setText(book.getTitle());
        authorLabel.setText(book.getAuthor());
        availabilityLabel.setText(book.isAvailable() ? "Tak" : "Nie");
        Image cover = new Image(book.getImagePath(), true);
        coverImage.setImage(cover);
    }
}

