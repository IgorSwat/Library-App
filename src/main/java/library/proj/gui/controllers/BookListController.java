package library.proj.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import library.proj.gui.events.OpenDialogEvent;
import library.proj.gui.scenes.AddBookCreator;
import library.proj.gui.scenes.RentBookCreator;
import library.proj.gui.scenes.objects.BookEntry;
import library.proj.model.Book;
import library.proj.model.Permissions;
import library.proj.service.BooksService;
import org.slf4j.LoggerFactory;
import org.springframework.context.ConfigurableApplicationContext;

import java.awt.event.MouseEvent;
import java.util.List;

public class BookListController {
    private final Stage stage;
    private final ConfigurableApplicationContext context;
    private final BooksService booksService;

    @FXML
    private VBox bookList;
    @FXML
    private Button addBookButton;

    private static final int maxEntriesInRow = 5;
    private static final double entriesSpacing = 50.0;

    public BookListController(Stage stage, ConfigurableApplicationContext context) {
        this.stage = stage;
        this.context = context;
        this.booksService = context.getBean(BooksService.class);
    }

    public void updateBookList() {
        bookList.getChildren().clear();

        List<Book> books = booksService.getAvailableBooks();
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
            entry.setOnDragDetected(event -> handleBookEntryDrag(book));
            row.getChildren().add(entry);
        }
        bookList.getChildren().add(row);
    }

    public void updateNavbar() {
        boolean hasPermissions = Permissions.values()[LoginController.loggedAccount.getPermissions()] != Permissions.USER;
        addBookButton.setDisable(!hasPermissions);
    }

    @FXML
    public void handleAddBookClick() {
        context.publishEvent(new OpenDialogEvent("Dodawanie książki", 360, 500,
                stage, context, new AddBookCreator(stage)));
    }

    private void handleBookEntryDrag(Book book) {
        if (Permissions.values()[LoginController.loggedAccount.getPermissions()] == Permissions.USER) {
            return;
        }
        context.publishEvent(new OpenDialogEvent("Wypożyczanie książki", 360, 500,
                stage, context, new RentBookCreator(stage, book)));
    }
}
