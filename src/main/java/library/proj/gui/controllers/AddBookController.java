package library.proj.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.*;
import javafx.stage.Stage;
import library.proj.gui.events.ChangeSceneEvent;
import library.proj.gui.scenes.BookListCreator;
import library.proj.model.Book;
import library.proj.model.Status;
import library.proj.service.BooksService;
import org.springframework.context.ConfigurableApplicationContext;

public class AddBookController {
    private final Stage primaryStage;
    private final Stage stage;
    private final ConfigurableApplicationContext context;
    private final BooksService booksService;

    @FXML
    private TextField titleField;
    @FXML
    private TextField authorField;
    @FXML
    private TextArea descriptionField;
    @FXML
    private ToggleGroup toggleGroup;
    @FXML
    private Label errorLabel;

    public AddBookController(Stage primaryStage, Stage stage, ConfigurableApplicationContext context) {
        this.primaryStage = primaryStage;
        this.stage = stage;
        this.context = context;
        this.booksService = context.getBean(BooksService.class);
    }

    @FXML
    public void handleAddClick() {
        String title = titleField.getText();
        String author = authorField.getText();
        String cover = "-";
        String description = descriptionField.getText();
        RadioButton clickedButton = (RadioButton)toggleGroup.getSelectedToggle();
        Status status = clickedButton.getText().equals("Dostępne") ? Status.AVAILABLE : Status.NOT_AVAILABLE;

        if (validateInput(title, author)) {
            Book book = new Book(title, author, cover, description, status,"");
            booksService.createBook(book);
            context.publishEvent(new ChangeSceneEvent(primaryStage, context, new BookListCreator()));
            stage.close();
        }
    }

    private boolean validateInput(String title, String author) {
        if (title.isEmpty() || author.isEmpty()) {
            errorLabel.setText("Pola \"tytuł\" i \"autor\" nie mogą być puste");
            return false;
        }
        else if (title.length() < 3 || author.length() < 3) {
            errorLabel.setText("Pola \"tytuł\" i \"autor\" muszą liczyć co najmniej 3 znaki");
            return false;
        }
        return true;
    }
}
