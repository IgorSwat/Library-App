package library.proj.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import library.proj.gui.events.ChangeSceneEvent;
import library.proj.gui.scenes.BookDetailsCreator;
import library.proj.gui.scenes.BookListCreator;
import library.proj.gui.scenes.MyRentalsCreator;
import library.proj.gui.scenes.objects.BookEntry;
import library.proj.model.Book;
import library.proj.model.Rental;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.ArrayList;
import java.util.List;

public class MyRentalsController {

    private Stage stage;
    private ConfigurableApplicationContext context;

    @FXML
    private VBox vBoxContainer;


    public MyRentalsController(Stage stage, ConfigurableApplicationContext context) {
        this.stage=stage;
        this.context=context;
    }

    @FXML
    private void handleBackButton(){
        context.publishEvent(new ChangeSceneEvent(stage, context, new BookListCreator()));
    }

    public void fillContainer(){
        List<Rental> rentals=LoginController.loggedAccount.getRentals();
        List<Book> books=new ArrayList<>();
        for(Rental rental: rentals){
            books.add(rental.getBook());
        }
        for(Book book:books){
            BookEntry bookEntry=new BookEntry(book);
            bookEntry.setOnMouseClicked(mouseEvent -> handleBookDetailsClicked(mouseEvent,book));
            vBoxContainer.getChildren().add(bookEntry);
        }
    }

    public void handleBookDetailsClicked(MouseEvent event, Book book){
        context.publishEvent(new ChangeSceneEvent(stage,context, new BookDetailsCreator(book)));
    }

}

