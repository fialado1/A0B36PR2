/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure.GUI;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Point;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.IOException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.imageio.ImageIO;
import javax.swing.JOptionPane;

/**
 *
 * @author Dominik
 */
public class Character extends MyClass2 {

    private int actualRoom = 0;
//  STUFF AROUND MOVEMENT OF CHARACTER    
    private int actualX;
    private int actualY;
    private int velocity = 7;
    private double mainAngle;
    private double koef = 1;
    private Point[] vanishingPoint;
    private int[] florHeight;
    double[] sinValue = new double[360];
    double[] cosValue = new double[360];
//  images etc.
    private int whichBody;
    private int whichHead;
    private Image actualLooks;
    private Image actualB;
    private Image actualH;
    private Image[] imgBody = new Image[4];
    private Image[] imgHead = new Image[4];
    private Image[] imgOfCharacter = new Image[12];
    private int[] widthImgBody = new int[4];
    private int[] heightImgBody = new int[4];
    private boolean can;
    private String name = null;

    public Character() throws IOException {
        createAndShowGUI();
    }

    @Override
    public void createAndShowGUI() {
        super.createAndShowGUI();

        for (int i = 0; i < imgBody.length; i++) {
            try {
                imgBody[i] = ImageIO.read(new File("images/character/body/04/" + i + ".png"));
            } catch (IOException ex) {
                JOptionPane.showMessageDialog(null, "Chyba při načítání obrázků.", "Tády dády dá!", JOptionPane.ERROR_MESSAGE);
                System.exit(1);
            }
            widthImgBody[i] = coorTransWidth(imgBody[i].getWidth(null));
            heightImgBody[i] = coorTransHeight(imgBody[i].getHeight(null));

        }
        for (int i = 0; i < 360; i++) {
            // have to convert in radian (and negate them if I go counter clockwise)
            double radiant = Math.toRadians((double) i);
            // because Java sin/cos method use radian
            sinValue[i] = Math.sin(radiant);
            cosValue[i] = Math.cos(radiant);
        }

        actualX = widthScreen / 2 - widthImgBody[0];
        actualY = heightScreen;
//        this.setLayout(null);
    }

    public String getPlayersName() {
        return name;
    }

    public void setPlayersName(String name) {
        this.name = name;
    }

    public int getActualRoom() {
        return actualRoom;
    }

    public void setActualRoom(int actualRoom) {
        this.actualRoom = actualRoom;
    }

    public double sin(int degree) {
        return sinValue[degree % 360];
    }

    public double cos(int degree) {
        return cosValue[degree % 360];
    }

    public void setMainAngle() {
        this.mainAngle = Math.abs((int) Math.toDegrees(Math.atan((double) (heightScreen - vanishingPoint[actualRoom].y) / (vanishingPoint[actualRoom].x))));
    }

    public int getVelocity() {
        return velocity;
    }

    public void setFlorHeight(int florHeight, int i) {
        this.florHeight[i] = coorTransHeight(florHeight);
    }

    public void setFlorHeightLenght(int lenght) {
        this.florHeight = new int[lenght];
    }

    public Point[] getVanishingPoint() {
        return vanishingPoint;
    }

    public void setVanishingPoint(int vanishingPointX, int vanishingPointY, int i) {
        vanishingPoint[i] = new Point();
        this.vanishingPoint[i].x = coorTransWidth(vanishingPointX);
        this.vanishingPoint[i].x = coorTransWidth(vanishingPointX);
    }

    public void setVanishingPointLenght(int lenght) {
        this.vanishingPoint = new Point[lenght];
    }

    public void setActualPosition(int x, int y) {
        actualX = x;
        actualY = y;
    }

    public int getActualX() {
        return actualX;
    }

    public int getActualY() {
        return actualY;
    }

    public void setActualX(int actualX) {
        this.actualX = actualX;
    }

    public void setActualY(int actualY) {
        this.actualY = actualY;
    }

