package ryleh.view.menu;

import javafx.scene.Node;
import javafx.scene.paint.Color;
import javafx.scene.text.Font;
import javafx.scene.text.Text;

public interface MenuFactory {
    /**
     * Creates a new yes or no alert window .
     * 
     * @param text The message of the alert
     */
    void createCustomAlert(String text);

    /**
     * Creates a custom button composed of a text and a side rectangle.
     * 
     * @param name        The text showed on the button
     * @param description The description of the action that the button execute
     * @param action      The action to run when the button is pressed
     * @return Node HBox which contains the button
     */
    Node createCustomButton(String name, String description, Runnable action);

    /**
     * 
     * @return A standard size scaled with screen size
     */
    int getScaledSize();

    /**
     * 
     * @return The initial color of every text of the menu
     */
    Color getStartColor();

    /**
     * 
     * @return The color to use when a text is hovered
     */
    Color getHoverColor();

    /**
     * 
     * @return The description of the action executed by a button
     */
    Text getDescription();

    /**
     * Initialize the font used by a Menu.
     * 
     * @param font The Font used by the current Menu
     */
    void setLevelFont(Font font);
}
