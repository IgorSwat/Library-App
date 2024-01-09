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

public class RentBookController {
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
    Label errorLabel;
    @FXML
    Label availabilityLabel;

    public RentBookController(Stage primaryStage, Stage stage, ConfigurableApplicationContext context, Book book) {
        this.primaryStage = primaryStage;
        this.stage = stage;
        this.context = context;
        this.book = book;
        this.booksService = context.getBean(BooksService.class);
        this.rentalsService = context.getBean(RentalsService.class);
        this.personService = context.getBean(PersonService.class);
    }

    @FXML
    public void handleRentClick() {
        String email = customerEmail.getText();
        LocalDate date = startDate.getValue();
        Person person = personService.getPerson(email);
        if (!validateHireRequirements(person, date))
            return;
        book.setStatus(Status.NOT_AVAILABLE);
        Rental rental = new Rental(LoginController.loggedAccount, book, date);
        rentalsService.createRental(rental);
        booksService.saveBook(book);
        context.publishEvent(new ChangeSceneEvent(primaryStage, context, new BookListCreator()));
        stage.close();
    }

    private boolean validateHireRequirements(Person person, LocalDate date) {
        if (date == null) {
            errorLabel.setText("Podaj datę wypożyczenia");
            return false;
        }
        if (date.isBefore(LocalDate.now())) {
            errorLabel.setText("Nie można podać daty z przeszłości");
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
        if(book.hasOverlappingActiveReservation(startDate.getValue(), startDate.getValue())){
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