    public void movee(int x, int y) {
        System.out.println("sdf");
        System.out.println("x a y " + x + " " + y);
        System.out.println("Ax ? Ay " + actualX + " " + actualY);
        if (y > actualY) {
            can = true;
            do {
                System.out.println("Ax  Ay " + actualX + " " + actualY);
                System.out.println("up");
                moveDown(actualX, actualY);
                repaint();
            } while (y != actualY && can);
        } else if (y < actualY) {
            can = true;
            do {
                System.out.println("Ax  Ay " + actualX + " " + actualY);
                System.out.println("down");
                moveUp(actualX, actualY);
                repaint();
            } while (y + 5 < actualY && can);
        }
        if (x < actualX) {
            can = true;
            do {
                System.out.println("Ax  Ay " + actualX + " " + actualY);
                System.out.println("left");
                moveLeft();
            } while (x != actualX && can);
        }
    }

    public boolean tryToMove(double angle, int x, int y) {
//        if (vanishingPoint[actualRoom].x*x+(vanishingPoint[actualRoom].y-heightScreen)*y+heightScreen*(heightScreen-vanishingPoint[actualRoom].y)==0) {

//        if (x <= widthScreen / 2) {
//            if (((y >= heightScreen - florHeight[actualRoom]) && (y <= heightScreen)) && (angle > (mainAngle - 10) && angle <= 90)) {
//                return true;
//            } else {
//                return false;
//            }
//        } else {
//            if (((y >= heightScreen - florHeight[actualRoom]) && (y <= heightScreen)) && (angle > (mainAngle +10 ) && angle <= 90)) {
//                return true;
//            } else {
//                return false;
//            }
//        }

        if ((y >= heightScreen - florHeight[actualRoom]) && (y <= heightScreen) && angle <= 90) {
//            if ((x <= widthScreen / 2) && (angle > (mainAngle - 10))) {
            return true;
//            } else if ((x >= widthScreen / 2) && (angle > (mainAngle + 14))) {
//                return true;
//            } else {
//                return false;
//            }
        } else {
            return false;
        }
    }

    public void moveLeft() {
        if ((getActualX() - getVelocity()) > (-1) * (((-heightScreen * (heightScreen - getVanishingPoint()[getActualRoom()].y)) - (getVanishingPoint()[getActualRoom()].y - heightScreen) * getActualY()) / (getVanishingPoint()[getActualRoom()].x))) {
            setActualX(getActualX() - getVelocity());
        } else {
            can = false;
        }
    }

    public void moveRight() {
        if ((getActualX() - getVelocity()) < (-1) * (((-heightScreen * (heightScreen - getVanishingPoint()[getActualRoom()].y)) - (widthScreen * (widthScreen - getVanishingPoint()[actualRoom].x)) - (getVanishingPoint()[getActualRoom()].y - heightScreen) * getActualY()) / (getVanishingPoint()[getActualRoom()].x - widthScreen))) {
            setActualX(getActualX() - getVelocity());
        } else {
            can = false;
        }
    }

    public void moveUp(int x, int y) {
        double angle = 0;
        int newX = 0, newY = 0;
        if (x <= widthScreen / 2) {
            angle = Math.abs((int) Math.toDegrees(Math.atan((double) (y - vanishingPoint[actualRoom].y) / (0.5 * widthScreen - x))));
//            newX = (int) (x + velocity * 1 * (sin((int) (angle))));
//            newY = (int) (y - velocity * 1 * (cos((int) (angle))));
            newX = (int) (x + velocity * 1.35 * (cos((int) (angle))));
            newY = (int) (y - velocity * 1 * (sin((int) (angle))));
        }
        if (x >= widthScreen / 2) {
            angle = Math.abs((int) Math.toDegrees(Math.atan((double) (vanishingPoint[actualRoom].y - y) / (-0.5 * widthScreen + x))));
//            newX = (int) (x - velocity * 1 * (sin((int) (angle))));
//            newY = (int) (y - velocity * 1 * (cos((int) (angle))));
            newX = (int) (x - velocity * 1 * (cos((int) (angle))));
            newY = (int) (y - velocity * 1 * (sin((int) (angle))));
        }
        setKoef(newY);
        if (tryToMove(angle, newX + (int) ((widthImgBody[0]) * koef), newY)) {
            setActualPosition(newX, newY);
        } else {
            can = false;
        }
    }

