/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure.GUI;

import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComponent;

/**
 *
 * @author Dominik
 */
public abstract class MyClass {

    public int widthScreen;
    public int heightScreen;
    public ArrayList<JComponent> listOfComp = new ArrayList<>();

    public MyClass(int widthScreen, int heightScreen) {
        this.widthScreen = widthScreen;
        this.heightScreen = heightScreen;
    }

    public void addActionListenerToList(ActionListener al, ArrayList<JComponent> alc) {
        for (int i = 0; i < alc.size(); i++) {
            if (alc.get(i) instanceof MyButton) {
                MyButton mb = (MyButton) alc.get(i);
                mb.addActionListener(al);
            }
        }
    }

    public void setCompDisable() {
        for (int i = 0; i < listOfComp.size(); i++) {
            listOfComp.get(i).setEnabled(false);
        }
    }

    public void setCompEnable() {
        for (int i = 0; i < listOfComp.size(); i++) {
            listOfComp.get(i).setEnabled(true);
        }
    }

    public final int coorTransHeight(int oldValue) {
        int newValue;
        newValue = oldValue * heightScreen / 1800;
        return newValue;
    }

    public final int coorTransWidth(int oldValue) {
        int newValue;
        newValue = oldValue * widthScreen / 3200;
        return newValue;
    }

    public final int coorTransHeightBack(int oldValue) {
        int newValue;
        newValue = oldValue * 1800 / heightScreen;
        return newValue;
    }

    public final int coorTransWidthBack(int oldValue) {
        int newValue;
        newValue = oldValue * 3200 / widthScreen;
        return newValue;
    }
}
