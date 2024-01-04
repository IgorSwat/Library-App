package library.proj.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.CheckBox;
import javafx.scene.control.TextField;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import library.proj.gui.scenes.navbar.NavButtonType;
import library.proj.gui.scenes.navbar.Navbar;
import library.proj.gui.scenes.objects.RentalEntry;
import library.proj.model.Rental;
import library.proj.service.RentalsService;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class LibrarianPanelController extends NavbarController {
    private final RentalsService rentalsService;

    @FXML
    private HBox navbarField;
    private Navbar navbar = null;

    @FXML
    private TextField userNameField;
    @FXML
    private TextField bookTitleField;
    @FXML
    private CheckBox expiredRentalsCheck;
    @FXML
    private VBox rentalList;


    public LibrarianPanelController(Stage stage, ConfigurableApplicationContext context) {
        super(stage, context);
        this.rentalsService = context.getBean(RentalsService.class);
    }

    public void setupNavbar() {
        navbar = new Navbar("Wypożyczenia", NavButtonType.BOOK_LIST_BUTTON, NavButtonType.PROFILE_BUTTON, NavButtonType.LOGOUT_BUTTON);
        navbar.linkHandlers(this);
        navbarField.getChildren().add(navbar);
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
}
