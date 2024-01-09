package library.proj.gui.scenes.pagination;

import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;

// This abstract class makes it easier to create different pagination bars with different amount of buttons
public abstract class PaginationBar extends HBox {
    protected int currPageId = 1;

    protected int elementsPerPage = 1;
    protected int totalNoElements = 1;
    protected int maxPageId = 1;

    public PaginationBar() {
        super.getStyleClass().add("pagination-panel");
        setHgrow(this, Priority.ALWAYS);

        // Remember to call updatePageDetails in derived classes constructors!
    }

    public abstract void setupHandler(PaginationHandlerIf handler);

    public void updatePageDetails(int elementsPerPage, int totalNoElements) {
        this.elementsPerPage = elementsPerPage;
        this.totalNoElements = totalNoElements;
        this.maxPageId = Math.max(1, (int)Math.ceil((double)totalNoElements / elementsPerPage));

        if (getPageLowerBound() >= totalNoElements)
            this.currPageId = maxPageId;
        updateButtonsState(currPageId);
    }

    public abstract void updateButtonsState(int pageId);

    public int getPageLowerBound() {
        return elementsPerPage * (currPageId - 1);
    }

    public int getPageUpperBound() {
        return Math.min(elementsPerPage * currPageId, totalNoElements);
    }
}
