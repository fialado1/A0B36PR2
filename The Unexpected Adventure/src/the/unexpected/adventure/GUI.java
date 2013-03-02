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
    private int[] widthImgStartM = new int[4];
    private int[] heightImgStartM = new int[4];
    private int[] coordXImgStartM = new int[4];
    private int[] coordYImgStartM = new int[4];
    private int widthScreen;
    private int heightScreen;
    private int fazeHry = 1;
    private int mX;
    private int mY;
    private int startM;

    public GUI() {
        creatAndShowGUI();
    }

    public void creatAndShowGUI() {

        vlakno.start();

        //LOAD IMAGES
        int pocitadlo = 0;
        for (int j = 0; j < imgDoorAnim.length; j++) {
            imgDoorAnim[j] = loadImage("openingDoorAnim/door_" + pocitadlo + ".jpg").getImage();
            pocitadlo += 5;
        }
        for (int j = 0; j < imgStartMenu.length; j++) {
            imgStartMenu[j] = loadImage("startMenu/door_0_" + j + ".jpg").getImage();
            widthImgStartM[j] = imgStartMenu[j].getWidth(this);
            heightImgStartM[j] = imgStartMenu[j].getHeight(this);
        }

        addMouseMotionListener(this);
        addMouseListener(this);

        //SET ANYTHING AROUND SCREEN & WINDOW
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        widthScreen = (int) d.getWidth();
        heightScreen = (int) d.getHeight();
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = env.getDefaultScreenDevice();


        setTitle("The Unexpected Journey");
        setIconImage(loadImage("icon/adventura.ico").getImage());
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

    public void startMenu(Graphics g) {
        g.drawImage(imgStartMenu[startM], 0, 0, widthScreen, heightScreen, this);

    }
    public void loadMenu(Graphics g){
        g.drawImage(imgDoorAnim[0], 0, 0, widthScreen, heightScreen, this);
        
    }

    public void doorAnim(Graphics g) {
        for (int j = 0; j < imgDoorAnim.length; j++) {
            g.drawImage(imgDoorAnim[j], 0, 0, widthScreen, heightScreen, this);
            try {
                vlakno.sleep(50);
            } catch (InterruptedException ex) {
                Logger.getLogger(GUI.class.getName()).log(Level.SEVERE, null, ex);
            }

            //zoom na temnotu za otevrenymi dvermi
            if ((j + 1) == imgDoorAnim.length) {
                int moveX = 0;
                int moveY = 0;
                for (; moveX <= 4000 || moveY <= 4000; moveX += 100, moveY += 80) {
                    g.drawImage(imgDoorAnim[j], -moveX, -moveY, widthScreen + (2 * moveX), heightScreen + (2 * moveY), this);
                }
            }
        }
        fazeHry = 2;
    }

    @Override
    public void paint(Graphics g) {
        switch (fazeHry) {
            case 0://ukonceni hry - vyskoci tabulka jestli si je hrac jisty nebo podekovani mu za zahrani si
                System.exit(1);
                break;
            case 1://uvodni menu - new, load, quit adventure
                startMenu(g);
                break;
            case 2://nova hra - vytvoreni nove postavy
                break;
            case 3://tabulka s moznosti naloudovani ulozene hry
                loadMenu(g);
                break;
            case 4://animace otevreni dveri (spusti se pri kliknuti na novou hru a i po spusteni hry po zvoleni ulozene hry)
                doorAnim(g);
                break;
            case 5:
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
            case 1:
                if (mX >= 625 && mX <= 660 && mY >= 207 && mY <= 350) {
                    startM = 1;
                } else if (mX >= 665 && mX <= 700 && mY >= 245 && mY <= 350) {
                    startM = 2;
                } else if (mX >= 712 && mX <= 745 && mY >= 207 && mY <= 350) {
                    startM = 3;
                } else {
                    startM = 0;
                }
                break;
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        mX = e.getX();
        mY = e.getY();
        switch (fazeHry) {
            case 1:
                if (mX >= 625 && mX <= 660 && mY >= 207 && mY <= 350) {
                    fazeHry = 3;
                } else if (mX >= 665 && mX <= 700 && mY >= 245 && mY <= 350) {
                    fazeHry = 4;
                } else if (mX >= 712 && mX <= 745 && mY >= 207 && mY <= 350) {
                    fazeHry = 0;
                }
                break;
            case 2:
                fazeHry = 1;
                break;
            case 3:
                fazeHry = 1;
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
