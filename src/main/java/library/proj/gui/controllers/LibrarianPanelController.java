package library.proj.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import library.proj.gui.events.ChangeSceneEvent;
import library.proj.gui.scenes.BookListCreator;
import library.proj.gui.scenes.LoginCreator;
import library.proj.gui.scenes.MyRentalsCreator;
import library.proj.gui.scenes.objects.RentalEntry;
import library.proj.model.Rental;
import library.proj.service.RentalsService;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class LibrarianPanelController implements NavbarControllerIf {
    private final Stage stage;
    private final ConfigurableApplicationContext context;
    private final RentalsService rentalsService;

    @FXML
    private TextField userNameField;
    @FXML
    private TextField bookTitleField;
    @FXML
    private CheckBox expiredRentalsCheck;
    @FXML
    private VBox rentalList;


    public LibrarianPanelController(Stage stage, ConfigurableApplicationContext context) {
        this.stage = stage;
        this.context = context;
        this.rentalsService = context.getBean(RentalsService.class);
    }

    public void updateRentalList() {
        rentalList.getChildren().clear();

        List<Rental> rentals = rentalsService.getCurrentRentals();
        int id = 1;
        for (Rental rental : rentals) {
            RentalEntry entry = new RentalEntry(id, rental);
            rentalList.getChildren().add(entry);
            id++;
        }
    }

    @FXML
    public void handleFilterRentals() {

    }

    @FXML
    public void handleBookListRedirect() {
        context.publishEvent(new ChangeSceneEvent(stage, context, new BookListCreator()));
    }

    @FXML
    public void handleUserClicked() {
        context.publishEvent(new ChangeSceneEvent(stage, context, new MyRentalsCreator()));
    }

    @FXML
    public void handleLogout() {
        context.publishEvent(new ChangeSceneEvent(stage, context, new LoginCreator()));
        LoginController.loggedAccount = null;
    }
}
