package library.proj.gui.scenes.objects;

import javafx.geometry.Pos;
import javafx.scene.Node;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import library.proj.model.Reservation;
import lombok.Getter;

public class ReservationEntry extends GridPane {

    @Getter
    private final Reservation reservation;

    private final Label index;
    private final Label userName;
    private final Label bookTitle;
    private final Label start;
    private final Label end;
    @Getter
    private final Button cancelButton;

    private int nextCol = 0;

    private static final double cancelButtonWidth = 20.0;
    private static final double cancelButtonHeight = 20.0;

    public ReservationEntry(int index, Reservation reservation) {
        super();
        this.reservation = reservation;

        this.index = new Label();
        this.index.setText(Integer.toString(index));
        this.index.getStyleClass().add("rental-info-label");

        userName = new Label();
        userName.setText(reservation.getPerson().getFullName());
        userName.getStyleClass().add("rental-info-label");

        bookTitle = new Label();
        bookTitle.setText(reservation.getBook().getTitle());
        bookTitle.getStyleClass().add("rental-info-label");

        start = new Label();
        start.setText(reservation.getStartDate().toString());
        start.getStyleClass().add("rental-info-label");

        end = new Label();
        end.setText(reservation.getEndDate().toString());
        end.getStyleClass().add("rental-info-label");

        cancelButton = new Button();
        Image image = new Image(getClass().getResource("/images/removeIcon.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitHeight(cancelButtonHeight);
        imageView.setFitWidth(cancelButtonWidth);
        cancelButton.setGraphic(imageView);
        cancelButton.getStyleClass().add("filter-button");
        cancelButton.getStyleClass().add("rental-accept-button");

        if (!reservation.isActive()) {
            this.setDisabled(true);
            cancelButton.setDisable(true);
            this.getStyleClass().add("disabled-pane-background");
        }

        super.getStyleClass().add("rental-entry");
        super.setHgap(8.0);
        addGridElement(this.index, 30.0);
        addGridElement(userName, 170.0);
        addGridElement(bookTitle, 220.0);
        addGridElement(start, 100.0);
        addGridElement(end, 100.0);
        addGridElement(cancelButton, 30.0);
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
