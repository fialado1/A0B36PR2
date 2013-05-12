/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.Insets;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.BorderFactory;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JTabbedPane;
import javax.swing.JTextArea;
import javax.swing.UIManager;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Dominik
 */
public final class InfoAndInventory extends GamePanel {

    private Image infoAndInvBackground;
    private Image slash;
    private int[] valueOfStatsLeft = new int[7];
    private int[] countOfItems = new int[4];
    private boolean[] twoDigitLeft = new boolean[7];
    private boolean firstIsChosen;
    private ItemButton actualItem;
    private ItemButton firstForCombine;
    public MyButton backB;
    private JTabbedPane tabbPane;
    private JComponent[] tab = new JComponent[4];
    public ArrayList<JComponent> listOfItems = new ArrayList<>();
    private String actualDescrip;

    public InfoAndInventory() throws IOException {
        createAndShowGUI();
    }

    @Override
    public void createAndShowGUI() {
        super.createAndShowGUI();
        infoAndInvBackground = loadImage("infoAndInv/background.png").getImage();
        slash = loadImage("numbers/slash.png").getImage();

        backB = new MyButton("backFromInv", "newGameMenu/button1.png", "newGameMenu/button2.png", coorTransWidth(1458), coorTransHeight(1633), coorTransWidth(291), coorTransHeight(94));
        backB.setBounds(coorTransWidth(1458), coorTransHeight(1633), coorTransWidth(291), coorTransHeight(94));

        UIManager.put("TabbedPane.selected", Color.black);
        UIManager.put("TabbedPane.contentBorderInsets", new Insets(0, 0, 0, 0));
        tabbPane = new JTabbedPane();
        for (int i = 0; i < tab.length; i++) {
            tab[i] = inventoryPanel();
            countOfItems[i] = 0;
        }
        tabbPane.addTab("k nošení", null, tab[0], "");
        tabbPane.addTab("k použití", null, tab[1], "");
        tabbPane.addTab("poživatiny", null, tab[2], "");
        tabbPane.addTab("ke kombinaci", null, tab[3], "");
        tabbPane.setBounds(coorTransWidth(187), coorTransHeight(243), coorTransWidth(1016), coorTransHeight(1296));
        tabbPane.setBackground(Color.red);
        tabbPane.setBorder(BorderFactory.createEmptyBorder());

        listOfComp.add(backB);
        listOfComp.add(tabbPane);
    }

    public JTextArea createDescription(String s) {

        JTextArea jta = new JTextArea(5, 2);
        jta.setEditable(false);
        jta.setBackground(new Color(0, 0, 0));
        jta.setForeground(new Color(250, 250, 250));
        jta.setFont(new Font("Arial", Font.BOLD, 15));
        jta.setText(s);
        return jta;
    }

    public void addToTab(int type, JComponent jc) {
        tab[type - 1].add(jc, "cell " + countOfItems[type - 1] % 5 + " " + countOfItems[type - 1] / 5);
        countOfItems[type - 1]++;
        listOfItems.add(jc);
    }

    public void removeFromTab(int type, JComponent jc) {
        tab[type - 1].remove(jc);
    }

    public void removeFromTab(int type, int index) {
        actualItem = (ItemButton) listOfItems.get(index);
        tab[type - 1].remove(actualItem);
        listOfItems.remove(actualItem);
        countOfItems[type - 1]--;
    }

    private JComponent inventoryPanel() {
        JPanel panel = new JPanel();
        panel.setLayout(new MigLayout());
        panel.setBackground(new Color(77, 77, 77));
        return panel;
    }

    public int indexOfItem(ItemButton ib) {
        return listOfItems.indexOf(ib);
    }

    public String getActualDescrip() {
        return actualDescrip;
    }

    public void setActualDescrip(String actualDescrip) {
        this.actualDescrip = actualDescrip;
    }

    public boolean isFirstIsChosen() {
        return firstIsChosen;
    }

    public void setFirstIsChosen(boolean firstIsChosen) {
        this.firstIsChosen = firstIsChosen;
    }

    public ItemButton getFirstForCombine() {
        return firstForCombine;
    }

    public void setFirstForCombine(ItemButton firstForCombine) {
        this.firstForCombine = firstForCombine;
    }

