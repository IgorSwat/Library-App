package library.proj.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import library.proj.gui.events.OpenDialogEvent;
import library.proj.gui.scenes.*;
import library.proj.model.Book;
import javafx.stage.Stage;
import library.proj.gui.events.ChangeSceneEvent;
import library.proj.model.Permissions;
import org.springframework.context.ConfigurableApplicationContext;


public class BookDetailsController {
    private Book book;
    private Stage stage;
    private ConfigurableApplicationContext context;
    private SceneCreator previousScene;

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

    @FXML
    private void handleBackButton() {
        context.publishEvent(new ChangeSceneEvent(stage, context, previousScene));
    }

    @FXML
    private void handleBorrowBook() {
        if (Permissions.values()[LoginController.loggedAccount.getPermissions()] == Permissions.USER) {
            return;
        }
        context.publishEvent(new OpenDialogEvent("Wypożyczanie książki", 360, 500,
                stage, context, new RentBookCreator(stage, book)));
    }

    public BookDetailsController(Stage stage, ConfigurableApplicationContext context, Book book, SceneCreator previousScene) {
        this.book = book;
        this.stage = stage;
        this.context = context;
        this.previousScene = previousScene;
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
}
