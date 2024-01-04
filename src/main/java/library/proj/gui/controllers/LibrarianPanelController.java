package library.proj.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
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

    private List<Rental> currentRentals = null;
    private List<Rental> filteredRentals = null;

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

    public void updateCurrentRentals() {
        currentRentals = rentalsService.getCurrentRentals();
        filteredRentals = currentRentals;
    }

    public void updateRentalList() {
        rentalList.getChildren().clear();

        int id = 1;
        for (Rental rental : filteredRentals) {
            RentalEntry entry = new RentalEntry(id, rental);
            Button acceptButton = entry.getAcceptButton();
            acceptButton.setOnAction(event -> handleReturnRental(entry.getRental()));
            rentalList.getChildren().add(entry);
            id++;
        }
    }

    public void handleReturnRental(Rental rental) {
        rentalsService.updateRentalStatus(rental.getId(), true);

        // TODO: optimize to remove element from local list and not access the database each time
        updateCurrentRentals();
        handleFilterRentals();
    }

    @FXML
    public void handleFilterRentals() {
        boolean onlyExpired = expiredRentalsCheck.isSelected();
        String person = userNameField.getText();
        String title = bookTitleField.getText();

        filteredRentals = currentRentals;
        if (onlyExpired)
            filteredRentals = currentRentals.stream().filter(Rental::isExpired).toList();
        if (!person.isEmpty())
            filteredRentals = filteredRentals.stream().filter(rental -> rental.getPerson().getFullName().contains(person)).toList();
        if (!title.isEmpty())
            filteredRentals = filteredRentals.stream().filter(rental -> rental.getBook().getTitle().startsWith(title)).toList();

        updateRentalList();
    }
}
