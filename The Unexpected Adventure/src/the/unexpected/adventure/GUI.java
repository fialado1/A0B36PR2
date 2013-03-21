/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure;

import java.awt.*;
import java.awt.event.*;
import java.util.logging.*;
import javax.swing.*;

/**
 *
 * @author Dominik
 */
public final class GUI extends JFrame implements Runnable, MouseListener, MouseMotionListener {

    Thread vlakno = new Thread(this);
    private Image img;
    private Graphics grp;
    private Image[] imgDoorAnim = new Image[12];
    private Image[] imgStartMenu = new Image[4];
    private Image[] imgDialogs = new Image[4];
    private Image[] imgNumbers = new Image[10];
    private Image[] imgButtons = new Image[6];
    private Image imgNewGame;
    private int widthScreen;
    private int heightScreen;
    private int fazeHry = 1;
    private int mX;
    private int mY;
    private int menuMouseLoc;
    Game game = new Game();

    public GUI() throws InterruptedException {
        creatAndShowGUI();
    }

    public void creatAndShowGUI() throws InterruptedException {

        vlakno.start();

        //LOAD IMAGES
        int pocitadlo = 0;
        for (int j = 0; j < imgDoorAnim.length; j++) {
            imgDoorAnim[j] = loadImage("openingDoorAnim/door_" + pocitadlo + ".jpg").getImage();
            pocitadlo += 5;
        }
        for (int j = 0; j < imgStartMenu.length; j++) {
            imgStartMenu[j] = loadImage("startMenu/door_0_" + j + ".jpg").getImage();
        }
        for (int j = 0; j < imgDialogs.length; j++) {
            imgDialogs[j] = loadImage("dialogs/quit_" + j + ".jpg").getImage();
        }
        for (int j = 0; j < imgNumbers.length; j++) {
            imgNumbers[j] = loadImage("newGameMenu/" + j + ".png").getImage();
        }
        for (int j = 0; j < imgButtons.length; j++) {
            imgButtons[j] = loadImage("newGameMenu/button" + j + ".png").getImage();
        }
        imgNewGame = loadImage("newGameMenu/menu.jpg").getImage();

        addMouseMotionListener(this);
        addMouseListener(this);

        //SET ANYTHING AROUND SCREEN & WINDOW
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        widthScreen = (int) d.getWidth();
        heightScreen = (int) d.getHeight();
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = env.getDefaultScreenDevice();


        setTitle("The Unexpected Journey");
        setIconImage(loadImage("icon/adventura.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        this.setUndecorated(true);
        this.setResizable(false);
        defaultScreen.setFullScreenWindow(this);

        setVisible(true);
    }

    private ImageIcon loadImage(String nazev) {
        ImageIcon ii = new ImageIcon("images/" + nazev);
        return ii;
    }

    @Override
    public void run() {
        while (true) {
            repaint();
            try {
                Thread.sleep(17);
            } catch (InterruptedException ex) {
            }
        }
    }

    public void quitDialog(Graphics g) {
        g.drawImage(imgDialogs[menuMouseLoc], (widthScreen - 522) / 2, (heightScreen - 200) / 2, 522, 200, this);
    }

    public void startMenu(Graphics g) {
        g.drawImage(imgStartMenu[menuMouseLoc], 0, 0, widthScreen, heightScreen, this);

    }

    public void newGameMenu(Graphics g) {
        doorAnim(g);
        g.drawImage(imgNewGame, 0, 0, widthScreen, heightScreen, this);
//        int moveX = 0;
//        int moveY = 0;
//        for (; moveX >= 1600 || moveY >= 900; moveX += 16, moveY += 9) {
//             System.out.println("bsf");
//            g.drawImage(imgNewGame,  widthScreen/2 - moveX/2, heightScreen/2 - moveY/2,moveX, moveY, this);
//            System.out.println("wS " + (widthScreen/2 - moveX/2) + "\nhS " +( heightScreen/2 - moveY/2));
//        }
        fazeHry = 3;
    }

    public void newGameMenu2(Graphics g) {
        g.drawImage(imgButtons[0], 115, 35, 230, 100, this);
        g.drawImage(imgButtons[1], (widthScreen - 435), (heightScreen - 110), 200, 90, this);
        int pomocna = 0;
        for (int i = 0; i < 4; i++) {
            g.drawImage(imgButtons[4], widthScreen - (130), heightScreen - (556 - pomocna), 58, 29, this);
            g.drawImage(imgButtons[2], widthScreen - (130), heightScreen - (556 - 33 - pomocna), 58, 29, this);
            g.drawImage(imgNumbers[5], widthScreen - (130+17+57), heightScreen - (556 - pomocna), 35, 47, this);
            pomocna += 107;
        }
    }

    public void loadMenu(Graphics g) {
        g.drawImage(imgDoorAnim[0], 0, 0, widthScreen, heightScreen, this);

    }

    public void doorAnim(Graphics g) {
        for (int j = 0; j < imgDoorAnim.length; j++) {
            g.drawImage(imgDoorAnim[j], 0, 0, widthScreen, heightScreen, this);
            try {
                vlakno.sleep(65);
            } catch (InterruptedException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            //zoom na temnotu za otevrenymi dvermi
            if ((j + 1) == imgDoorAnim.length) {
                int moveX = 0;
                int moveY = 0;
                for (; moveX <= 5000 || moveY <= 5000; moveX += 350, moveY += 80) {
                    g.drawImage(imgDoorAnim[j], -moveX, -moveY, widthScreen + (2 * moveX), heightScreen + (2 * moveY), this);
                }
            }
        }
        try {
            vlakno.sleep(500);
        } catch (InterruptedException ex) {
            Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
        }
        fazeHry = 2;
    }

    public void mainGame(Graphics g) {
    }

    @Override
    public void paint(Graphics g) {
        switch (fazeHry) {
            case 0://ukonceni hry - vyskoci tabulka jestli si je hrac jisty nebo podekovani mu za zahrani si
                quitDialog(g);
                break;
            case 1://uvodni menu - new, load, quit adventure
                startMenu(g);
                break;
            case 2://nova hra - vytvoreni nove postavy
                newGameMenu(g);
                break;
            case 3://
                newGameMenu2(g);
                break;
            case 4://tabulka s moznosti naloudovani ulozene hry
                loadMenu(g);
                break;
            case 5://vlastni hra
                mainGame(g);
                break;
        }
    }

    @Override
    public void update(Graphics g) {
        if (img == null) {
            img = createImage(this.getSize().width, this.getSize().height);
            grp = img.getGraphics();
        }

        grp.setColor(getBackground());
        grp.fillRect(0, 0, this.getSize().width, this.getSize().height);

        grp.setColor(getForeground());
        paint(grp);

        g.drawImage(img, 0, 0, this);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
        e.consume();
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();
        switch (fazeHry) {
            case 0:
                if (mX >= 530 && mX <= 580 && mY >= 410 && mY <= 440) {
                    menuMouseLoc = 1;
                } else if (mX >= 825 && mX <= 870 && mY >= 410 && mY <= 440) {
                    menuMouseLoc = 2;
                } else {
                    menuMouseLoc = 0;
                }
                break;
            case 1:
                if (mX >= 625 && mX <= 660 && mY >= 207 && mY <= 350) {
                    menuMouseLoc = 1;
                } else if (mX >= 665 && mX <= 700 && mY >= 245 && mY <= 350) {
                    menuMouseLoc = 2;
                } else if (mX >= 712 && mX <= 745 && mY >= 207 && mY <= 350) {
                    menuMouseLoc = 3;
                } else {
                    menuMouseLoc = 0;
                }
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();
        switch (fazeHry) {
            case 0:
                if (mX >= 530 && mX <= 580 && mY >= 410 && mY <= 440) {
                    System.exit(1);
                } else if (mX >= 825 && mX <= 870 && mY >= 410 && mY <= 440) {
                    fazeHry = 1;
                }
                break;
            case 1:
                if (mX >= 625 && mX <= 660 && mY >= 207 && mY <= 350) {
                    fazeHry = 4;
                } else if (mX >= 665 && mX <= 700 && mY >= 245 && mY <= 350) {
                    fazeHry = 2;
                } else if (mX >= 712 && mX <= 745 && mY >= 207 && mY <= 350) {
                    fazeHry = 0;
                }
                break;
            case 2:

                break;
            case 3:
                System.out.println(e.getX() + "    " + e.getY());
                break;
        }
    }

    @Override
    public void mousePressed(MouseEvent e) {
        e.consume();
    }

    @Override
    public void mouseReleased(MouseEvent e) {
        e.consume();
    }

    @Override
    public void mouseEntered(MouseEvent e) {
        e.consume();
    }

    @Override
    public void mouseExited(MouseEvent e) {
        e.consume();
    }
}
