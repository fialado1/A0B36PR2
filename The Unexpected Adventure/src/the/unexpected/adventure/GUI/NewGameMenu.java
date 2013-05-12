package the.unexpected.adventure.GUI;

import java.awt.*;
import java.io.IOException;
import javax.swing.*;

/**
 *
 * @author Dominik
 */
public class NewGameMenu extends GamePanel {

    private Image imgNewGame;
    private Image[] imgHead = new Image[4];
    private Image[] imgBody = new Image[4];
    private int actualHead;
    private int actualBody;
    private int points = 20;
    private int[] widthImgHead = new int[4];
    private int[] heightImgHead = new int[4];
    private int[] widthImgBody = new int[4];
    private int[] heightImgBody = new int[4];
//    public boolean back = false;
//    public boolean next = false;
    private volatile boolean newGameMenuRuns = true;
//    
    public JTextField nameField;
    public MyButton nextB;
    MyButton backB;
    public MyButton[] up = new MyButton[4];
    MyButton[] down = new MyButton[4];

    public NewGameMenu() throws IOException {
        createAndShowGUI();
    }

    @Override
    public void createAndShowGUI() {
        super.createAndShowGUI();
//        vlakno.start();
        //LOAD IMAGES
        for (int i = 0; i < imgHead.length; i++) {
            imgHead[i] = loadImage("character/head/0" + (i + 1) + "/0.png").getImage();
            widthImgHead[i] = iconWidth;
            heightImgHead[i] = iconHeight;
            imgBody[i] = loadImage("character/body/0" + (i + 1) + "/0.png").getImage();
            widthImgBody[i] = iconWidth;
            heightImgBody[i] = iconHeight;
        }
        imgNewGame = loadImage("newGameMenu/menu.png").getImage();


        //CREATE COMPONENTS (buttons, text field) & SET THEIR PROPERTIES
        createAndSetComponents();

    }

    @Override
    public void updateAfterChangeOfValue(int stat) {
        super.updateAfterChangeOfValue(stat);
        actualBody = (valueOfStats[0] / 5) - 1;
        actualHead = (valueOfStats[3] / 5) - 1;
        if (actualHead == 4) {
            actualHead--;
        }
        if (actualBody == 4) {
            actualBody--;
        }
    }

    public void resetStats() {
        for (int i = 0; i < valueOfStats.length; i++) {
            valueOfStats[i] = 5;
            updateAfterChangeOfValue(i);
        }
        points = 20;
    }

    public void valueOfStatPlus(int stat) {
        if (points != 0) {
            valueOfStats[stat]++;
            points--;
        }
        updateAfterChangeOfValue(stat);
    }

    public void valueOfStatMinus(int stat) {
        if (valueOfStats[stat] >= 6) {
            valueOfStats[stat]--;
            points++;
        }
        updateAfterChangeOfValue(stat);
    }

    public int getValueOfStat(int i) {
        return valueOfStats[i];
    }

    public int getActualHead() {
        return actualHead;
    }

    public int getActualBody() {
        return actualBody;
    }

    public boolean isNewGameMenuRuns() {
        return newGameMenuRuns;
    }

    public void setNewGameMenuRuns(boolean newGameMenuRuns) {
        this.newGameMenuRuns = newGameMenuRuns;
    }

    private void createAndSetComponents() {
        backB = new MyButton("backB", "newGameMenu/button" + 1 + ".png", "newGameMenu/button" + 2 + ".png", (2 * 40), (heightScreen - 60), 100, 40);
        backB.setBounds((2 * 40), (heightScreen - 60), 100, 40);

        nextB = new MyButton("startNewGame", "newGameMenu/button" + 3 + ".png", "newGameMenu/button" + 4 + ".png", (widthScreen - 100) - (2 * 40), (heightScreen - 60), 100, 40);
        nextB.setBounds((2 * 40) + (widthScreen - 260), (heightScreen - 60), 100, 40);

        int spaceH = 0;
        for (int i = 0; i < up.length; i++) {
            up[i] = new MyButton("up" + i, "newGameMenu/button5.png", "newGameMenu/button7.png", widthScreen - (330), heightScreen - (540 - spaceH), 58, 29);
            down[i] = new MyButton("down" + i, "newGameMenu/button6.png", "newGameMenu/button8.png", widthScreen - (330), heightScreen - (510 - spaceH), 58, 29);
            up[i].setBounds(widthScreen - (330), heightScreen - (540 - spaceH), 58, 29);
            down[i].setBounds(widthScreen - (330), heightScreen - (510 - spaceH), 58, 29);
            spaceH += 85;
            listOfComp.add(up[i]);
            listOfComp.add(down[i]);

        }
        nameField = new JTextField();
        nameField.setBounds(544, 650, 275, 30);
        nameField.setOpaque(true);
        nameField.setToolTipText("Zde si urči jméno tvé postavy.");
        nameField.setBackground(Color.DARK_GRAY);
        nameField.setForeground(Color.red);

        listOfComp.add(backB);
        listOfComp.add(nextB);
        listOfComp.add(nameField);
    }

//    @Override
//    public void run() {
//        while (newGameMenuRuns) {
//            repaint();
//            try {
//                vlakno.sleep(17);
//            } catch (InterruptedException ex) {
//            }
//        }
//        System.out.println("vlakno ngm dobehlo");
//    }
    @Override
    public void paint(Graphics g) {
        g.drawImage(imgNewGame, 0, 0, widthScreen, heightScreen, this);
        g.drawImage(imgBody[actualBody], (widthScreen / 3) - (widthImgBody[actualBody] / 2) / 5 * 2, 150 + heightImgHead[actualBody] / 5, widthImgBody[actualBody] / 5 * 2, heightImgBody[actualBody] / 5 * 2, this);
        g.drawImage(imgHead[actualHead], (widthScreen / 3) - (widthImgHead[actualHead] / 2) / 5, 155, widthImgHead[actualHead] / 5, heightImgHead[actualHead] / 5, this);
        if (points >= 10) {
            toSingleDigits(points);
            g.drawImage(imgNumbers[result[0]], widthScreen - (310 + 8), (heightScreen - (600)), (15), (20), this);
            g.drawImage(imgNumbers[result[1]], widthScreen - (310 + 8 - 15), (heightScreen - 600), 15, 20, this);
        } else if (points == 9) {
            g.drawImage(imgNumbers[points], widthScreen - (310), (heightScreen - (600)), 15, 20, this);
        } else {
            g.drawImage(imgNumbers[points], widthScreen - 310, (heightScreen - 600), 15, 20, this);
        }
        int spaceH = 0;
        for (int i = 0; i < 4; i++) {
            if (!twoDigit[i]) {
                g.drawImage(imgNumbers[valueOfStats[i]], widthScreen - (330 + 10 + 40), heightScreen - (525 - spaceH), 20, 26, this);
            }
            int spaceW = 0;
            for (int j = 0; j < 2; j++) {
                if (twoDigit[i]) {
                    toSingleDigits(valueOfStats[i]);
                    g.drawImage(imgNumbers[result[j]], widthScreen - (330 + 20 + 40 - spaceW), heightScreen - (525 - spaceH), 20, 26, this);
                }
                spaceW += 20;
            }
            spaceH += 85;
        }
    }
}
