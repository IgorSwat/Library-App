package library.proj.gui.controllers;

import javafx.stage.Stage;
import library.proj.gui.events.ChangeSceneEvent;
import library.proj.gui.events.OpenDialogEvent;
import library.proj.gui.scenes.*;
import org.springframework.context.ConfigurableApplicationContext;

public abstract class NavbarController {
    protected final Stage stage;
    protected final ConfigurableApplicationContext context;

    public NavbarController(Stage stage, ConfigurableApplicationContext context) {
        this.stage = stage;
        this.context = context;
    }

    public abstract void setupNavbar();

    public void handleAddBookClick() {
        context.publishEvent(new OpenDialogEvent("Dodawanie książki", 360, 500,
                stage, context, new AddBookCreator(stage)));
    }

    public void handleRentalListRedirect() {
        context.publishEvent(new ChangeSceneEvent(stage, context, new LibrarianPanelCreator()));
    }

    public void handleBookListRedirect() {
        context.publishEvent(new ChangeSceneEvent(stage, context, new BookListCreator()));
    }

    public void handleUserClicked() {
        context.publishEvent(new ChangeSceneEvent(stage, context, new MyRentalsCreator()));
    }

    public void handleReservationsClick() {
        context.publishEvent(new ChangeSceneEvent(stage, context, new MyReservationsCreator()));
    }

    public void handleLogout() {
        context.publishEvent(new ChangeSceneEvent(stage, context, new LoginCreator()));
        LoginController.loggedAccount = null;
    }
}
