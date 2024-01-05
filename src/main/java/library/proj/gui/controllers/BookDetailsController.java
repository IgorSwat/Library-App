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
import library.proj.model.Book;
import javafx.stage.Stage;
import library.proj.gui.events.ChangeSceneEvent;
import library.proj.model.Permissions;
import org.springframework.context.ConfigurableApplicationContext;


public class BookDetailsController extends NavbarController {
    private Book book;

    @FXML
    private HBox navbarField;
    private Navbar navbar = null;

    @FXML
    public Label bookTitleField;
    @FXML
    public Label authorField;
    @FXML
    public Label coverField;
    @FXML
    public Label contentField;
    @FXML
    public Label statusField;
    @FXML
    private ImageView imageViewField;


    public BookDetailsController(Stage stage, ConfigurableApplicationContext context, Book book) {
        super(stage, context);
        this.book = book;
    }

    public void setupNavbar() {
        navbar = new Navbar(book.getTitle(), NavButtonType.RENTALS_BUTTON,
                NavButtonType.PROFILE_BUTTON, NavButtonType.LOGOUT_BUTTON);
        navbar.linkHandlers(this);
        navbarField.getChildren().add(navbar);

        boolean hasPermissions = Permissions.values()[LoginController.loggedAccount.getPermissions()] != Permissions.USER;
        Button rentalListButton = navbar.getButton(NavButtonType.RENTALS_BUTTON);
        rentalListButton.setDisable(!hasPermissions);
    }

    public void setFields() {
        bookTitleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        coverField.setText(book.getCover());
        contentField.setText(book.getContents());
        statusField.setText(book.getStatus().toString());
        Image img = new Image(book.getImagePath());
        imageViewField.setImage(img);
    }

    @FXML
    private void handleBackButton() {
        context.publishEvent(new ChangeSceneEvent(stage, context, new BookListCreator()));
    }

    @FXML
    private void handleBorrowBook() {
        if (Permissions.values()[LoginController.loggedAccount.getPermissions()] == Permissions.USER) {
            return;
        }
        context.publishEvent(new OpenDialogEvent("Wypożyczanie książki", 360, 500,
                stage, context, new RentBookCreator(stage, book)));
    }
}
