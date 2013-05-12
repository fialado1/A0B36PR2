/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure.GUI;

import java.awt.Graphics;
import java.awt.Image;

/**
 *
 * @author Dominik
 */
public class QuitDialog extends GamePanel {

    private Image imgQuitDialog;
    public MyButton yes;
    public MyButton no;

    public QuitDialog() {
        createAndShowGUI();
    }

    @Override
    public void createAndShowGUI() {
        super.createAndShowGUI();
        imgQuitDialog = loadImage("mainMenu/quitDialog.png").getImage();

        yes = new MyButton("yes", "mainMenu/yes1.png", "mainMenu/yes2.png", coorTransWidth(1199), coorTransHeight(963), coorTransWidth(129), coorTransHeight(52));
        yes.setBounds(coorTransWidth(1199), coorTransHeight(963), coorTransWidth(129), coorTransHeight(52));
        no = new MyButton("no", "mainMenu/no1.png", "mainMenu/no2.png", coorTransWidth(1895), coorTransHeight(963), coorTransWidth(81), coorTransHeight(52));
        no.setBounds(coorTransWidth(1895), coorTransHeight(963), coorTransWidth(81), coorTransHeight(52));

        
        listOfComp.add(yes);
        listOfComp.add(no);

    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(imgQuitDialog, 0, 0, widthScreen, heightScreen, this);
    }
}
