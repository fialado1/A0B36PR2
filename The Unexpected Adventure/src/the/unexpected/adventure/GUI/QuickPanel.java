/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure.GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.KeyEvent;
import java.io.IOException;

/**
 *
 * @author Dominik
 */
public class QuickPanel extends GamePanel {

    private Image quickPanel;
    public MyButton toInfAndInv;
    public MyButton toEscMenu;
//    public ArrayList<JComponent> quikPanelComp = new ArrayList<>();

    public QuickPanel() throws IOException {
        createAndShowGUI();

    }

    @Override
    public void createAndShowGUI(){
        super.createAndShowGUI();
        quickPanel = loadImage("infoAndInv/quickPanel.png").getImage();
        toInfAndInv = new MyButton("openInfAndInv", "infoAndInv/toInfAndInv1.png", "infoAndInv/toInfAndInv2.png", coorTransWidth(2423), coorTransHeight(1665), coorTransWidth(135), coorTransHeight(135));
        toInfAndInv.setBounds(coorTransWidth(2423), coorTransHeight(1665), coorTransWidth(135), coorTransHeight(135));
        toEscMenu = new MyButton("toEscMenu", "infoAndInv/toMenu1.png", "infoAndInv/toMenu2.png", coorTransWidth(643), coorTransHeight(1665), coorTransWidth(135), coorTransHeight(135));
        toEscMenu.setBounds(coorTransWidth(643), coorTransHeight(1665), coorTransWidth(135), coorTransHeight(135));

        listOfComp.add(toInfAndInv);
        listOfComp.add(toEscMenu);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(quickPanel, 0, heightScreen - coorTransHeight(135), widthScreen, coorTransHeight(135), null);
    }
}
