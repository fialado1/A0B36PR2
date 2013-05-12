package the.unexpected.adventure.GUI;

import java.util.ArrayList;
import javax.swing.JComponent;

/**
 *
 * @author Dominik
 */
public class BoxButton extends MyButton {

    private ArrayList<JComponent> listOfItems = new ArrayList<>();

    public BoxButton(String nameOfCommand, String imageName1, String imageName2, int coordX, int coordY, int width, int height) {
        super(nameOfCommand, imageName1, imageName2, coordX, coordY, width, height);
    }

    public void addCompToTheBox(ItemButton ib) {
        listOfItems.add(ib);
    }

    public ArrayList<JComponent> getListOfItems() {
        return listOfItems;
    }

    public void clearListOfItems() {
        this.listOfItems.clear();
    }

    public JComponent getItemFromList(int i) {
        return listOfItems.get(i);
    }
}
