package the.unexpected.adventure.GUI;

import java.awt.*;
import java.io.IOException;
import java.util.ArrayList;
import javax.swing.JComponent;

public class MainMenu extends GamePanel {

    private Image[] imgDoorAnim = new Image[12];
    private Image[] imgStartMenu = new Image[4];
    private Image[] imgDialogs = new Image[4];
    private Image imgMainMenu;
    public int mX;
    public int mY;
    public int mouseLoc;
    private boolean mainMenuRuns = true;
    public MyButton loadG;
    public MyButton newG;
    public MyButton quitG;
    public ArrayList<JComponent> dialogsComp = new ArrayList<>();

    public MainMenu() throws IOException {
        createAndShowGUI();
    }

    @Override
    public void createAndShowGUI() {
        super.createAndShowGUI();
        imgMainMenu = loadImage("mainMenu/mainMenu.png").getImage();
        int pocitadlo = 0;
        for (int j = 0; j < imgDoorAnim.length; j++) {
            imgDoorAnim[j] = loadImage("openingDoorAnim/door_" + pocitadlo + ".jpg").getImage();
            pocitadlo += 5;
        }

        loadG = new MyButton("loadGame", "mainMenu/load1.png", "mainMenu/load2.png", coorTransWidth(1473), coorTransHeight(512), coorTransWidth(69), coorTransHeight(322));
        loadG.setBounds(coorTransWidth(1473), coorTransHeight(512), coorTransWidth(69), coorTransHeight(322));
        newG = new MyButton("newGame", "mainMenu/new1.png", "mainMenu/new2.png", coorTransWidth(1573), coorTransHeight(598), coorTransWidth(69), coorTransHeight(236));
        newG.setBounds(coorTransWidth(1573), coorTransHeight(598), coorTransWidth(69), coorTransHeight(236));
        quitG = new MyButton("quitGame", "mainMenu/quit1.png", "mainMenu/quit2.png", coorTransWidth(1677), coorTransHeight(512), coorTransWidth(69), coorTransHeight(322));
        quitG.setBounds(coorTransWidth(1677), coorTransHeight(512), coorTransWidth(69), coorTransHeight(322));

        listOfComp.add(loadG);
        listOfComp.add(newG);
        listOfComp.add(quitG);
    }

    public void doorAnim(Graphics g) {
        g.drawImage(imgDoorAnim[0], 0, 0, widthScreen, heightScreen, this);


//        for (int j = 0; j < imgDoorAnim.length; j++) {
//            System.out.println("zzvzsfsdf");
//            g.drawImage(imgDoorAnim[j], 0, 0, widthScreen, heightScreen, this);
//            try {
//                vlaknoo.sleep(65);
//            } catch (InterruptedException ex) {
//                Logger.getLogger(GUI.class
//                        .getName()).log(Level.SEVERE, null, ex);
//            }
//            //zoom na temnotu za otevrenymi dvermi
//            if ((j + 1) == imgDoorAnim.length) {
//                int moveX = 0;
//                int moveY = 0;
//                for (; moveX <= 5000 || moveY <= 5000; moveX += 350, moveY += 80) {
//                    g.drawImage(imgDoorAnim[j], -moveX, -moveY, widthScreen + (2 * moveX), heightScreen + (2 * moveY), this);
//                }
//            }
//        }
//        try {
//            vlaknoo.sleep(500);
//        } catch (InterruptedException ex) {
//            Logger.getLogger(GUI.class
//                    .getName()).log(Level.SEVERE, null, ex);
//        }
    }

    public void mainMenu(Graphics g) {
        g.drawImage(imgMainMenu, 0, 0, widthScreen, heightScreen, this);
    }

    public boolean isMainMenuRuns() {
        return mainMenuRuns;
    }

    public void setMainMenuRuns(boolean mainMenuRuns) {
        this.mainMenuRuns = mainMenuRuns;
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(imgMainMenu, 0, 0, widthScreen, heightScreen, this);
    }
}
