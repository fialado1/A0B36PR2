package the.unexpected.adventure.GUI;

/**
 *
 * @author Dominik
 */
import java.awt.*;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import javax.imageio.ImageIO;
import javax.swing.*;

public class ViewOfRoom extends MyClass {
//0-S, 1-J, 2-V, 3-Z

    Image imgOfRoom[] = new Image[3];
    int florHeight;
    int vanishingPoint;
    public int whichImg = 0;
    public boolean lightOn = true;
    private ItemButton actualItem;
    public ArrayList<JComponent> listOfItems = new ArrayList<>();
    public ArrayList<JComponent> listOfBoxes = new ArrayList<>();
//
    private String nameOfRoom = "";
    private int numOfRoom;
    private int amountOfDoors;
    private int amountOfBoxes;
    private int amountOfItems;
    private int amountOfSpecials;
    private DoorButton[] doors;
    private BoxButton[] boxes;
    private ItemButton[] items;
    private MyButton[] specials;

    public ViewOfRoom(int widthScreen, int heightScreen, int numOfRoom, String dir) {
        super(widthScreen, heightScreen);
        this.numOfRoom = numOfRoom;

        File file = new File("map/view/" + numOfRoom + ".txt");
        File file2 = new File(dir + "/view/contentOfRooms/" + numOfRoom + ".txt");

        if (!file.exists()) {
            JOptionPane.showMessageDialog(null, "Chyba při načítání souborů.", "Soubor neexistuje!", JOptionPane.ERROR_MESSAGE);
            return;
        }

        if (!(file.isFile() && file.canRead())) {
            JOptionPane.showMessageDialog(null, "Chyba při načítání souborů.", "Nelze nacitat data.", JOptionPane.ERROR_MESSAGE);
            return;
        }

        try {
            FileInputStream fis = new FileInputStream(file);
            FileInputStream fis2 = new FileInputStream(file2);
            char c0, c1;
            do {
                c0 = (char) fis.read();
                if (c0 != '#') {
                    if (c0 >= ' ' && c0 <= 'z') {
                        nameOfRoom += c0;
                    }
                }
            } while (c0 != '#');
            for (int i = 1; i <= 3; i++) {
                imgOfRoom[i - 1] = ImageIO.read(new File("images/rooms/" + numOfRoom + "/" + nameOfRoom + i + ".png"));
            }
            do {
                c0 = (char) fis.read();
                if (c0 != '#') {
                    amountOfDoors =  java.lang.Character.getNumericValue(c0);
                }
            } while (c0 != '#');
            int numRange;
            doors = new DoorButton[amountOfDoors];
            for (int i = 0; i < doors.length; i++) {
                numRange = 1000;
                int[] ii = new int[5];
                int what = 0;
                do {
                    c1 = (char) fis.read();
                    if (c1 != '#') {
                        if (c1 >= '0' && c1 <= '9') {
                            ii[what] += numRange *  java.lang.Character.getNumericValue(c1);
                            numRange /= 10;
                        }
                        if (c1 == ',') {
                            numRange = 1000;
                            what++;
                        }
                    }
                } while (c1 != '#');
                for (int j = 0; j < 5; j++) {
                }
                doors[i] = new DoorButton("door", "rooms/" + numOfRoom + "/door" + (i + 1) + "_1.png", "rooms/" + numOfRoom + "/door" + (i + 1) + "_2.png", coorTransWidth(ii[0]), coorTransHeight(ii[1]), coorTransWidth(ii[2]), coorTransHeight(ii[3]), ii[4]);
                doors[i].setBounds(coorTransWidth(ii[0]), coorTransHeight(ii[1]), coorTransWidth(ii[2]), coorTransHeight(ii[3]));
                listOfComp.add(doors[i]);
            }

            do {
                c0 = (char) fis.read();
                if (c0 != '#') {
                    amountOfBoxes = java.lang.Character.getNumericValue(c0);
                }
            } while (c0 != '#');

            boxes = new BoxButton[amountOfBoxes];
            for (int i = 0; i < boxes.length; i++) {
                numRange = 1000;
                int[] ii = new int[4];
                int what = 0;
                do {
                    c1 = (char) fis.read();
                    if (c1 != '#') {
                        if (c1 >= '0' && c1 <= '9') {
                            ii[what] += numRange *  java.lang.Character.getNumericValue(c1);
                            numRange /= 10;
                        }
                        if (c1 == ',') {
                            numRange = 1000;
                            what++;
                        }
                    }
                } while (c1 != '#');
                boxes[i] = new BoxButton("box", "rooms/" + numOfRoom + "/box" + (i + 1) + "_1.png", "rooms/" + numOfRoom + "/box" + (i + 1) + "_2.png", coorTransWidth(ii[0]), coorTransHeight(ii[1]), coorTransWidth(ii[2]), coorTransHeight(ii[3]));
                boxes[i].setBounds(coorTransWidth(ii[0]), coorTransHeight(ii[1]), coorTransWidth(ii[2]), coorTransHeight(ii[3]));
                listOfComp.add(boxes[i]);
                listOfBoxes.add(boxes[i]);
            }

            do {
                c0 = (char) fis2.read();
                if (c0 != '#') {
                    amountOfItems =  java.lang.Character.getNumericValue(c0);
                }
            } while (c0 != '#');

            items = new ItemButton[amountOfItems];
            for (int i = 0; i < items.length; i++) {
                numRange = 1000;
                int[] ii = new int[6];
                int what = 0;
                String itemName = "";
                do {
                    c1 = (char) fis2.read();
                    if (c1 != '#') {
                        if (c1 >= '0' && c1 <= '9' && what < 6) {
                            ii[what] += numRange *  java.lang.Character.getNumericValue(c1);
                            numRange /= 10;
                        }
                        if (c1 >= ' ' && c1 <= 'z' && what > 5) {
                            itemName += c1;
                        }
                        if (c1 == ',') {
                            numRange = 1000;
                            what++;
                        }
                    }
                } while (c1 != '#');
                items[i] = new ItemButton("items/" + itemName + "1.png", "items/" + itemName + "2.png", coorTransWidth(ii[0]), coorTransHeight(ii[1]), coorTransWidth(ii[2]), coorTransHeight(ii[3]), ii[4], true);
                items[i].setBounds(coorTransWidth(ii[0]), coorTransHeight(ii[1]), coorTransWidth(ii[2]), coorTransHeight(ii[3]));
                items[i].setItemName(itemName);
                if (ii[5] == 0) {
                    listOfItems.add(items[i]);
                    items[i].setWhereIsItInRoom(0);
                } else {
                    System.out.println(boxes[ii[5] - 1] + " wwww " + items[i]);
                    boxes[ii[5] - 1].addCompToTheBox(items[i]);
                    items[i].setWhereIsItInRoom(ii[5]);
                }
            }

            do {
                c0 = (char) fis.read();
                if (c0 != '#') {
                    amountOfSpecials =  java.lang.Character.getNumericValue(c0);
                }
            } while (c0 != '#');

            specials = new MyButton[amountOfSpecials];
            for (int i = 0; i < specials.length; i++) {
                numRange = 1000;
                int[] ii = new int[4];
                int what = 0;
                String cmdName = "";
                do {
                    c1 = (char) fis.read();
                    if (c1 != '#') {
                        if (c1 >= '0' && c1 <= '9') {
                            ii[what] += numRange *  java.lang.Character.getNumericValue(c1);
                            numRange /= 10;
                        }
                        if (c1 >= ' ' && c1 <= 'z' && what > 3) {
                            cmdName += c1;
                        }
                        if (c1 == ',') {
                            numRange = 1000;
                            what++;
                        }
                    }
                } while (c1 != '#');
                specials[i] = new MyButton(cmdName, "rooms/" + numOfRoom + "/special" + (i + 1) + "_1.png", "rooms/" + numOfRoom + "/special" + (i + 1) + "_2.png", coorTransWidth(ii[0]), coorTransHeight(ii[1]), coorTransWidth(ii[2]), coorTransHeight(ii[3]));
                specials[i].setBounds(coorTransWidth(ii[0]), coorTransHeight(ii[1]), coorTransWidth(ii[2]), coorTransHeight(ii[3]));
                listOfComp.add(specials[i]);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Chyba při načítání souborů.", "Tády dády dá!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setItemsEnable() {
        for (int i = 0; i < listOfItems.size(); i++) {
            listOfItems.get(i).setEnabled(true);
        }
    }

    public void setItemsDisable() {
        for (int i = 0; i < listOfItems.size(); i++) {
            listOfItems.get(i).setEnabled(false);
        }
    }

    public void setButtonsIcon(String imgName, JButton jb) {
        jb.setIcon(toResizeIcon(imgName, jb));
    }

    public ItemButton getItemFromRoom(int index) {
        return (ItemButton) listOfItems.get(index);
    }

    public ItemButton getActualItem() {
        return actualItem;
    }

    public void setActualItem(ItemButton actualItem) {
        this.actualItem = actualItem;
    }

    public int indexOfItem(ItemButton ib) {
        return listOfItems.indexOf(ib);
    }

    public void addItemsToRoom(ArrayList<JComponent> ajc) {
        listOfItems.addAll(ajc);
    }

    public void addItemToRoom(JComponent jc) {
        listOfItems.add(jc);
    }

    public void removeItemFromRoom(JComponent jc) {
        listOfItems.remove(jc);
    }

    public void removeItemFromRoom(int i) {
        actualItem = (ItemButton) listOfItems.remove(i);
    }

    public ImageIcon toResizeIcon(String name, JButton jb) {
        Image i = new ImageIcon("images/" + name).getImage();
        Image is = i.getScaledInstance(jb.getWidth(), jb.getHeight(), Image.SCALE_DEFAULT);
        ImageIcon resizedIcon = new ImageIcon(is);
        return resizedIcon;
    }

    public void paint(Graphics g) {
        g.drawImage(imgOfRoom[whichImg], 0, 0, widthScreen, heightScreen, null);
    }

    public int getWhichImg() {
        return whichImg;
    }

    public void setWhichImg(int whichImg) {
        this.whichImg = whichImg;
    }

    public boolean isLightOn() {
        return lightOn;
    }

    public void setLightOn(boolean lightOn) {
        this.lightOn = lightOn;
    }

    public void setButtonsIconDark() {
        for (int i = 0; i < doors.length; i++) {
            doors[i].setIcon(toResizeIcon("rooms/" + numOfRoom + "/door" + (i + 1) + "_3.png", doors[i]));
        }
        for (int i = 0; i < boxes.length; i++) {
            boxes[i].setIcon(toResizeIcon("rooms/" + numOfRoom + "/box" + (i + 1) + "_3.png", boxes[i]));
        }
        for (int i = 0; i < specials.length; i++) {
            specials[i].setIcon(toResizeIcon("rooms/" + numOfRoom + "/special" + (i + 1) + "_3.png", specials[i]));
        }
    }

    public void setButtonsIconLight() {
        for (int i = 0; i < doors.length; i++) {
            doors[i].setIcon(toResizeIcon("rooms/" + numOfRoom + "/door" + (i + 1) + "_1.png", doors[i]));
        }
        for (int i = 0; i < boxes.length; i++) {
            boxes[i].setIcon(toResizeIcon("rooms/" + numOfRoom + "/box" + (i + 1) + "_1.png", boxes[i]));
        }
        for (int i = 0; i < specials.length; i++) {
            specials[i].setIcon(toResizeIcon("rooms/" + numOfRoom + "/special" + (i + 1) + "_1.png", specials[i]));
        }
    }
}
