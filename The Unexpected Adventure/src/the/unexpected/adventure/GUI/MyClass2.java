/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure.GUI;

import java.awt.Dimension;
import java.awt.Toolkit;
import javax.swing.JPanel;

/**
 *
 * @author Dominik
 */
public abstract class MyClass2 extends JPanel {

    public int widthScreen;
    public int heightScreen;

    public void createAndShowGUI() {
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        widthScreen = (int) d.getWidth();
        heightScreen = (int) d.getHeight();
        this.setOpaque(false);
        this.setBounds(0, 0, widthScreen, heightScreen);
        this.setLayout(null);
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
