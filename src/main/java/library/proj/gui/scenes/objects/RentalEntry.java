package library.proj.gui.scenes.objects;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import library.proj.model.Rental;

public class RentalEntry extends GridPane {
    private final Label index;
    private final Label userName;
    private final Label bookTitle;
    private final Label start;
    private final Label end;
    private final Button acceptButton;

    private int nextCol = 0;

    private static final double acceptButtonWidth = 21.0;
    private static final double acceptButtonHeight = 21.0;

    public RentalEntry(int index, Rental rental) {
        this.index = new Label();
        this.index.setText(Integer.toString(index));
        this.index.getStyleClass().add("rental-info-label");

        userName = new Label();
        userName.setText(rental.getPerson().getFullName());
        userName.getStyleClass().add("rental-info-label");

        bookTitle = new Label();
        bookTitle.setText(rental.getBook().getTitle());
        bookTitle.getStyleClass().add("rental-info-label");

        start = new Label();
        start.setText(rental.getRentalDate().toString());
        start.getStyleClass().add("rental-info-label");

        end = new Label();
        end.setText(rental.getReturnDate().toString());
        end.getStyleClass().add("rental-info-label");

        acceptButton = new Button();
        Image image = new Image(getClass().getResource("/images/acceptIcon.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(acceptButtonHeight);
        imageView.setFitWidth(acceptButtonWidth);
        acceptButton.setGraphic(imageView);
        acceptButton.getStyleClass().add("filter-button");
        acceptButton.getStyleClass().add("rental-accept-button");

        super.getStyleClass().add("rental-entry");
        super.setHgap(8.0);
        addGridElement(this.index, 30.0);
        addGridElement(userName, 170.0);
        addGridElement(bookTitle, 220.0);
        addGridElement(start, 100.0);
        addGridElement(end, 100.0);
        addGridElement(acceptButton, 30.0);
    }

    private void addGridElement(Node element, double columnWidth) {
        ColumnConstraints column = new ColumnConstraints();
        column.setPrefWidth(columnWidth);
        super.getColumnConstraints().add(column);
        super.add(element, nextCol, 0);
        super.setAlignment(Pos.CENTER);
        nextCol++;
    }

}
