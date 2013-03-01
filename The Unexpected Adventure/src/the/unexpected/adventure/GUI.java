/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Dominik
 */
public final class GUI extends JFrame implements Runnable, MouseListener, MouseMotionListener {

    Thread vlakno = new Thread(this);
    private Image img;
    private Graphics grp;
    private Image[] imgDoorAnim = new Image[8];
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
            imgDoorAnim[j] = loadImage("openingDoorAnim/celek_" + pocitadlo + ".png").getImage();
            pocitadlo += 5;
        }
        for (int j = 0; j < imgStartMenu.length; j++) {
            imgStartMenu[j] = loadImage("startMenu/door_0_" + j + ".jpg").getImage();
            widthImgStartM[j] = imgStartMenu[j].getWidth(this);
            heightImgStartM[j] = imgStartMenu[j].getHeight(this);
        }

        addMouseMotionListener(this);

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

    public void startMenu(Graphics g) {
        g.drawImage(imgStartMenu[startM], 0, 0, widthScreen, heightScreen, this);
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
                break;
            case 4://animace otevreni dveri (spusti se pri kliknuti na novou hru a i po spusteni hry po zvoleni ulozene hry)
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
        if (fazeHry == 1) {
            if (mX >= 625 && mX <= 660 && mY >= 207 && mY <= 350) {
                startM = 1;
            } else if (mX >= 665 && mX <= 700 && mY >= 245 && mY <= 350) {
                startM = 2;
            } else if (mX >= 712 && mX <= 745 && mY >= 207 && mY <= 350) {
                startM = 3;
            } else {
                startM = 0;
            }
        }
    }

    @Override
    public void mouseClicked(MouseEvent e) {
        e.consume();
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
