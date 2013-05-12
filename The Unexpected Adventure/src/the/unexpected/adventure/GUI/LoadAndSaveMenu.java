/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure.GUI;

import java.awt.Color;
import java.awt.Graphics;
import java.awt.Image;
import java.awt.event.ActionListener;
import java.io.File;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Date;
import javax.swing.JComponent;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.text.AttributeSet;
import javax.swing.text.BadLocationException;
import javax.swing.text.PlainDocument;
import net.miginfocom.swing.MigLayout;

/**
 *
 * @author Dominik
 */
public class LoadAndSaveMenu extends GamePanel {

    public JTextField nameOfGame;
    public MyButton load;
    public MyButton save;
    public MyButton closeLG;
    public MyButton closeSG;
    private JPanel panel;
    private JScrollPane sp;
    private Image backgroundSG;
    private Image backgroundLG;
    private boolean loadForMainMenu;
    private boolean showLoadBackground;
    private int amountOfSavedGames;
    private String nameOfLG;
    private ArrayList<ButtonOfSavedGame> listOfSavedGames = new ArrayList<>();
    public ArrayList<JComponent> listOfCompSG = new ArrayList<>();

    public LoadAndSaveMenu(boolean forMainMenu) {
        this.loadForMainMenu = forMainMenu;
        createAndShowGUI();
    }

    @Override
    public void createAndShowGUI() {
        super.createAndShowGUI();
        backgroundLG = loadImage("LoadAndSaveMenu/LoadGameMenu.png").getImage();
        backgroundSG = loadImage("LoadAndSaveMenu/SaveGameMenu.png").getImage();

        closeLG = new MyButton("backFromLGMenu", "LoadAndSaveMenu/button1_1.png", "LoadAndSaveMenu/button1_2.png", coorTransWidth(115), coorTransHeight(1647), coorTransWidth(324), coorTransHeight(99));
        closeLG.setBounds(coorTransWidth(115), coorTransHeight(1647), coorTransWidth(324), coorTransHeight(99));
        closeSG = new MyButton("backFromSGMenu", "LoadAndSaveMenu/button1_1.png", "LoadAndSaveMenu/button1_2.png", coorTransWidth(115), coorTransHeight(1647), coorTransWidth(324), coorTransHeight(99));
        closeSG.setBounds(coorTransWidth(115), coorTransHeight(1647), coorTransWidth(324), coorTransHeight(99));
        save = new MyButton("saveGame", "LoadAndSaveMenu/button2_1.png", "LoadAndSaveMenu/button2_2.png", coorTransWidth(2761), coorTransHeight(1647), coorTransWidth(324), coorTransHeight(99));
        save.setBounds(coorTransWidth(2761), coorTransHeight(1647), coorTransWidth(324), coorTransHeight(99));
        load = new MyButton("loadGame", "LoadAndSaveMenu/button3_1.png", "LoadAndSaveMenu/button3_2.png", coorTransWidth(2761), coorTransHeight(1647), coorTransWidth(324), coorTransHeight(99));
        load.setBounds(coorTransWidth(2761), coorTransHeight(1647), coorTransWidth(324), coorTransHeight(99));

        MaxLengthTextDocument maxLength = new MaxLengthTextDocument(21);
        nameOfGame = new JTextField();
        nameOfGame.setDocument(maxLength);
        nameOfGame.setBounds(coorTransWidth(1983), coorTransHeight(1164), coorTransWidth(720), coorTransHeight(100));
        nameOfGame.setOpaque(true);
        nameOfGame.setToolTipText("Název ukládané hry.");
        nameOfGame.setBackground(Color.DARK_GRAY);
        nameOfGame.setForeground(Color.black);

        panel = new JPanel();
        panel.setBounds(coorTransWidth(115), coorTransHeight(295), coorTransWidth(1485), coorTransHeight(1333));
        panel.setLayout(new MigLayout());

        sp = new JScrollPane(panel, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setBounds(coorTransWidth(115), coorTransHeight(295), coorTransWidth(1485), coorTransHeight(1333));
        sp.setBackground(new Color(0, 0, 0, 150));

        listOfComp.add(sp);
        listOfComp.add(closeLG);
        listOfComp.add(load);

        listOfCompSG.add(sp);
        listOfCompSG.add(closeSG);
        listOfCompSG.add(save);
        listOfCompSG.add(nameOfGame);

        update();
    }

    public void update() {
        File f = new File("save/");
        File[] saves = f.listFiles();
        listOfSavedGames.clear();
        for (File file : saves) {
            if (file.isDirectory()) {
                ButtonOfSavedGame mb = new ButtonOfSavedGame("LoadAndSaveMenu/background.png", coorTransWidth(115), coorTransHeight(295 + 100 * amountOfSavedGames), coorTransWidth(1485), coorTransHeight(100), file.lastModified());
                mb.setBounds(coorTransWidth(115), coorTransHeight(295 + 100 * amountOfSavedGames), coorTransWidth(1485), coorTransHeight(100));
//                mb.setVerticalAlignment(SwingConstants.TOP);
//                mb.setHorizontalAlignment(SwingConstants.CENTER);
                mb.setHorizontalTextPosition(SwingConstants.CENTER);
                mb.setVerticalTextPosition(SwingConstants.TOP);
                mb.setText(file.getName());
                mb.setNameOfSG(file.getName());
                listOfSavedGames.add(mb);
                amountOfSavedGames++;
            }
        }
        Collections.sort(listOfSavedGames, new ComparatorOfSavedGames());
        panel.removeAll();
        for (int i = 0; i < listOfSavedGames.size(); i++) {
            panel.add(listOfSavedGames.get(i), "wrap");
        }
    }

    public void addActionListener(ActionListener controller) {
        for (int i = 0; i < listOfSavedGames.size(); i++) {
            listOfSavedGames.get(i).addActionListener(controller);
        }
    }

    public String getNameOfLG() {
        return nameOfLG;
    }

    public void setNameOfLG(String nameOfLG) {
        this.nameOfLG = nameOfLG;
    }

    public boolean isLoadForMainMenu() {
        return loadForMainMenu;
    }

    public void setLoadForMainMenu(boolean loadForMainMenu) {
        this.loadForMainMenu = loadForMainMenu;
    }

    public void setShowLoadBackground(boolean showLoadBackground) {
        this.showLoadBackground = showLoadBackground;
    }

    @Override
    public void paint(Graphics g) {
        if (showLoadBackground) {
            g.drawImage(backgroundLG, 0, 0, widthScreen, heightScreen, null);
        } else {
            g.drawImage(backgroundSG, 0, 0, widthScreen, heightScreen, null);
        }
    }

    class ComparatorOfSavedGames implements Comparator<ButtonOfSavedGame> {

        @Override
        public int compare(ButtonOfSavedGame o1, ButtonOfSavedGame o2) {
            return Long.compare(o2.getDate(), o1.getDate());
        }
    }
    /*
     * kod ziskan ze stranek http://www.andrels.com/
     * pomoci teto vnitrni tridy bude mozne urcit maximum ynaku pro textove pole urcuici nazev ukladane hry
     */

    public class MaxLengthTextDocument extends PlainDocument {

        private int maxChars;

        public MaxLengthTextDocument(int maxChars) {
            this.maxChars = maxChars;
        }

        @Override
        public void insertString(int offs, String str, AttributeSet a)
                throws BadLocationException {
            if (str != null && (getLength() + str.length() < maxChars)) {
                super.insertString(offs, str, a);
            }
        }
    }
}
