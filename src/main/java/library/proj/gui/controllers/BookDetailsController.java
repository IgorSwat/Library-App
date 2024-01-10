package library.proj.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import library.proj.gui.events.OpenDialogEvent;
import library.proj.gui.scenes.*;
import library.proj.gui.scenes.navbar.NavButtonType;
import library.proj.gui.scenes.navbar.Navbar;
import library.proj.gui.scenes.rating.RatingHandlerIf;
import library.proj.gui.scenes.rating.RatingPanel;
import library.proj.model.Book;
import javafx.stage.Stage;
import library.proj.gui.events.ChangeSceneEvent;
import library.proj.model.Permissions;
import library.proj.model.Rating;
import library.proj.model.Rental;
import library.proj.service.RatingsService;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;


public class BookDetailsController extends NavbarController implements RatingHandlerIf {
    private final RatingsService ratingsService;
    private Book book;

    private double ratingsSum = 0.0;
    private int noRatings = 0;

    @FXML
    private HBox navbarField;
    private Navbar navbar = null;

    @FXML
    private HBox ratingField;
    private RatingPanel ratingPanel = null;

    @FXML
    private Label ratingLabel;
    @FXML
    private Label bookTitleField;
    @FXML
    private Label authorField;
    @FXML
    private Label coverField;
    @FXML
    private Label contentField;
    @FXML
    private Label statusField;
    @FXML
    private Label infoLabel;
    @FXML
    private ImageView imageViewField;
    @FXML
    private Button reserveButton;
    @FXML
    private Button borrowButton;

    @FXML
    private Button notifyButton;


    public BookDetailsController(Stage stage, ConfigurableApplicationContext context, Book book) {
        super(stage, context);
        this.ratingsService = context.getBean(RatingsService.class);
        this.book = book;
    }

    public void setupNavbar() {
        navbar = new Navbar(book.getTitle(), NavButtonType.BOOK_LIST_BUTTON, NavButtonType.RENTALS_BUTTON,
                NavButtonType.PROFILE_BUTTON, NavButtonType.LOGOUT_BUTTON);
        navbar.linkHandlers(this);
        navbarField.getChildren().add(navbar);

        boolean hasPermissions = LoginController.loggedAccount.getPermissions() != Permissions.USER;
        Button rentalListButton = navbar.getButton(NavButtonType.RENTALS_BUTTON);
        rentalListButton.setDisable(!hasPermissions);
    }

    public void setupRatingPanel() {
        // Validation - if user has admin permissions or has he ever rented the book
        List<Rental> userRentals = LoginController.loggedAccount.getRentals();
        userRentals = userRentals.stream().filter(rental -> rental.getBook().getId() == book.getId()).toList();

        if (LoginController.loggedAccount.getPermissions() == Permissions.ADMIN || userRentals.size() > 0) {
            ratingPanel = new RatingPanel(this);
            Rating prevRating = ratingsService.getRating(LoginController.loggedAccount, book);
            if (prevRating != null)
                ratingPanel.setRating(prevRating.getRating());
            ratingField.getChildren().add(ratingPanel);
        }
    }

    public void setupBookRating() {
        List<Rating> bookRatings = ratingsService.getRatingsByBook(book);

        if (bookRatings.isEmpty()) {
            ratingsSum = 0.0;
            noRatings = 0;
        } else {
            double sum = 0.0;
            for (Rating rating : bookRatings)
                sum += (double) rating.getRating();
            ratingsSum = sum;
            noRatings = bookRatings.size();
        }
        updateRatingLabel();
    }

    public void setupButtons() {
        if (LoginController.loggedAccount.getPermissions() == Permissions.USER) {
            borrowButton.setVisible(false);
            borrowButton.setManaged(false);
        }
    }

    public void setFields() {
        bookTitleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        coverField.setText(book.getCover());
        contentField.setText(book.getContents());
        statusField.setText(book.getStatus().toString());
        Image img = new Image(book.getImagePath());
        imageViewField.setImage(img);
        if (LoginController.loggedAccount.getPermissions() == Permissions.USER) {
            borrowButton.setDisable(true);
        }
        if(this.book.isAvailable()){
            notifyButton.setDisable(true);
        }
    }

    public void handleRatingSet(int rating) {
        ratingsService.createRating(new Rating(LoginController.loggedAccount, book, rating));
        ratingsSum += rating;
        noRatings += 1;
        updateRatingLabel();
    }

    public void handleRatingUnset() {
        ratingsService.removeRating(LoginController.loggedAccount, book);
        setupBookRating();
    }

    private void updateRatingLabel() {
        if (noRatings == 0)
            ratingLabel.setText("Brak ocen");
        else {
            double average = ratingsSum / noRatings;
            ratingLabel.setText(Double.toString(average) + " / 5.0");
        }
    }

    @FXML
    private void handleBackButton() {
        context.publishEvent(new ChangeSceneEvent(stage, context, new BookListCreator()));
    }

    @FXML
    private void handleNotify() {
        book.addNotifyPerson(LoginController.loggedAccount);
        infoLabel.setText("Zostaniesz powiadomiony gdy książka będzie dostępna.");
    }

    @FXML
    private void handleBorrowBook() {
        if (LoginController.loggedAccount.getPermissions() == Permissions.USER) {
            return;
        }
        context.publishEvent(new OpenDialogEvent("Wypożyczanie książki", 360, 500,
                stage, context, new RentBookCreator(stage, book)));
    }

    @FXML
    private void handleReserveBook() {
        context.publishEvent(new OpenDialogEvent("Rezerwacja książki", 360, 500,
                stage, context, new ReserveBookCreator(stage, book)));    }
}
