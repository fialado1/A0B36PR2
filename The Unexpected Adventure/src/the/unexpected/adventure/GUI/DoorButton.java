/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure.GUI;

/**
 *
 * @author Dominik
 */
public class DoorButton extends MyButton {
    //0-N, 1-S, 2-E, 3-W

    private int direction;    

    public DoorButton( String nameOfCommand, String imageName1, String imageName2, int coordX, int coordY, int width, int height, int direction) {
        super( nameOfCommand, imageName1, imageName2, coordX, coordY, width, height);
        this.direction = direction;
    }

    public int getDirection() {
        return direction;
    }
}
