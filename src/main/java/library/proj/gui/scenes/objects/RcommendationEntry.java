package library.proj.gui.scenes.objects;

import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import library.proj.model.Book;

public class RcommendationEntry extends HBox {
    private final Image image;
    private final ImageView imageView;
    private final Label title;
    private final Label author;

    private static final double width = 700.0;
    private static final double height = 80.0;

    private static final double iconSize = 70.0;

    public RcommendationEntry(Book book) {
        // Root & background
        setPrefWidth(width);
        setPrefHeight(height);
        super.getStyleClass().add("recommendation-entry");

        // Book icon
        image = new Image(getClass().getResource(book.getImagePath()).toExternalForm());
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

        VBox authorBox = new VBox();
        authorBox.getChildren().addAll(title, author);
        authorBox.getStyleClass().add("author-box");
        // Arrangement of elements
        super.getChildren().addAll(imageView, authorBox);
    }
}
