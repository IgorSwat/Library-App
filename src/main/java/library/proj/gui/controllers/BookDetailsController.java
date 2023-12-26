package library.proj.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import library.proj.gui.scenes.RegisterCreator;
import library.proj.gui.scenes.SceneCreator;
import library.proj.model.Book;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextField;
import javafx.stage.Stage;
import library.proj.gui.events.ChangeSceneEvent;
import library.proj.gui.scenes.BookListCreator;
import library.proj.gui.scenes.LoginCreator;
import library.proj.model.Permissions;
import library.proj.model.Person;
import library.proj.service.PersonService;
import org.springframework.context.ConfigurableApplicationContext;

//import javax.swing.text.html.ImageView;
import java.util.regex.Pattern;

public class BookDetailsController {
    private Book book;
    private  Stage stage;
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
    private void handleBackButton(){
        context.publishEvent(new ChangeSceneEvent(stage, context, previousScene));
    }

    @FXML
    private void handleBorrowBook(){
        System.out.println("wypozycz ksiazke");
    }

    public BookDetailsController(Stage stage, ConfigurableApplicationContext context, Book book, SceneCreator previousScene){
        this.book=book;
        this.stage=stage;
        this.context=context;
        this.previousScene=previousScene;
    }

    public void setFields(){
        bookTitleField.setText(book.getTitle());
        authorField.setText(book.getAuthor());
        coverField.setText(book.getCover());
        contentField.setText(book.getContents());
        statusField.setText(book.getStatus().toString());
        Image img=new Image(book.getImagePath());
        imageViewField.setImage(img);
    }
}
