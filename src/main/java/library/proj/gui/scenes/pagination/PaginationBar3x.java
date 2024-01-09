package library.proj.gui.scenes.pagination;

import javafx.scene.control.Button;

public class PaginationBar3x extends PaginationBar {
    private final Button leftButton = new Button();
    private final Button centralButton = new Button();
    private final Button rightButton = new Button();

    public PaginationBar3x(int elementsPerPage, int totalNoElements) {
        updatePageDetails(elementsPerPage, totalNoElements);

        leftButton.getStyleClass().add("pagination-button");
        centralButton.getStyleClass().add("pagination-button");
        centralButton.getStyleClass().add("pagination-central-button");
        rightButton.getStyleClass().add("pagination-button");
        super.getChildren().addAll(leftButton, centralButton, rightButton);
    }

    public void setupHandler(PaginationHandlerIf handler) {
        leftButton.setOnAction(event -> {
            updateButtonsState(currPageId - 1);
            handler.updateItemsList();
        });
        rightButton.setOnAction(event -> {
            updateButtonsState(currPageId + 1);
            handler.updateItemsList();
        });
    }

    public void updateButtonsState(int pageId) {
        leftButton.setText(pageId > 1 ? Integer.toString(pageId - 1) : "");
        leftButton.setDisable(pageId < 2);
        centralButton.setText(Integer.toString(pageId));
        rightButton.setText(pageId < maxPageId ? Integer.toString(pageId + 1) : "");
        rightButton.setDisable(pageId > maxPageId - 1);

        currPageId = pageId;
    }
}
