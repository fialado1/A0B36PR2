package the.unexpected.adventure.GUI;

import java.awt.*;
import javax.swing.*;

/**
 *
 * @author Dominik
 */
public class MyButton extends JButton {

    private Image actual;
    private int coordX;
    private int coordY;
    private int width;
    private int height;
    private int clickedX;
    private int clickedY;

    public MyButton(String nameOfCommand, String imageName1, String imageName2, int coordX, int coordY, int width, int height) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.width = width;
        this.height = height;
//        this.addMouseListener(listener1);
//        addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                setClicked(true);
//            }
//        });
        //set looks of button
        this.setActionCommand(nameOfCommand);
        if (imageName1 != null) {
            setIcon(toResizeIcon(imageName1));
            this.setDisabledIcon(toResizeIcon(imageName1));
        }
        if (imageName2 != null) {
            setRolloverIcon(toResizeIcon(imageName2));
            setPressedIcon(toResizeIcon(imageName2));
            setSelectedIcon(toResizeIcon(imageName2));
        }
        //nastaveni transparentniho pozadi tlacitka
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }
//    public MouseListener listener1 = new MouseAdapter() {
//        @Override
//        public void mouseClicked(MouseEvent e) {
//            System.out.println("X on screen " + e.getXOnScreen());
//            System.out.println("Y on screen " + e.getYOnScreen());
//            clickedX = e.getXOnScreen();
//            clickedY = e.getYOnScreen();
//            System.out.println("clickedX " + clickedX);
//            System.out.println("cleckedY " + clickedY);
//        }
//    };

    public int getClickedX() {
        return clickedX;
    }

    public int getClickedY() {
        return clickedY;
    }

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public void ActualImage(Image img) {
        this.actual = img;
    }

    public final ImageIcon toResizeIcon(String name) {
        Image i = new ImageIcon("images/" + name).getImage();
        Image is = i.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        ImageIcon resizedIcon = new ImageIcon(is);
        return resizedIcon;
    }

    public int getCoordX() {
        return coordX;
    }

    public void setCoordX(int coordX) {
        this.coordX = coordX;
    }

    public int getCoordY() {
        return coordY;
    }

    public void setCoordY(int coordY) {
        this.coordY = coordY;
    }
}