    public void moveDown(int x, int y) {
        int angle = 0;
        int newX = 0, newY = 0;
        if (x <= widthScreen / 2) {
            angle = Math.abs((int) Math.toDegrees(Math.atan((double) (y - vanishingPoint[actualRoom].y) / (vanishingPoint[actualRoom].x - x))));
            newX = (int) (x - velocity * 0.65 * (cos(angle)));
            newY = (int) (y + velocity * 1 * (sin(angle)));
        }
        if (x >= widthScreen / 2) {
            angle = Math.abs((int) Math.toDegrees(Math.atan((double) (y - vanishingPoint[actualRoom].y) / (-0.5 * widthScreen + x))));
            newX = (int) (x + velocity * 1 * (cos(angle)));
            newY = (int) (y + velocity * 1 * (sin(angle)));
        }
        setKoef(newY);
        if (tryToMove(angle, newX + (int) ((widthImgBody[0]) * koef), newY)) {

            setActualPosition(newX, newY);
        } else {
            can = false;
        }
    }

    public void setKoef(int k) {
//        koef = 0.28 * Math.pow(Math.E, 0.00505145 * k);
//       koef = 0.0000000008*k;
        koef = (double) (k - heightImgBody[0]) / florHeight[actualRoom];

    }

    public Image createCharacter(Image head, Image body) throws IOException {
        int hWidth = head.getWidth(this);
        int hHeight = head.getHeight(this);
        int bWidth = body.getWidth(this);
        int bHeight = body.getHeight(this);
        int newHHeight = 200;
        int newHWidth = newHHeight * hWidth / hHeight;
        int newBHeight = 550;
        int newBWidth = newBHeight * bWidth / bHeight;

        Image headScaled = head.getScaledInstance(newHWidth, newHHeight, Image.SCALE_SMOOTH);
        Image bodyScaled = body.getScaledInstance(newBWidth, newBHeight, Image.SCALE_SMOOTH);

        BufferedImage fin = new BufferedImage(Math.max(newHWidth, newBWidth), newHHeight + newBHeight - 10, BufferedImage.TYPE_INT_ARGB);
        Graphics g = fin.getGraphics();
        g.drawImage(bodyScaled, 0, newHHeight - 10, this);
        g.drawImage(headScaled, (newBWidth - newHWidth) / 2, 0, this);
        g.dispose();

        ImageIO.write(fin, "png", new File("images/character", name + ".png"));

        Image image;

        image = ImageIO.read(new File("images/character", name + ".png"));

        return image;
    }

    public void setWhichBody(int whichBody) {
        this.whichBody = whichBody;
    }

    public void setWhichHead(int whichHead) {
        this.whichHead = whichHead;
    }

    public void updateImageOfBody() {
        try {
            Image body = ImageIO.read(new File("images/character/body/0" + (whichBody + 1) + "/0.png"));
            Image head = ImageIO.read(new File("images/character/head/0" + (whichHead + 1) + "/0.png"));
            actualLooks = createCharacter(head, body);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Chyba při načítání obrázků.", "Tády dády dá!", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
    }

    public void loadImageOfBody() {
        try {
            actualLooks = ImageIO.read(new File("images/character", name + ".png"));
        } catch (IOException ex) {
            Logger.getLogger(Character.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(actualLooks, actualX, actualY - 750, 172, 750, null);
        g.drawImage(actualLooks, actualX, actualY - (int) (750 * koef), (int) (172 * koef), (int) (750 * koef), null);
    }
}
