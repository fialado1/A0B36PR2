/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure.GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author Dominik
 */
public class Loading extends MyClass2 {

    private Image imgLoading;

    public Loading() {
        createAndShowGUI();
    }

    @Override
    public void createAndShowGUI() {
        super.createAndShowGUI();
        try {
            imgLoading = ImageIO.read(new File("images/others/loading.png"));
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Došlo k chybě při načítání obrázku zobrazující se ho při loadingu.", "Error",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(imgLoading, 0, 0, widthScreen, heightScreen, this);
    }
}
