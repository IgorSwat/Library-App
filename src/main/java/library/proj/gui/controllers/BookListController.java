package library.proj.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.ListView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import library.proj.gui.scenes.objects.BookEntry;
import library.proj.model.Book;
import library.proj.service.BooksService;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class BookListController {
    private final Stage stage;
    private final ConfigurableApplicationContext context;
    private final BooksService booksService;

    @FXML
    private VBox bookList;

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
            }
            BookEntry entry = new BookEntry(book);
            row.getChildren().add(entry);
        }
        bookList.getChildren().add(row);
    }

}
