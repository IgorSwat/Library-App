package library.proj.gui.controllers;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.chart.*;
import javafx.scene.control.Button;
import javafx.scene.control.ComboBox;
import javafx.scene.layout.HBox;
import javafx.stage.Stage;
import library.proj.gui.scenes.navbar.NavButtonType;
import library.proj.gui.scenes.navbar.Navbar;
import library.proj.model.Permissions;
import library.proj.service.BooksService;
import library.proj.service.RentalsService;
import org.springframework.context.ConfigurableApplicationContext;

import java.util.Map;

public class StatsController extends NavbarController{

    private Stage stage;
    private ConfigurableApplicationContext context;
    private RentalsService rentalsService;
    private BooksService booksService;
    @FXML
    private HBox navbarField;
    private Navbar navbar;

    @FXML
    private ComboBox comboBox;

    @FXML
    private HBox chartHolder;
    public StatsController(Stage stage, ConfigurableApplicationContext context) {
        super(stage, context);
        this.context=context;
        this.stage=stage;
        this.rentalsService=context.getBean(RentalsService.class);
        this.booksService=context.getBean(BooksService.class);
    }

    public void setup(){
        makeComboBox();
    }

    @Override
    public void setupNavbar() {
        navbar = new Navbar("Statystyki", NavButtonType.BOOK_LIST_BUTTON,
                NavButtonType.RENTALS_BUTTON, NavButtonType.PROFILE_BUTTON, NavButtonType.LOGOUT_BUTTON);
        navbar.linkHandlers(this);
        navbarField.getChildren().add(navbar);

        boolean hasPermissions = LoginController.loggedAccount.getPermissions() != Permissions.USER;
        Button rentalListButton = navbar.getButton(NavButtonType.RENTALS_BUTTON);
        rentalListButton.setDisable(!hasPermissions);
    }

    private void makeComboBox(){
        ObservableList<String> options= FXCollections.observableArrayList("Wypożyczenia w ostatnich 12 miesiącach", "Najbardziej aktywni czytacze", "Wypożyczone książki");
        comboBox.setItems(options);
        comboBox.setOnAction(event -> {
            int selected=comboBox.getSelectionModel().getSelectedIndex();
            switch (selected){
                case 0:
                    chartHolder.getChildren().clear();
                    chartHolder.getChildren().add(getAnnualRentals());
                    break;
                case 1:
                    chartHolder.getChildren().clear();
                    chartHolder.getChildren().add(getBestReader());
                    break;
                case 2:
                    chartHolder.getChildren().clear();
                    chartHolder.getChildren().add(rentedBookRatio());
                    break;
            }
        });
    }

    private Chart getAnnualRentals(){
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        Map<String, Long> map= rentalsService.getRentalsCountInLastYearGroupedByMonth();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for(Map.Entry<String, Long> entry: map.entrySet()){
            series.getData().add(new XYChart.Data<>(entry.getKey(),entry.getValue()));
        }

        barChart.getData().add(series);
        return barChart;
    }

    private Chart getBestReader(){
        CategoryAxis xAxis = new CategoryAxis();
        NumberAxis yAxis = new NumberAxis();
        BarChart<String, Number> barChart = new BarChart<>(xAxis, yAxis);

        Map<String, Long> map= rentalsService.getBestReader();

        XYChart.Series<String, Number> series = new XYChart.Series<>();
        for(Map.Entry<String, Long> entry: map.entrySet()){
            series.getData().add(new XYChart.Data<>(entry.getKey(),entry.getValue()));
        }

        barChart.getData().add(series);
        return barChart;
    }

    private Chart rentedBookRatio(){
        long rentedBooks= rentalsService.getRentedBook();
        long allBooks=booksService.getAllBooks().size();

        PieChart pie=new PieChart();
        PieChart.Data slice1=new PieChart.Data("Wypożyczone",rentedBooks);
        PieChart.Data slice2=new PieChart.Data("Nie wypożyczone",allBooks-rentedBooks);

        pie.getData().add(slice1);
        pie.getData().add(slice2);

        return pie;
    }
}
