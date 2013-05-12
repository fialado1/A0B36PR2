/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure.GUI;

import java.awt.*;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.*;

/**
 *
 * @author Dominik
 */
public abstract class GamePanel extends MyClass2 {

    public int ratio;
    public int iconWidth = 0;
    public int iconHeight = 0;
//        
    public Image[] imgNumbers = new Image[10];
    public int[] valueOfStats = new int[4];
    public boolean[] twoDigit = new boolean[4];
    public int[] result = new int[2];
//    
    public ArrayList<JComponent> listOfComp = new ArrayList<>();

    @Override
    public void createAndShowGUI() {
        super.createAndShowGUI();
        //LOAD IMAGES
        for (int j = 0; j < imgNumbers.length; j++) {
            imgNumbers[j] = loadImage("numbers/" + j + ".jpg").getImage();
        }
        for (int i = 0; i < 4; i++) {
            valueOfStats[i] = 5;
            twoDigit[i] = false;
        }
    }

    @Override
    public abstract void paint(Graphics g);

    public void updateAfterChangeOfValue(int stat) {
        if (valueOfStats[stat] > 9) {
            twoDigit[stat] = true;
        } else {
            twoDigit[stat] = false;
        }
    }

    public void toSingleDigits(int value) {
        int firstMemb = value / 10;
        int secondMemb = value - firstMemb * 10;
        result[0] = firstMemb;
        result[1] = secondMemb;
    }

    public ImageIcon loadImage(String nazev) {
        ImageIcon ii = new ImageIcon("images/" + nazev);
        iconWidth = ii.getIconWidth();
        iconHeight = ii.getIconHeight();
        return ii;
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
}
