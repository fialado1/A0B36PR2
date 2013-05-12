package the.unexpected.adventure.GUI;

import java.awt.*;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Observable;
import java.util.Observer;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.*;
import the.unexpected.adventure.model.Game;
import the.unexpected.adventure.model.Inventory;
import the.unexpected.adventure.model.Player;

/**
 * Hlavni trida view. Je to okno, jehoz kontajner pro komponenty je "obohacen" o
 * JLayeredPane, diky cemuz je mozne vkladat potrebne panely do ruznych vrstev.
 *
 * @author Dominik
 */
public final class GUI extends JFrame implements Runnable, Observer {

    Thread vlakno = new Thread(this);
    private volatile boolean gameRuns = true;
    private Image img;
    private Graphics grp;
    private volatile int fazeHry = 2;
    private JLayeredPane layers = new JLayeredPane();
    public MainMenu mm;
    public NewGameMenu ngm;
    public LoadAndSaveMenu lasm;
    public OwnGame og;
    public Character cha;
    public QuickPanel qp;
    public EscMenu em;
    public InfoAndInventory iai;
    public MessagePanel mp;
    public QuitDialog qd;
    public Loading loading;
    public int mX;
    public int mY;
    private boolean showLoading = false;
    private boolean showEscMenu = false;
    private boolean showInfAndInv = false;
    private boolean showSaveGameMenu = false;
    private boolean showLoadGameMenu = false;
    private ActionListener al;

    public GUI() {
        createAndShowGUI();
    }

