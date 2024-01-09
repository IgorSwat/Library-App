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
import library.proj.gui.scenes.pagination.PaginationBar3x;
import library.proj.gui.scenes.pagination.PaginationHandlerIf;
import library.proj.model.Book;
import library.proj.model.Permissions;
import library.proj.service.BooksService;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class BookListController extends NavbarController implements PaginationHandlerIf {
    private final BooksService booksService;

    private List<Book> books;

    @FXML
    private HBox navbarField;
    private Navbar navbar = null;

    @FXML
    private HBox paginationField;
    private PaginationBar3x pagination = null;

    @FXML
    private VBox bookList;

    private static final int maxEntriesInRow = 5;
    private static final int maxRowsPerPage = 3;
    private static final double entriesSpacing = 50.0;

    public BookListController(Stage stage, ConfigurableApplicationContext context) {
        super(stage, context);
        this.booksService = context.getBean(BooksService.class);

        loadBooks();
    }

    public void setupNavbar() {
        navbar = new Navbar("Lista książek", NavButtonType.ADD_BOOK_BUTTON, NavButtonType.RENTALS_BUTTON,
                            NavButtonType.PROFILE_BUTTON, NavButtonType.LOGOUT_BUTTON);
        navbar.linkHandlers(this);
        navbarField.getChildren().add(navbar);

        boolean hasPermissions = LoginController.loggedAccount.getPermissions() != Permissions.USER;
        Button addBookButton = navbar.getButton(NavButtonType.ADD_BOOK_BUTTON);
        addBookButton.setDisable(!hasPermissions);
        Button rentalListButton = navbar.getButton(NavButtonType.RENTALS_BUTTON);
        rentalListButton.setDisable(!hasPermissions);
    }

    public void setupPagination() {
        pagination = new PaginationBar3x(maxEntriesInRow * maxRowsPerPage, books.size());
        pagination.setupHandler(this);
        paginationField.getChildren().add(pagination);
    }

    public void loadBooks() {
        this.books = booksService.getAllBooks();
    }

    public void updateItemsList() {
        bookList.getChildren().clear();

        int lowerBound = pagination.getPageLowerBound();
        int upperBound = pagination.getPageUpperBound();
        int rowCounter = 0;

        HBox row = new HBox();
        row.getStyleClass().add("book-list-row");
        for (int i = lowerBound; i < upperBound; i++) {
            Book book = books.get(i);
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
