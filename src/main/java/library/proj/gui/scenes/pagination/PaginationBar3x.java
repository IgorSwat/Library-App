package library.proj.gui.scenes.pagination;

import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

public class PaginationBar3x extends HBox {
    private int currPageId = 1;     // 1 mean starting page (items with ids from 0 to elementsPerPage - 1)

    private int elementsPerPage;
    private int totalNoElements;
    private int maxPageId;

    private final Button leftButton = new Button();
    private final Button centralButton = new Button();
    private final Button rightButton = new Button();

    public PaginationBar3x(int elementsPerPage, int totalNoElements) {
        super.getStyleClass().add("pagination-panel");
        setHgrow(this, Priority.ALWAYS);

        updatePageDetails(elementsPerPage, totalNoElements);

        leftButton.getStyleClass().add("pagination-button");
        centralButton.getStyleClass().add("pagination-button");
        centralButton.getStyleClass().add("pagination-central-button");
        rightButton.getStyleClass().add("pagination-button");
        super.getChildren().addAll(leftButton, centralButton, rightButton);
    }

    public void updatePageDetails(int elementsPerPage, int totalNoElements) {
        this.elementsPerPage = elementsPerPage;
        this.totalNoElements = totalNoElements;
        this.maxPageId = Math.max(1, (int)Math.ceil((double)totalNoElements / elementsPerPage));

        if (getPageLowerBound() >= totalNoElements)
            this.currPageId = maxPageId;
        updateButtonsState(currPageId);
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

    public int getPageLowerBound() {
        return elementsPerPage * (currPageId - 1);
    }

    public int getPageUpperBound() {
        return Math.min(elementsPerPage * currPageId, totalNoElements);
    }

    private void updateButtonsState(int pageId) {
        leftButton.setText(pageId > 1 ? Integer.toString(pageId - 1) : "");
        leftButton.setDisable(pageId < 2);
        centralButton.setText(Integer.toString(pageId));
        rightButton.setText(pageId < maxPageId ? Integer.toString(pageId + 1) : "");
        rightButton.setDisable(pageId > maxPageId - 1);

        currPageId = pageId;
    }
}
