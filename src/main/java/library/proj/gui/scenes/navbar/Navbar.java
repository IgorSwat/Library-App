package library.proj.gui.scenes.navbar;

import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Priority;
import library.proj.gui.controllers.NavbarController;

/*

 ||| A quick tutorial to Navbar class |||

 Adding a navbar to some view consists of the following steps:
 1. Create a navbar root in FXML file (just a simple empty Hbox, see example in librarianPanelVie.fxml)
 2. Define setupNavbar() method in the controller class (see example in LibrarianPanelController). During creation of navbar
    you should specify which buttons should be contained by it - look at NavButtonType enum for more details.
 3. Run setupNavbar() method inside createScene() from your scene creator class (see example in LibrarianPanelCreator)
 4. *** If you want to manipulate with single button of Navbar, you can always use getButton() method with
    the correct button type you are interested in.

If you want to add new button with completely new functionality, you should follow these steps:
1. Add another value to NavButtonType enum
2. Add another handler method to NavbarControllerIf and it's implementation to NavbarController class
3. Handle the added value case in NavButtonFactory class. You need to handle both creation of button (specify image path)
   and linking it with the right click handler (if you want the button to do something :) )

 */

public class Navbar extends HBox {
    private final int noButtons = NavButtonType.values().length;
    private final Button[] buttons = new Button[noButtons];

    private static final NavButtonFactory buttonsFactory = new NavButtonFactory();

    public Navbar(String name, NavButtonType... buttonTypes) {
        super.getStyleClass().add("books-navbar");

        Label viewName = new Label();
        viewName.setText(name);
        viewName.getStyleClass().add("view-title");

        HBox buttonsField = new HBox();
        buttonsField.getStyleClass().add("buttons-panel");
        setHgrow(buttonsField, Priority.ALWAYS);
        for (NavButtonType type : buttonTypes) {
            Button button = buttonsFactory.createButton(type);
            buttons[type.ordinal()] = button;
            buttonsField.getChildren().add(button);
        }

        super.getChildren().addAll(viewName, buttonsField);
    }

    public void linkHandlers(NavbarController controller) {
        for (int i = 0; i < noButtons; i++) {
            Button button = buttons[i];
            if (button != null)
                buttonsFactory.linkHandler(button, NavButtonType.values()[i], controller);
        }
    }

    public Button getButton(NavButtonType type) {
        return buttons[type.ordinal()];
    }
}
