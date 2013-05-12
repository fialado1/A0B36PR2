/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure.GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.io.IOException;

public final class EscMenu extends GamePanel {

    private Image menuBackground;
    public MyButton backFromEscMenu;
    public MyButton controls;
    public MyButton loadGame;
    public MyButton saveGame;
    public MyButton quitGame;

    public EscMenu() throws IOException {
        createAndShowGUI();
    }

    @Override
    public void createAndShowGUI() {
        super.createAndShowGUI();
        menuBackground = loadImage("EscMenu/background.png").getImage();

        backFromEscMenu = new MyButton("backFromEscMenu", "EscMenu/button1_1.png", "EscMenu/button1_2.png", coorTransWidth(1387), coorTransHeight(532), coorTransWidth(426), coorTransWidth(60));
        backFromEscMenu.setBounds(coorTransWidth(1387), coorTransHeight(532), coorTransWidth(426), coorTransWidth(60));
        controls = new MyButton("controls", "EscMenu/button2_1.png", "EscMenu/button2_2.png", coorTransWidth(1387), coorTransHeight(690), coorTransWidth(426), coorTransWidth(60));
        controls.setBounds(coorTransWidth(1387), coorTransHeight(690), coorTransWidth(426), coorTransWidth(60));
        saveGame = new MyButton("saveGame", "EscMenu/button3_1.png", "EscMenu/button3_2.png", coorTransWidth(1387), coorTransHeight(848), coorTransWidth(426), coorTransWidth(60));
        saveGame.setBounds(coorTransWidth(1387), coorTransHeight(848), coorTransWidth(426), coorTransWidth(60));
        loadGame = new MyButton("loadGame", "EscMenu/button4_1.png", "EscMenu/button4_2.png", coorTransWidth(1387), coorTransHeight(1006), coorTransWidth(426), coorTransWidth(60));
        loadGame.setBounds(coorTransWidth(1387), coorTransHeight(1006), coorTransWidth(426), coorTransWidth(60));
        quitGame = new MyButton("endGame", "EscMenu/button5_1.png", "EscMenu/button5_2.png", coorTransWidth(1387), coorTransHeight(1164), coorTransWidth(426), coorTransWidth(60));
        quitGame.setBounds(coorTransWidth(1387), coorTransHeight(1164), coorTransWidth(426), coorTransWidth(60));

        listOfComp.add(backFromEscMenu);
        listOfComp.add(controls);
        listOfComp.add(saveGame);
        listOfComp.add(loadGame);
        listOfComp.add(quitGame);
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(menuBackground, 0, 0, widthScreen, heightScreen, null);
    }
}
