package library.proj.gui.scenes.navbar;

import javafx.scene.control.Button;
import javafx.scene.control.Tooltip;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import library.proj.gui.controllers.NavbarController;

import java.net.URL;

public class NavButtonFactory {

    // I decided to move this functionality to NavButtonFactory as it is some part of building a navigation button
    public Button linkHandler(Button button, NavButtonType type, NavbarController controller) {
        switch (type) {
            case LOGOUT_BUTTON -> button.setOnAction(event -> controller.handleLogout());
            case PROFILE_BUTTON -> button.setOnAction(event -> controller.handleUserClicked());
            case BOOK_LIST_BUTTON -> button.setOnAction(event -> controller.handleBookListRedirect());
            case RENTALS_BUTTON -> button.setOnAction(event -> controller.handleRentalListRedirect());
            case ADD_BOOK_BUTTON -> button.setOnAction(event -> controller.handleAddBookClick());
            case RECOMMENDATIONS_BUTTON -> button.setOnAction(event -> controller.handleRecommendationsClick());
        }
        return button;
    }

    public Button createButton(NavButtonType type) {
        double buttonHeight = 30.0;
        double buttonWidth = 26;

        return switch (type) {  // Nie wiadomo czy pierwsze / jest potrzebne
            case LOGOUT_BUTTON -> createButton(buttonHeight, buttonWidth, "images/logoutIcon.png", "Wyloguj");
            case PROFILE_BUTTON -> createButton(buttonHeight, buttonWidth, "images/userIcon.png", "Profil");
            case BOOK_LIST_BUTTON -> createButton(buttonHeight, buttonWidth, "images/bookIcon.png", "Lista książek");
            case RENTALS_BUTTON -> createButton(buttonHeight, buttonWidth, "images/bookingIcon.png", "Wypożyczenia");
            case ADD_BOOK_BUTTON -> createButton(buttonHeight, buttonWidth, "images/plusIcon.png", "Dodaj książkę");
            case RECOMMENDATIONS_BUTTON -> createButton(buttonHeight, buttonWidth, "images/recommendationsIcon.png", "Rekomendacje");
        };
    }

    private Button createButton(double height, double width, String iconPath, String tooltipText) {
        Button button = new Button();
        button.getStyleClass().add("nav-button");
        button.setTooltip(new Tooltip(tooltipText));

        ClassLoader classLoader = getClass().getClassLoader();
        URL iconUrl = classLoader.getResource(iconPath);
        if (iconUrl == null)
            throw new RuntimeException("Invalid image path: " + iconPath);

        Image image = new Image(iconUrl.toExternalForm());
        ImageView imageView = new ImageView();
        imageView.setImage(image);
        imageView.setFitHeight(height);
        imageView.setFitWidth(width);
        button.setGraphic(imageView);

        return button;
    }
}
