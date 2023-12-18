package library.proj.gui.scenes.objects;

import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;
import javafx.scene.layout.VBox;
import library.proj.model.Book;

public class BookEntry extends VBox {
    private final Image image;
    private final ImageView imageView;
    private final Label title;
    private final Label author;

    private static final double entrySize = 120.0;
    private static final double iconSize = 40.0;

    public BookEntry(Book book) {
        // Root & background
        setPrefWidth(entrySize);
        setPrefHeight(entrySize);
        super.getStyleClass().add("rounded-button");

        // Book icon
        image = new Image(getClass().getResource("/images/bookIcon.png").toExternalForm());
        imageView = new ImageView(image);
        imageView.setFitHeight(iconSize);
        imageView.setFitWidth(iconSize);

        // Book info
        title = new Label();
        title.setText(book.getTitle());
        title.getStyleClass().add("book-title");
        author = new Label();
        author.setText(book.getAuthor());
        author.getStyleClass().add("book-author");

        // Arrangement of elements
        super.getChildren().addAll(imageView, title, author);
    }
}
