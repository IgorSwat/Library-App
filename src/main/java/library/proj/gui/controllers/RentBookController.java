package library.proj.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.stage.Stage;
import library.proj.model.Book;
import library.proj.service.BooksService;
import library.proj.service.RentalsService;
import org.springframework.context.ConfigurableApplicationContext;


public class RentBookController {
    private final Stage primaryStage;
    private final Stage stage;
    private final ConfigurableApplicationContext context;
    private final BooksService booksService;
    private final RentalsService rentalsService;
    private final Book book;
    @FXML
    private Label titleLabel;
    @FXML
    private Label authorLabel;

    public RentBookController(Stage primaryStage, Stage stage, ConfigurableApplicationContext context, Book book) {
        this.primaryStage = primaryStage;
        this.stage = stage;
        this.context = context;
        this.book = book;
        this.booksService = context.getBean(BooksService.class);
        this.rentalsService = context.getBean(RentalsService.class);
    }

    @FXML
    public void handleRentClick() {
        System.out.println("Kliknięto wypożycz " + book.getTitle());
    }

    public void initialize() {
        titleLabel.setText(book.getTitle());
        authorLabel.setText(book.getAuthor());
    }
}
