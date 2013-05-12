/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure.GUI;

import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

/**
 *
 * @author Dominik
 */
public class ImageLikeJButton extends JButton {

    private Image img1;
    private Image img2;
    private Image actual;
    private int coordX;
    private int coordY;
    private int width;
    private int height;
    private boolean clicked = false;
    private boolean moved = false;

    public ImageLikeJButton(String nameOfCommand,String imageName1, String imageName2, int coordX, int coordY, int width, int height) {
        this.coordX = coordX;
        this.coordY = coordY;
        this.width = width;
        this.height = height;
        addMouseListener(listener1);
//        addActionListener(new ActionListener() {
//            @Override
//            public void actionPerformed(ActionEvent e) {
//                System.out.println(isClicked() + " clik");
//                setClicked(true);
//                System.out.println(isClicked() + " clik");
//            }
//        });
        //set looks of button
        this.setActionCommand(nameOfCommand);
        setIcon(toResizeIcon(imageName1));
        setRolloverIcon(toResizeIcon(imageName2));
        setPressedIcon(toResizeIcon(imageName2));
        //nastaveni transparentniho pozadi tlacitka
        setOpaque(false);
        setContentAreaFilled(false);
        setBorderPainted(false);
    }

    private MouseListener listener1 = new MouseAdapter() {
        @Override
        public void mouseClicked(MouseEvent e) {
            setClicked(true);
            System.out.println("sfmsdfdsfqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqqq");
        }
    };

    @Override
    public Dimension getPreferredSize() {
        return new Dimension(width, height);
    }

    public void ActualImage(Image img) {
        this.actual = img;
    }

    private ImageIcon toResizeIcon(String name) {
        Image i = new ImageIcon("images/" + name).getImage();
        Image is = i.getScaledInstance(width, height, Image.SCALE_DEFAULT);
        ImageIcon resizedIcon = new ImageIcon(is);
        return resizedIcon;
    }

    public boolean isClicked() {
        return clicked;
    }

    public void setClicked(boolean cliked) {
        this.clicked = cliked;
    }

    public boolean isMoved() {
        return moved;
    }

    public void setMoved(boolean moved) {
        this.moved = moved;
    }
}
