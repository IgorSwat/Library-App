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
import library.proj.gui.scenes.objects.RcommendationEntry;
import library.proj.model.Book;
import library.proj.model.Permissions;
import library.proj.service.BooksService;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Comparator;
import java.util.List;

public class RecommendationsController extends NavbarController{
    private final BooksService booksService;

    private List<Book> books;
    private List<Book> filteredBooks;

    @FXML
    private HBox navbarField;
    private Navbar navbar = null;

    @FXML
    private VBox bookList;

    private static final double entriesSpacing = 50.0;

    public RecommendationsController(Stage stage, ConfigurableApplicationContext context) {
        super(stage, context);
        this.booksService = context.getBean(BooksService.class);

        loadBooks();
    }

    public void setupNavbar() {
        navbar = new Navbar("Rekomendacje", NavButtonType.BOOK_LIST_BUTTON,
                NavButtonType.RENTALS_BUTTON, NavButtonType.PROFILE_BUTTON, NavButtonType.LOGOUT_BUTTON);
        navbar.linkHandlers(this);
        navbarField.getChildren().add(navbar);

        boolean hasPermissions = LoginController.loggedAccount.getPermissions() != Permissions.USER;
        Button rentalListButton = navbar.getButton(NavButtonType.RENTALS_BUTTON);
        rentalListButton.setDisable(!hasPermissions);
    }

    public void loadBooks() {
        this.books = booksService.getAllBooks();
        this.filteredBooks = books;
        this.filteredBooks.sort(Comparator.comparing(Book::getRating).reversed());
    }

    public void updateItemsList() {
        bookList.getChildren().clear();
        loadBooks();

        for (int i = 0; i < 5; i++) {
            Book book = filteredBooks.get(i);
            RcommendationEntry entry = new RcommendationEntry(book);
            entry.setOnMouseClicked(mouseEvent -> handleBookDetailsClicked(mouseEvent, book));
            bookList.getChildren().add(entry);
        }
    }

    @FXML
    public void handleBookDetailsClicked(MouseEvent event, Book book) {
        context.publishEvent(new ChangeSceneEvent(stage, context, new BookDetailsCreator(book)));
    }
}