    public ItemButton getActualItem() {
        return actualItem;
    }

    public void setActualItem(ItemButton actualItem) {
        this.actualItem = actualItem;
    }

    public int[] getValueOfStats() {
        return valueOfStats;
    }

    public int getValueOfStats(int index) {
        return valueOfStats[index];
    }

    public void setValueOfStats(int[] valueOfStats) {
        this.valueOfStats = valueOfStats;
    }
    public void setValueOfStats(int sil, int odol, int intel, int cha) {
        valueOfStats[0] = sil;
        valueOfStats[1] = odol;
        valueOfStats[2] = intel;
        valueOfStats[3] = cha;
    }

    public int[] getValueOfStatsLeft() {
        return valueOfStatsLeft;
    }

    public int getValueOfStatsLeft(int index) {
        return valueOfStatsLeft[index];
    }

    public void setValueOfStatsLeft(int ut, int ob, int ziv, int mZiv, int zav, int zk, int ur) {
        valueOfStatsLeft[0] = ut;
        valueOfStatsLeft[1] = ob;
        valueOfStatsLeft[2] = ziv;
        valueOfStatsLeft[3] = mZiv;
        valueOfStatsLeft[4] = zav;
        valueOfStatsLeft[5] = zk;
        valueOfStatsLeft[6] = ur;
    }

    public void updateVariables() {
        for (int i = 0; i < 7; i++) {
            if (i < 4) {
                updateAfterChangeOfValue(i);
            }
            if (valueOfStatsLeft[i] > 9) {
                twoDigitLeft[i] = true;
            } else {
                twoDigitLeft[i] = false;
            }
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(infoAndInvBackground, 0, 0, widthScreen, heightScreen, null);
        g.drawImage(slash, coorTransWidth(2130), coorTransHeight(1450) - 153, 13, 21, null);
        int spaceH = 0;
        for (int i = 6; i >= 0; i--) {
            if (i >= 3) {
                if (!twoDigit[i - 3]) {
                    g.drawImage(imgNumbers[valueOfStats[i - 3]], coorTransWidth(2850), coorTransHeight(1450) - spaceH, 16, 21, null);
                }
            }
            if (!twoDigitLeft[i]) {
                if (i == 2) {
                    g.drawImage(imgNumbers[valueOfStatsLeft[i]], coorTransWidth(2130) - 16, coorTransHeight(1450) - spaceH, 16, 21, null);
                } else if (i == 3) {
                    g.drawImage(imgNumbers[valueOfStatsLeft[i]], coorTransWidth(2130) + 16, coorTransHeight(1450) - spaceH, 16, 21, null);
                } else {
                    g.drawImage(imgNumbers[valueOfStatsLeft[i]], coorTransWidth(2130), coorTransHeight(1450) - spaceH, 16, 21, null);
                }
            }
            int spaceW = 0;
            for (int j = 0; j < 2; j++) {
                if (i >= 3) {
                    if (twoDigit[i - 3]) {
                        toSingleDigits(valueOfStats[i - 3]);
                        g.drawImage(imgNumbers[result[j]], coorTransWidth(2850) + spaceW - 8, coorTransHeight(1450) - spaceH, 16, 21, null);
                    }
                }
                if (twoDigitLeft[i]) {
                    toSingleDigits(valueOfStatsLeft[i]);
                    if (i == 2) {
                        g.drawImage(imgNumbers[result[j]], coorTransWidth(2130) - 31 + spaceW, coorTransHeight(1450) - spaceH, 16, 21, null);
                    } else if (i == 3) {
                        g.drawImage(imgNumbers[result[j]], coorTransWidth(2130) + 15 + spaceW, coorTransHeight(1450) - spaceH, 16, 21, null);
                    } else {
                        g.drawImage(imgNumbers[result[j]], coorTransWidth(2130) + spaceW - 8, coorTransHeight(1450) - spaceH, 16, 21, null);
                    }
                }
                spaceW += 16;
            }
            if (i != 3) {
                spaceH += 51;
            }
        }
        //nechat vykreslit obrazek("mrizku") do ktere se vykresli obrazky veci z inventare
    }
}
