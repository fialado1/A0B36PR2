/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure.GUI;

import java.awt.event.ActionListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import javax.swing.JMenuItem;
import javax.swing.JPopupMenu;

/**
 *
 * @author Dominik
 */
public class ItemButton extends MyButton implements MouseMotionListener {

    private int type;
    private int whereIsItInRoom;
    private boolean itsInRoom;
    private boolean itsInInv;
    private String itemName;
    private JPopupMenu invPopupMenu = new JPopupMenu();
    private JPopupMenu roomPopupMenu = new JPopupMenu();
    private JMenuItem add = new JMenuItem("přidat předmět");
    private JMenuItem remove = new JMenuItem("odebrat předmět");
    //1-k noseni, 2-k pouzivani, 3-k uziti, 4-ke kombin.
    private JMenuItem equip = new JMenuItem("vybavit se");
    private JMenuItem use = new JMenuItem("použít");
    private JMenuItem consume = new JMenuItem("požít");
    private JMenuItem combine = new JMenuItem("zkombinovat");
    public JMenuItem[] listOfMenuItems = new JMenuItem[6];

    public ItemButton(String imageName1, String imageName2, int coordX, int coordY, int width, int height, int type, boolean itsInRoom) {
        super("item", imageName1, imageName2, coordX, coordY, width, height);
        this.type = type;
        this.itsInRoom = itsInRoom;
        setInvPopupMenu();
        setRoomPopupMenu();
        add.setActionCommand("addItem");
        listOfMenuItems[0] = add;
        remove.setActionCommand("removeItem");
        listOfMenuItems[1] = remove;
        equip.setActionCommand("use");
        listOfMenuItems[2] = equip;
        use.setActionCommand("use");
        listOfMenuItems[3] = use;
        consume.setActionCommand("use");
        listOfMenuItems[4] = consume;
        combine.setActionCommand("combine");
        listOfMenuItems[5] = combine;
        this.addMouseMotionListener(this);
    }

    public String getItemName() {
        return itemName;
    }

    public void setItemName(String itemName) {
        this.itemName = itemName;
    }

    public int getType() {
        return type;
    }

    public int getWhereIsItInRoom() {
        return whereIsItInRoom;
    }

    public void setWhereIsItInRoom(int whereIsItInRoom) {
        this.whereIsItInRoom = whereIsItInRoom;
    }

    public boolean isItsInRoom() {
        return itsInRoom;
    }

    public void setItsInRoom(boolean itsInRoom) {
        this.itsInRoom = itsInRoom;
        this.itsInInv = !itsInRoom;
    }

    public boolean isItsInInv() {
        return itsInInv;
    }

    public void setItsInInv(boolean itsInInv) {
        this.itsInInv = itsInInv;
        this.itsInRoom = !itsInInv;
    }

    public void addActionListToMenuItem(ActionListener al) {
        for (int i = 0; i < listOfMenuItems.length; i++) {
            listOfMenuItems[i].addActionListener(al);
        }
    }

    public JPopupMenu getInvPopupMenu() {
        return invPopupMenu;
    }

    public JPopupMenu getRoomPopupMenu() {
        return roomPopupMenu;
    }

    public final void setRoomPopupMenu() {
        roomPopupMenu.add(add);
//        switch (this.type) {
//            case 1:
//                invPopupMenu.add(equip);
//                break;
//            case 2:
//                invPopupMenu.add(use);
//                break;
//            case 3:
//                invPopupMenu.add(consume);
//                break;
//            case 4:
//                invPopupMenu.add(combine);
//                break;
//        }
    }

    public final void setInvPopupMenu() {
        switch (this.type) {
            case 1:
                invPopupMenu.add(equip);
                break;
            case 2:
                invPopupMenu.add(use);
                break;
            case 3:
                invPopupMenu.add(consume);
                break;
            case 4:
                invPopupMenu.add(combine);
                break;
        }
        invPopupMenu.add(remove);
    }

    @Override
    public void mouseDragged(MouseEvent e) {
    }

    @Override
    public void mouseMoved(MouseEvent e) {
    }
}
