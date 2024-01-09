package library.proj.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import library.proj.gui.events.ChangeSceneEvent;
import library.proj.gui.scenes.*;
import library.proj.gui.scenes.navbar.NavButtonType;
import library.proj.gui.scenes.navbar.Navbar;
import library.proj.gui.scenes.objects.BookEntry;
import library.proj.model.Book;
import library.proj.model.Permissions;
import library.proj.service.BooksService;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class BookListController extends NavbarController {
    private final BooksService booksService;

    @FXML
    private HBox navbarField;
    private Navbar navbar = null;

    @FXML
    private VBox bookList;

    private static final int maxEntriesInRow = 5;
    private static final double entriesSpacing = 50.0;

    public BookListController(Stage stage, ConfigurableApplicationContext context) {
        super(stage, context);
        this.booksService = context.getBean(BooksService.class);
    }

    public void setupNavbar() {
        navbar = new Navbar("Lista książek", NavButtonType.ADD_BOOK_BUTTON, NavButtonType.RENTALS_BUTTON, NavButtonType.RESERVATIONS_BUTTON,
                            NavButtonType.PROFILE_BUTTON, NavButtonType.LOGOUT_BUTTON);
        navbar.linkHandlers(this);
        navbarField.getChildren().add(navbar);

        boolean hasPermissions = LoginController.loggedAccount.getPermissions() != Permissions.USER;
        Button addBookButton = navbar.getButton(NavButtonType.ADD_BOOK_BUTTON);
        addBookButton.setDisable(!hasPermissions);
        Button rentalListButton = navbar.getButton(NavButtonType.RENTALS_BUTTON);
        rentalListButton.setDisable(!hasPermissions);
    }

    public void updateBookList() {
        bookList.getChildren().clear();

        List<Book> books = booksService.getAllBooks();
        HBox row = new HBox();
        row.getStyleClass().add("book-list-row");
        int rowCounter = 0;
        for (Book book : books) {
            rowCounter = (rowCounter + 1) % (maxEntriesInRow + 1);
            if (rowCounter == 0) {
                bookList.getChildren().add(row);
                row = new HBox();
                row.getStyleClass().add("book-list-row");
                rowCounter++;
            }
            BookEntry entry = new BookEntry(book);
            entry.setOnMouseClicked(mouseEvent -> handleBookDetailsClicked(mouseEvent, book));
            row.getChildren().add(entry);
        }
        bookList.getChildren().add(row);
    }

    @FXML
    public void handleBookDetailsClicked(MouseEvent event, Book book) {
        context.publishEvent(new ChangeSceneEvent(stage, context, new BookDetailsCreator(book)));
    }
}