    public void createAndShowGUI() {
        try {
            mm = new MainMenu();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Eggs are not supposed to be green.", "Inane error",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        lasm = new LoadAndSaveMenu(true);
        qd = new QuitDialog();
        loading = new Loading();

        //SET ANYTHING AROUND SCREEN & WINDOW
        Dimension d = Toolkit.getDefaultToolkit().getScreenSize();
        GraphicsEnvironment env = GraphicsEnvironment.getLocalGraphicsEnvironment();
        GraphicsDevice defaultScreen = env.getDefaultScreenDevice();
        setTitle("The Unexpected Journey");
        setIconImage(loadImage("icon/adventura.png").getImage());
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

        vlakno.start();

        this.setUndecorated(true);
        this.setResizable(false);
        defaultScreen.setFullScreenWindow(this);
        layers.setPreferredSize(d);

        this.add(layers);
//        this.setLayout(null);
        this.pack();
        this.setVisible(true);
    }

    public ActionListener getAl() {
        return al;
    }

    public boolean isShowLoading() {
        return showLoading;
    }

    public void setShowLoading(boolean showLoading) {
        this.showLoading = showLoading;
    }

    public boolean isShowEscMenu() {
        return showEscMenu;
    }

    public void setShowEscMenu(boolean showEscMenu) {
        this.showEscMenu = showEscMenu;
    }

    public boolean isShowInfAndInv() {
        return showInfAndInv;
    }

    public void setShowInfAndInv(boolean showInfAndInv) {
        this.showInfAndInv = showInfAndInv;
    }

    public boolean isShowLoadGameMenu() {
        return showLoadGameMenu;
    }

    public void setShowLoadGameMenu(boolean showLoadGameMenu) {
        this.showLoadGameMenu = showLoadGameMenu;
    }

    public boolean isShowSaveGameMenu() {
        return showSaveGameMenu;
    }

    public void setShowSaveGameMenu(boolean showSaveGameMenu) {
        this.showSaveGameMenu = showSaveGameMenu;
    }

    public JLayeredPane getLayers() {
        return layers;
    }

    public void setFazeHry(int fazeHry) {
        this.fazeHry = fazeHry;
    }

    public void addListToLayer(ArrayList<JComponent> jcList, Integer i) {
        for (int j = 0; j < jcList.size(); j++) {
            layers.add(jcList.get(j), i);
        }
    }

    public void addToLayer(JComponent jc, Integer i) {
        this.layers.add(jc, i);
    }

    public void removeListFromLayer(ArrayList<JComponent> jcList) {
        for (int j = 0; j < jcList.size(); j++) {
            this.layers.remove(jcList.get(j));
        }
    }

    public void removeFromLayer(JComponent jc) {
        if (jc == null) {
            this.layers.removeAll();
        } else {
            this.layers.remove(jc);
        }
    }

    public void moveListToFront(ArrayList<JComponent> jcList) {
        for (int j = 0; j < jcList.size(); j++) {
            this.layers.moveToFront(jcList.get(j));
        }
    }

    /**
     * Metoda, ktera slouzi ke zobrazeni obrazku naznacujici nacitani dat
     */
    public void loading() {
        layers.removeAll();
        layers.add(loading, new Integer(6));
        loading.repaint();
        try {
            vlakno.sleep(1000);
        } catch (InterruptedException ex) {
        }
    }

    /**
     * Podle tohodle cyklu bezi hlavni vlakno hry, vzdy se ridi podle hodnoty
     * promenne "fazeHry" Pri lichych hodnotach dochazi k nacteni dat a
     * vytvareni instanci, proto se zde vola metoda "loading"
     */
    @Override
    public void run() {
        while (gameRuns) {
            switch (fazeHry) {
                case 0:
                    System.exit(1);
                    break;
                case 1:
                    loading();
                    if (mm == null) {
                        try {
                            mm = new MainMenu();
                            lasm = new LoadAndSaveMenu(true);
                            mm.addActionListenerToList(al, mm.listOfComp);
                            lasm.addActionListenerToList(al, lasm.listOfComp);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(this, "Eggs are not supposed to be green.", "Inane error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    fazeHry = 2;
                    break;
                case 2:
                    layers.removeAll();
                    layers.add(mm, new Integer(2));
                    addListToLayer(mm.listOfComp, new Integer(2));
                    moveListToFront(mm.listOfComp);
                    while (true) {
                        mm.repaint();
                        if (isShowLoadGameMenu()) {
                            lasm.repaint();
                        }
                        if (!mm.isMainMenuRuns()) {
                            mm = null;
                            break;
                        }
                        try {
                            vlakno.sleep(17);
                        } catch (InterruptedException ex) {
                        }
                    }
                    System.gc();
                    break;
                case 3:
                    loading();
                    if (ngm == null) {
                        try {
                            ngm = new NewGameMenu();
                            ngm.addActionListenerToList(al, ngm.listOfComp);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(this, "Eggs are not supposed to be green.", "Inane error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    fazeHry = 4;
                    break;
                case 4:
                    layers.removeAll();
                    layers.add(ngm, new Integer(2));
                    addListToLayer(ngm.listOfComp, new Integer(3));
                    int i = 0;
                    while (true) {
                        ngm.repaint();
                        if (showLoading) {
                            loading.repaint();
                            i++;
                            if (i == 1) {
                                ngm.nextB.doClick();
                            }
                        }
                        if (!ngm.isNewGameMenuRuns()) {
                            ngm = null;
                            break;
                        }
                        try {
                            vlakno.sleep(17);
                        } catch (InterruptedException ex) {
                        }
                    }
                    System.gc();
                    break;
                case 5:
                    if (og == null) {
                        try {
                            iai = new InfoAndInventory();
                            qp = new QuickPanel();
                            mp = new MessagePanel();
                            em = new EscMenu();
                            og.addActionListenerToList(al, og.listOfComp);
                            iai.addActionListenerToList(al, iai.listOfComp);
                            qp.addActionListenerToList(al, qp.listOfComp);
                            mp.addActionListenerToList(al, mp.listOfComp);
                        } catch (IOException ex) {
                            JOptionPane.showMessageDialog(this, "Eggs are not supposed to be green.", "Inane error",
                                    JOptionPane.ERROR_MESSAGE);
                        }
                    }
                    fazeHry = 6;
                    break;
                case 6:
                    layers.removeAll();
                    layers.add(og, new Integer(2));
                    layers.add(qp, new Integer(4));
                    layers.add(cha, new Integer(3));
                    addListToLayer(og.getActualRom().listOfComp, new Integer(2));
                    addListToLayer(qp.listOfComp, new Integer(4));
                    lasm.setLoadForMainMenu(false);
                    while (true) {
//                        og.repaint();
                        cha.repaint();
                        qp.repaint();
                        if (isShowInfAndInv()) {
                            iai.repaint();
                        }
                        if (isShowEscMenu()) {
                            em.repaint();
                        }
                        if (isShowLoadGameMenu()) {
                            lasm.repaint();
                        }
                        if (!og.run) {
                            cha = null;
                            em = null;
//                            sgm = null;
                            lasm = null;
                            og = null;
                            iai = null;
                            qp = null;
                            mp = null;
                            break;
                        }
                        try {
                            vlakno.sleep(17);
                        } catch (InterruptedException ex) {
                        }
                    }
                    System.gc();
                    break;
//                case 7:
////                    loading();
//                    if (lasm == null) {
//                        lasm = new LoadAndSaveMenu();
//                        lasm.addActionListenerToList(al, lasm.listOfComp);
//                    }
//                    fazeHry = 8;
//                    break;
//                case 8:
//                    layers.removeAll();
//                    layers.add(lasm, new Integer(2));
//                    addListToLayer(lasm.listOfComp, new Integer(2));
//                    moveListToFront(lasm.listOfComp);
//                    while (true) {
//                        lasm.repaint();
//                        if (isShowLoadGameMenu()) {
//                            lasm = null;
//                            break;
//                        }
//                        try {
//                            vlakno.sleep(17);
//                        } catch (InterruptedException ex) {
//                        }
//                    }
//                    System.gc();
//                    break;
            }
        }
    }

    private ImageIcon loadImage(String nazev) {
        ImageIcon ii = new ImageIcon("images/" + nazev);
        return ii;
    }

    public void removeController(JComponent jc) {
        jc.removeComponentListener(null);
    }

    /**
     * Prida controller panelum, ktere jsou vytvoreny na zactku behu programu a
     * take se nastavi
     *
     * @param controller
     */
    public void addController(ActionListener controller) {
        this.al = controller;
        mm.addActionListenerToList(controller, mm.listOfComp);
        lasm.addActionListenerToList(controller, lasm.listOfComp);
        qd.addActionListenerToList(controller, qd.listOfComp);
    }

    /**
     * Metoda, jez, kdyz je volana, ma za ukol nacist data z textovych souboru
     * nachazejicich se v dane slozce.
     *
     * @param dir nese nazev nacitane hry, cemuz pak odpovida nazev slozky, ve
     * ktere se pak budou nachazet vise zminovane soubory a slozky
     */
    public void loadGameView(String dir) {
        //load and set what is in players inventory
        try {
            File file = new File("save/" + dir + "/view/inventory.txt");
            FileInputStream fis = new FileInputStream(file);
            char c0;
            int amountOfItems = 0;
            do {
                c0 = (char) fis.read();
                if (c0 != '#') {
                    amountOfItems = java.lang.Character.getNumericValue(c0);
                }
            } while (c0 != '#');
            for (int i = 0; i < amountOfItems; i++) {
                int numRange = 1000;
                int[] ii = new int[6];
                int what = 0;
                String itemName = "";
                do {
                    c0 = (char) fis.read();
                    if (c0 != '#') {
                        if (c0 >= '0' && c0 <= '9' && what < 6) {
                            ii[what] += numRange * java.lang.Character.getNumericValue(c0);
                            numRange /= 10;
                        }
                        if (c0 >= ' ' && c0 <= 'z' && what > 5) {
                            itemName += c0;
                        }
                        if (c0 == ',') {
                            numRange = 1000;
                            what++;
                        }
                    }
                } while (c0 != '#');
                ItemButton ib = new ItemButton("items/" + itemName + "1.png", "items/" + itemName + "2.png", iai.coorTransWidth(ii[0]), iai.coorTransHeight(ii[1]), iai.coorTransWidth(ii[2]), iai.coorTransHeight(ii[3]), ii[4], false);
                ib.setBounds(iai.coorTransWidth(ii[0]), iai.coorTransHeight(ii[1]), iai.coorTransWidth(ii[2]), iai.coorTransHeight(ii[3]));
                ib.setItemName(itemName);
                ib.setItsInRoom(false);
                iai.addToTab(ib.getType(), ib);
            }
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    /**
     * Metoda, jez, kdyz je volana, ma za ukol vytvorit dane slozky a v nich
     * textove soubory, do kterych se zapisou potrebne informace o stavu hry.
     *
     * @param name nese nazev ukladane hry, cemuz pak odpovida nazev slozky, ve
     * ktere se pak budou nachazet vise zminovane soubory a slozky
     */
    public void saveGameView(String name) {
        try {
            File dir1 = new File("save/" + name + "/view");
            dir1.mkdir();
            dir1 = new File("save/" + name + "/view/contentOfRooms");
            dir1.mkdir();
            //create txt file for players stats and write them on it
            PrintWriter writer = new PrintWriter("save/" + name + "/view/stats.txt", "UTF-8");
            for (int i = 0; i < iai.getValueOfStats().length; i++) {
                writer.println(iai.getValueOfStats(i) + "#");
            }
            for (int i = 0; i < iai.getValueOfStatsLeft().length; i++) {
                writer.println(iai.getValueOfStatsLeft(i) + "#");
            }
            writer.close();
            //create txt file for items at players inventory
            writer = new PrintWriter("save/" + name + "/view/inventory.txt", "UTF-8");
            writer.print(iai.listOfItems.size() + "#");
            for (int j = 0; j < iai.listOfItems.size(); j++) {
                ItemButton ib = (ItemButton) iai.listOfItems.get(j);
                writer.print(bla(iai.coorTransWidthBack(ib.getX())));
                writer.print("," + bla(iai.coorTransHeightBack(ib.getY())));
                writer.print("," + bla(iai.coorTransWidthBack(ib.getWidth())));
                writer.print("," + bla(iai.coorTransHeightBack(ib.getHeight())));
                writer.print("," + bla(ib.getType()));
                writer.print("," + bla(ib.getWhereIsItInRoom()));
                writer.print("," + ib.getItemName() + "#");
            }
            writer.close();

            for (int i = 0; i < og.getAmountOfRooms(); i++) {
                writer = new PrintWriter("save/" + name + "/view/contentOfRooms/" + i + ".txt", "UTF-8");
                int amountOfItems = og.getRoom(i).listOfItems.size();
                String s = null;
                for (int j = 0; j < og.getRoom(i).listOfItems.size(); j++) {
                    if (s == null) {
                        s = bla(og.getRoom(i).coorTransWidthBack(og.getRoom(i).getItemFromRoom(j).getX()));
                    } else {
                        s += bla(og.getRoom(i).coorTransWidthBack(og.getRoom(i).getItemFromRoom(j).getX()));
                    }
                    s += "," + bla(og.getRoom(i).coorTransHeightBack(og.getRoom(i).getItemFromRoom(j).getY()));
                    s += "," + bla(og.getRoom(i).coorTransWidthBack(og.getRoom(i).getItemFromRoom(j).getWidth()));
                    s += "," + bla(og.getRoom(i).coorTransHeightBack(og.getRoom(i).getItemFromRoom(j).getHeight()));
                    s += "," + bla(og.getRoom(i).getItemFromRoom(j).getType());
                    s += "," + bla(og.getRoom(i).getItemFromRoom(j).getWhereIsItInRoom());
                    s += ("," + og.getRoom(i).getItemFromRoom(j).getItemName() + "#");
                }
                for (int j = 0; j < og.getRoom(i).listOfBoxes.size(); j++) {
                    BoxButton bb = (BoxButton) og.getRoom(i).listOfBoxes.get(j);
                    amountOfItems += bb.getListOfItems().size();
                    for (int k = 0; k < bb.getListOfItems().size(); k++) {
                        ItemButton ib = (ItemButton) bb.getItemFromList(k);
                        if (s == null) {
                            s = bla(og.getRoom(i).coorTransWidthBack(ib.getX()));
                        } else {
                            s += bla(og.getRoom(i).coorTransWidthBack(ib.getX()));
                        }
                        s += "," + bla(og.getRoom(i).coorTransHeightBack(ib.getY()));
                        s += "," + bla(og.getRoom(i).coorTransWidthBack(ib.getWidth()));
                        s += "," + bla(og.getRoom(i).coorTransHeightBack(ib.getHeight()));
                        s += "," + bla(ib.getType());
                        s += "," + bla(ib.getWhereIsItInRoom());
                        s += ("," + ib.getItemName() + "#");
                    }
                }
                s = amountOfItems + "#" + s;
                writer.print(s);
                writer.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(this, "Chyba při ukládání hry.", "Zkus to uložit znova, když to pořád nepujde, tak máš smůlu.",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Vezme int a vytvori String retezec jemu odpovidajici. Pote zjisti jak
     * dlouhy ten retezec je, a pokud je to min jak ctyri znaky, tak na jeho
     * zacatek pridava "0" dokud nebude mit ctyri znaky
     *
     * @param oldValue cislo, ktere ma byt zpracovano
     * @return promenou typu String
     */
    public String bla(int oldValue) {
        String s = String.valueOf(oldValue);
        while (s.length() < 4) {
            s = "0" + s;
        }
        return s;
    }

    /**
     * metoda zajistujici double buffering
     */
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

    /**
     * This method is called whenever the observed object is changed. An
     * application calls an <tt>Observable</tt> object's
     * <code>notifyObservers</code> method to have all the object's observers
     * notified of the change.
     *
     * @param o the observable object.
     * @param arg an argument passed to the <code>notifyObservers</code> method.
     */
    @Override
    public void update(Observable o, Object arg) {
        if (o.getClass().equals(Game.class)) {
            if (arg == null) {
                og.getActualRom().removeItemFromRoom(og.getActualRom().getActualItem());
                og.getActualRom().getActualItem().setItsInRoom(false);
                iai.addToTab(og.getActualRom().getActualItem().getType(), og.getActualRom().getActualItem());
                og.getActualRom().getActualItem().setActionCommand("item"/* + og.getActualRom().getActualItem().getType()*/);
            } else if (arg.getClass().equals(String.class)) {
                String msg = (String) arg;
                mp.setMessage(msg);
                addListToLayer(mp.listOfComp, new Integer(5));
            }
        }
        if (o.getClass().equals(Inventory.class)) {
            String s = (String) arg;
            switch (s) {
                case "add":
                    og.getActualRom().getActualItem().setItsInRoom(false);
                    iai.addToTab(og.getActualRom().getActualItem().getType(), og.getActualRom().getActualItem());
                    og.getActualRom().getActualItem().setActionCommand("item"/* + og.getActualRom().getActualItem().getType()*/);
                    break;
                case "removeAndAdd":
                    iai.removeFromTab(iai.getActualItem().getType(), iai.indexOfItem(iai.getActualItem()));
                    iai.getActualItem().setActionCommand("item");
                    iai.getActualItem().setItsInInv(false);

                    iai.getActualItem().setLocation(cha.getActualX(), cha.getActualY() - iai.getActualItem().getHeight());

                    og.getActualRom().addItemToRoom(iai.getActualItem());
                    removeListFromLayer(og.getActualRom().listOfItems);
                    addListToLayer(og.getActualRom().listOfItems, new Integer(3));
                    break;
                case "onlyRemove":
                    iai.removeFromTab(iai.getActualItem().getType(), iai.indexOfItem(iai.getActualItem()));
                    break;
                default:
//                    ItemButton ib = new ItemButton("items/" + s + "1.png", "items/" + s + "2.png", coorTransWidth(ii[0]), coorTransHeight(ii[1]), coorTransWidth(ii[2]), coorTransHeight(ii[3]), ii[4], false);
//                    ib.setBounds(coorTransWidth(ii[0]), coorTransHeight(ii[1]), coorTransWidth(ii[2]), coorTransHeight(ii[3]));

                    break;
            }
        }
        if (o.getClass().equals(Player.class)) {
            String msg = (String) arg;
            mp.setMessage(msg);
            addListToLayer(mp.listOfComp, new Integer(5));
        }
    }
}
