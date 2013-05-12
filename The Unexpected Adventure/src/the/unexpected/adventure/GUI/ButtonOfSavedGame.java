/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure.GUI;

/**
 *
 * @author Dominik
 */
public class ButtonOfSavedGame extends MyButton {

    private long date;
    private String nameOfSG;

    public ButtonOfSavedGame(String imageName2, int coordX, int coordY, int width, int height, long date) {
        super("showInfoOfSG", null, imageName2, coordX, coordY, width, height);
        this.date = date;
    }

    public String getNameOfSG() {
        return nameOfSG;
    }

    public void setNameOfSG(String nameOfSG) {
        this.nameOfSG = nameOfSG;
    }

    public long getDate() {
        return date;
    }
}
