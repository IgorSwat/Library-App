package library.proj.gui.controllers;

import javafx.fxml.FXML;
import javafx.scene.control.Button;
import javafx.scene.layout.HBox;
import javafx.scene.layout.VBox;
import javafx.stage.Stage;
import library.proj.gui.scenes.navbar.NavButtonType;
import library.proj.gui.scenes.navbar.Navbar;
import library.proj.gui.scenes.objects.ReservationEntry;
import library.proj.gui.scenes.pagination.PaginationBar3x;
import library.proj.gui.scenes.pagination.PaginationHandlerIf;
import library.proj.model.Reservation;
import library.proj.service.ReservationsService;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.List;

public class MyReservationsController extends NavbarController implements PaginationHandlerIf {
    private final ReservationsService reservationsService;
    private List<Reservation> reservations = null;
    @FXML
    private HBox navbarField;
    private Navbar navbar = null;
    @FXML
    private HBox paginationField;
    private PaginationBar3x pagination = null;
    @FXML
    private VBox reservationList;
    private static final int itemsPerPage = 8;
    public MyReservationsController(Stage stage, ConfigurableApplicationContext context) {
        super(stage, context);
        this.reservationsService = context.getBean(ReservationsService.class);

        loadReservations();
    }
    public void setupNavbar() {
        navbar = new Navbar("Moje rezerwacje", NavButtonType.BOOK_LIST_BUTTON, NavButtonType.PROFILE_BUTTON, NavButtonType.LOGOUT_BUTTON);
        navbar.linkHandlers(this);
        navbarField.getChildren().add(navbar);
    }

    public void setupPagination() {
        pagination = new PaginationBar3x(itemsPerPage, reservations.size());
        pagination.setupHandler(this);
        paginationField.getChildren().add(pagination);
    }

    public void loadReservations(){
        reservations = reservationsService.getAllReservationsByPerson(LoginController.loggedAccount);
    }

    public void updateItemsList() {
        reservationList.getChildren().clear();

        int lowerBound = pagination.getPageLowerBound();
        int upperBound = pagination.getPageUpperBound();
        int id = lowerBound + 1;

        for (int i = lowerBound; i < upperBound; i++) {
            Reservation reservation = reservations.get(i);
            ReservationEntry entry = new ReservationEntry(id, reservation);
            Button acceptButton = entry.getCancelButton();
            acceptButton.setOnAction(event -> handleCancelReservation(entry.getReservation()));
            reservationList.getChildren().add(entry);
            id++;
        }
    }

    public void handleCancelReservation(Reservation reservation){
        reservation.setActive(false);
        reservationsService.updateReservationActiveness(reservation, false);
        updateItemsList();
    }
}
