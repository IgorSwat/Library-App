package library.proj.gui.scenes.rating;

import javafx.scene.shape.Polygon;

public class Star extends Polygon {

    public Star(double originX, double originY, double width, double height) {
        super.getPoints().addAll(
                originX + width / 2.0, originY,
                originX + 63.0 * width / 100.0, originY + 3.0 * height / 10.0,
                originX + width, originY + height / 3.0,
                originX + 7.0 * width / 10.0, originY + 55.0 * height / 100.0,
                originX + 4.0 * width / 5.0, originY + height,
                originX + width / 2.0, originY + 7.0 * height / 10.0,
                originX + width / 5.0, originY + height,
                originX + 3.0 * width / 10.0, originY + 55.0 * height / 100.0,
                originX, originY + height / 3.0,
                originX + 37.0 * width / 100.0, originY + 3.0 * height / 10.0
        );
        super.getStyleClass().add("star");
    }

    public void setHandlers(int starId, StarPanelIf panel) {
        super.setOnMouseEntered(event -> panel.handleMouseEnter(starId));
        super.setOnMouseExited(event -> panel.handleMouseExit(starId));
        super.setOnMouseClicked(event -> panel.handleClick(starId));
    }

    public void applyStyle(String style) {
        if (!super.getStyleClass().contains(style))
            super.getStyleClass().add(style);
    }
}
