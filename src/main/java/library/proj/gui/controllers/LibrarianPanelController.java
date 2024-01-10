package library.proj.gui.controllers;

import jakarta.mail.MessagingException;
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
import library.proj.gui.scenes.pagination.PaginationBar3x;
import library.proj.gui.scenes.pagination.PaginationHandlerIf;
import library.proj.model.Person;
import library.proj.model.Rental;
import library.proj.service.RentalsService;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class LibrarianPanelController extends NavbarController implements PaginationHandlerIf {
    private final RentalsService rentalsService;

    private List<Rental> currentRentals = null;
    private List<Rental> filteredRentals = null;

    @FXML
    private HBox navbarField;
    private Navbar navbar = null;

    @FXML
    private HBox paginationField;
    private PaginationBar3x pagination = null;

    @FXML
    private TextField userNameField;
    @FXML
    private TextField bookTitleField;
    @FXML
    private CheckBox expiredRentalsCheck;
    @FXML
    private VBox rentalList;

    private static final int itemsPerPage = 8;

    public LibrarianPanelController(Stage stage, ConfigurableApplicationContext context) {
        super(stage, context);
        this.rentalsService = context.getBean(RentalsService.class);

        loadCurrentRentals();
    }

    public void setupNavbar() {
        navbar = new Navbar("Wypożyczenia", NavButtonType.BOOK_LIST_BUTTON, NavButtonType.PROFILE_BUTTON, NavButtonType.LOGOUT_BUTTON);
        navbar.linkHandlers(this);
        navbarField.getChildren().add(navbar);
    }

    public void setupPagination() {
        pagination = new PaginationBar3x(itemsPerPage, currentRentals.size());
        pagination.setupHandler(this);
        paginationField.getChildren().add(pagination);
    }

    public void loadCurrentRentals() {
        currentRentals = rentalsService.getCurrentRentals();
        filteredRentals = currentRentals;
    }

    public void updateItemsList() {
        rentalList.getChildren().clear();

        int lowerBound = pagination.getPageLowerBound();
        int upperBound = pagination.getPageUpperBound();
        int id = lowerBound + 1;

        for (int i = lowerBound; i < upperBound; i++) {
            Rental rental = filteredRentals.get(i);
            RentalEntry entry = new RentalEntry(id, rental);
            Button acceptButton = entry.getAcceptButton();
            acceptButton.setOnAction(event -> handleReturnRental(entry.getRental()));
            rentalList.getChildren().add(entry);
            id++;
        }
    }

    public void handleReturnRental(Rental rental) {
        rentalsService.updateRentalStatus(rental.getId(), true);
        try {
            rental.getBook().notifyPerson();
        } catch (javax.mail.MessagingException e) {
            System.out.println("nie wyslano");
            System.out.println(e);
            throw new RuntimeException(e);
        }
        // TODO: optimize to remove element from local list and not access the database each time
        loadCurrentRentals();
        handleFilterRentals();
    }

    @FXML
    public void handleFilterRentals() {
        boolean onlyExpired = expiredRentalsCheck.isSelected();
        String person = userNameField.getText().toLowerCase();
        String title = bookTitleField.getText().toLowerCase();

        filteredRentals = currentRentals;
        if (onlyExpired)
            filteredRentals = currentRentals.stream().filter(Rental::isExpired).toList();
        if (!person.isEmpty())
            filteredRentals = filteredRentals.stream().filter(rental -> rental.getPerson().getFullName().toLowerCase().contains(person)).toList();
        if (!title.isEmpty())
            filteredRentals = filteredRentals.stream().filter(rental -> rental.getBook().getTitle().toLowerCase().startsWith(title)).toList();

        pagination.updatePageDetails(itemsPerPage, filteredRentals.size());
        updateItemsList();
    }
}
