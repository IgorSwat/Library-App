package library.proj.gui.scenes.rating;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class RatingPanel extends HBox implements StarPanelIf {
    private final RatingHandlerIf ratingHandler;

    private final ArrayList<Star> stars = new ArrayList<>();
    private final HBox removeIcon;

    private boolean active = true;

    static private final int noStars = 5;
    static private final double starWidth = 40;
    static private final double starHeight = 35;
    static private final double spacing = 12;

    public RatingPanel(RatingHandlerIf ratingHandler) {
        this.ratingHandler = ratingHandler;

        final double originX = 0.0;
        final double originY = 0.0;

        super.setPrefHeight(starHeight);
        super.setPrefWidth(noStars * (starWidth + spacing) - spacing);
        super.getStyleClass().add("rating-panel");

        for (int i = 0; i < noStars; i++) {
            double xPos = originX + (starWidth + spacing) * i;
            Star star = new Star(xPos, originY, starWidth, starHeight);
            star.setHandlers(i, this);
            super.getChildren().add(star);
            stars.add(star);
        }

        Image image = new Image(getClass().getResource("/images/removeIcon.png").toExternalForm());
        ImageView imageView = new ImageView(image);
        imageView.setFitWidth(starWidth / 3.0);
        imageView.setFitHeight(starHeight / 3.0);
        removeIcon = new HBox();
        removeIcon.getStyleClass().add("remove-icon");
        removeIcon.getChildren().add(imageView);
        removeIcon.setOnMouseClicked(event -> handleRemove());
    }

    public void setRating(int rating) {
        activateStars(rating - 1);
        active = false;
        super.getChildren().add(removeIcon);
    }

    public void handleMouseEnter(int starId) {
        if (active)
            activateStars(starId);
    }

    public void handleMouseExit(int starId) {
        if (active) {
            for (int i = 0; i <= starId; i++)
                stars.get(i).getStyleClass().remove("star-active");
        }
    }

    public void handleClick(int starId) {
        if (active) {
            int rating = starId + 1;
            ratingHandler.handleRatingSet(rating);
            setRating(rating);
        }
    }

    public void handleRemove() {
        deactivateStars();
        super.getChildren().remove(removeIcon);
        ratingHandler.handleRatingUnset();
        active = true;
    }

    private void activateStars(int lastStar) {
        for (int i = 0; i <= lastStar; i++)
            stars.get(i).applyStyle("star-active");
    }

    private void deactivateStars() {
        for (int i = 0; i < noStars; i++)
            stars.get(i).getStyleClass().remove("star-active");
    }
}
