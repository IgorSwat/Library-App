package library.proj.gui.scenes.rating;

import javafx.scene.layout.HBox;

import java.util.ArrayList;

public class RatingPanel extends HBox implements StarPanelIf {
    private final ArrayList<Star> stars = new ArrayList<>();

    private boolean active = true;

    static private final int noStars = 5;
    static private final double starWidth = 40;
    static private final double starHeight = 35;
    static private final double spacing = 12;

    public RatingPanel() {
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
    }

    public void handleMouseEnter(int starId) {
        if (active) {
            for (int i = 0; i <= starId; i++)
                stars.get(i).applyStyle("star-active");
        }
    }

    public void handleMouseExit(int starId) {
        if (active) {
            for (int i = 0; i <= starId; i++)
                stars.get(i).getStyleClass().remove("star-active");
        }
    }

    public void handleClick(int starId) {
        handleMouseEnter(starId);
        active = false;
    }
}
