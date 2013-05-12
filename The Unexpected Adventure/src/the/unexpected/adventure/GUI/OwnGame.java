package the.unexpected.adventure.GUI;

import java.awt.*;
import java.awt.event.ActionListener;
import java.awt.font.TextAttribute;
import java.io.IOException;
import java.text.AttributedString;
//import the.unexpected.adventure.GUI.rooms.*;

/**
 *
 * @author Dominik
 */
public class OwnGame extends GamePanel {

    private boolean showRoom = true;
    private int actualRoom = 0;
    private int amountOfRooms;
    private String dir;
//  
    public boolean run = true;
    Image imageC;
    Image imageB;
    Image imageA;
    private ViewOfRoom[] rooms;
    private ViewOfRoom actualRom;
    private ItemButton actualItem;

    public OwnGame(int amountOfRooms, String dir) throws IOException {
        this.amountOfRooms = amountOfRooms;
        this.dir = dir;
        createAndShowGUI();
    }

    @Override
    public void createAndShowGUI() {
        super.createAndShowGUI();

        amountOfRooms = 3;

        rooms = new ViewOfRoom[amountOfRooms];
        for (int i = 0; i < rooms.length; i++) {
            rooms[i] = new ViewOfRoom(widthScreen, heightScreen, i, dir);
        }
        actualRom = rooms[0];
    }

    public void addActionListenerForAllRooms(ActionListener controller) {
        for (int i = 0; i < rooms.length; i++) {
            rooms[i].addActionListenerToList(controller, rooms[i].listOfComp);
            rooms[i].addActionListenerToList(controller, rooms[i].listOfItems);
        }
    }

    public ViewOfRoom getRoom(int index) {
        return rooms[index];
    }

    public ItemButton getActualItem() {
        return actualItem;
    }

    public void setActualItem(ItemButton actualItem) {
        this.actualItem = actualItem;
    }

    public ViewOfRoom getActualRom() {
        return actualRom;
    }

    public void setActualRom() {
        actualRom = rooms[actualRoom];
    }

    public int getAmountOfRooms() {
        return amountOfRooms;
    }


    public void setShowRoom(boolean showRoom) {
        this.showRoom = showRoom;
    }

    public int getActualRoom() {
        return actualRoom;
    }

    public void setActualRoom(int actualRoom) {
        this.actualRoom = actualRoom;
        setActualRom();
    }

    public boolean isRun() {
        return run;
    }

    public void setRun(boolean run) {
        this.run = run;
    }

    public AttributedString printMessage(String msg) {
        AttributedString as = new AttributedString(msg);
        as.addAttribute(TextAttribute.FONT, new Font("Arial", Font.BOLD, 30), 0, 1);
        as.addAttribute(TextAttribute.SIZE, 20);
        return as;
    }

    @Override
    public void paint(Graphics g) {
        if (showRoom) {
            actualRom.paint(g);
        }
    }

    private void drawString(Graphics g, String text, int x, int y) {
        int countOfRow = 0;
        for (String line : text.split("\n")) {
            g.drawString(line, x, y += g.getFontMetrics().getHeight());
            countOfRow++;
        }
        g.setColor(new Color(0, 0, 0, 50));
        g.fillRect(x, y, 1200, countOfRow * 30);
    }
}
