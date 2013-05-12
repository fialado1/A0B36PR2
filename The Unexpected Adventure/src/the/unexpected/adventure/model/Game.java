package the.unexpected.adventure.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Observable;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.JOptionPane;

/**
 * Hlavni trida modelu
 *
 * @author Dominik
 */
public class Game extends Observable {

    public Player player = new Player();
    public CreateAMap cam;
    private Room aktualniM;
    int podm = 0;
    int ukol = 1;
    private String message;

    public Room getAktualniM() {
        return aktualniM;
    }

    public void setAktualniM(Room aktualniM) {
        this.aktualniM = aktualniM;
    }

    public String getMessage() {
        return message;
    }

    /**
     * metoda urcujici konec prvni kapitoly
     */
    public boolean konecK1() {
        if ("Schodiste".equals(player.getAktualniM())) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * metoda urcujici konec cele hry
     */
    public boolean konecHry() {
        if (konecK1() == true) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * metoda vracejici prislusnou hodnotu promene podm p5i splneni danneho
     * ukolu
     */
    public int ukoly() {
        try {
            switch (ukol) {
                case 1:
                    //ukol 1 - dojit s UV lampou na zachod a pouzit ji
                    Item uv = new Item("UV lampa");
                    if (player.getPouzityPr().equals(uv.getNazevP()) && "toaleta".equals(player.getAktualniM())) {
                        podm = 1;
                    }
                    break;
                case 2:
                    //ukol 2 - zabranit laseru ve snimani prostoru prede dvermi pomoci zrcatka
                    Item zr = new Item("zrcatko");
                    if (player.getPouzityPr().equals(zr.getNazevP()) && "toaleta".equals(player.getAktualniM()) && ukol == 2) {
                        podm = 2;
                    }
                    break;
                case 3:
                    //ukol 3 - poskladat prehravac a pprehrat si vzkaz od tajny osoby
                    Item mp = new Item("funkcni mpTrojka");
                    if (player.getPouzityPr().equals(mp.getNazevP()) && ukol == 3) {
                        podm = 3;
                    }
                    break;
                case 4:
                    //posledni ukol - kdyz player odblokuje dvere ke schodisti (sperhak z hulek a pilniku)
                    Item sp = new Item("sperhak");
                    if (player.getPouzityPr().equals(sp.getNazevP()) && "obyvak".equals(player.getAktualniM()) && ukol == 4) {
                        podm = 4;
                    }
                    break;
            }

        } catch (FileNotFoundException ex) {
            JOptionPane.showMessageDialog(null, "Chyba při načítání souborů", "Soubor nenalezen!",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.", "Soubor nenalezen!",
                    JOptionPane.ERROR_MESSAGE);
            System.exit(0);
        }
        return podm;
    }

    /**
     * Metoda, ktera je volana, kdyz se ma vytvorit nova hra
     */
    public void startNewGame() {
        cam = new CreateAMap("map");
        aktualniM = CreateAMap.getZacatek(0);
        aktualniM.setByl(true);
        ukol = 1;
    }

    /**
     * Metoda, ktera je volana, kdyz se ma nacist jiz ulozena hra
     *
     * @param dir nese "stringovou" podobu nazvu ulozene hry, ktera se ma nacist
     */
    public void startSavedGame(String dir) {
        cam = new CreateAMap("save/" + dir);
        try {
            File file = new File("save/" + dir + "/model/character.txt");
            FileInputStream fis = new FileInputStream(file);
            char c0;
            //load and set players stats
            for (int i = 0; i < player.getValueOfStats().length; i++) {
                int numRange = 1;
                do {
                    c0 = (char) fis.read();
                    if (c0 >= '0' && c0 <= '9') {
                        if (numRange > 1) {
                            player.setValueOfStats(i, player.getValueOfStats(i) * numRange + Character.getNumericValue(c0));
                        } else {
                            player.setValueOfStats(i, Character.getNumericValue(c0));
                        }
                        numRange *= 10;
                        System.out.println("stat " + i + ": " + player.getValueOfStats(i));
                    }
                } while (c0 != '#');
            }
            //load and set players name
            String s = null;
            do {
                c0 = (char) fis.read();
                if (c0 == 13) {
                    s = "";
                }
                if (s == null && c0 != '#') {
                    s = "" + c0;
                } else {
                    if (c0 != '#') {
                        if (c0 >= '!' && c0 <= 'z') {
                            s += c0;
                        }
                    }
                }
            } while (c0 != '#');
            player.setJmeno(s);
            //load and set value of "ukol" and actual room
            int numRange = 1;
            int i = 0;
            boolean ukolIsSet = false;
            while (true) {
                c0 = (char) fis.read();
                if (c0 >= '0' && c0 <= '9') {
                    if (numRange > 1) {
                        i = i * numRange + Character.getNumericValue(c0);
                    } else {
                        i = Character.getNumericValue(c0);
                    }
                    numRange *= 10;
                }
                if (c0 == '#' && !ukolIsSet) {
                    ukol = i;
                    ukolIsSet = true;
                }
                if (c0 == '#' && ukolIsSet) {
                    aktualniM = CreateAMap.getZacatek(i);
                    break;
                }
            }
        } catch (IOException ex) {
            Logger.getLogger(Game.class.getName()).log(Level.SEVERE, null, ex);
        }
        player.setStats();
    }

    /**
     * metoda, ktera je volana, kdyz se chce player premistit
     *
     * @param i je roven smeru, kterym se chce hrac presunout
     */
    public boolean presun(int i) {
        //v pripade, ze se chce premistit do jeste zablokovane mistnosti, tak ho to tam nepusti
        if (getAktualniM().moveTo(i).isZablokovano() == true) {
            message = "Bereš za kliku s tím, že se teda koukneš co je za těmito dveřmi, "
                    + "ale bohužel to nejde. Asi jsou zamčeny, či nějak jinak zablokovaný...";
            return false;
        } else {
            player.setAktualniM(getAktualniM().moveTo(i).getNazevM());
            setAktualniM(getAktualniM().moveTo(i));
            //pokud se v dane mistnosti player objevil poprve vytiskne se mu popis mistnosti a nastavi se promena byl na true
            if (getAktualniM().isByl() == false) {
                message = getAktualniM().getPribeh(0);
                getAktualniM().setByl(true);
            } else {
                message = "Nacházíš se v místnosti " + getAktualniM().getNazevM();
            }
            return true;
        }
    }

    /**
     * Metoda, ktera prida predmet do mistnosti z inventare
     *
     * @param index odpovida poradovemu cislu dotycneho predmetu v arraylistu
     * predmetu v inventari
     */
    public void addToRoom(int index) {
        Item p = player.inventar.nactiPredmet(index);
        player.inventar.odeberPredmet(index);
        getAktualniM().pridejPredmet(p);
    }

    /**
     * Metoda, ktera prida predmet z mistnosti do inventare
     *
     * @param index odpovida poradovemu cislu dotycneho predmetu v arraylistu
     * predmetu v mistnosti
     */
    public void addToInventory(int index) {
        Item p = getAktualniM().obsah.get(index);
//        if (!player.inventar.obsazenost()) {
//            getAktualniM().vezmiPredmet(index);
//            player.inventar.pridejPredmet(p, type);
        if (!player.inventar.obsazenost()) {
            player.inventar.pridejPredmet(p, false);
            getAktualniM().vezmiPredmet(p);
            message = p.popis[0];
            setChanged();
            notifyObservers();
        } else {
            message = "Do tvých kapes se ti toho už víc nevejde!!!";
            setChanged();
            notifyObservers(message);
        }
    }

    public void useItem(int index) {
        Item p = player.inventar.nactiPredmet(index);
        player.pouzijPredmet(p);
        check();
    }

    public void combineItems(int index1, int index2) {
        Item p1 = player.inventar.nactiPredmet(index1);
        Item p2 = player.inventar.nactiPredmet(index2);
        try {
            player.zkombinujPredmet(p1, p2);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Chyba při načítání souborů", "Soubor nenalezen!",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metoda, ktera slouzi ke zkontrolovani, zda-li nedoslo ke splneni nejakeho
     * z ukolu
     */
    public void check() {
        podm = 0;
        switch (ukoly()) {
            case 1:
                message = getAktualniM().getPribeh(2);
                player.setZkusenosti(20);
                ukol++;
                break;
            case 2:
                message = getAktualniM().getPribeh(3);
                getAktualniM().moveTo(0).setZablokovano(false);
                player.setZkusenosti(20);
                ukol++;
                break;
            case 3:
                player.setZkusenosti(30);
                ukol++;
                break;
            case 4:
                message = getAktualniM().getPribeh(2);
                getAktualniM().moveTo(1).setZablokovano(false);
                if (player.setZkusenosti(35)) {
                    message += "/n /n Dosahl jsi noveh urovne! Muzes si ted priradit az 3 body k atributum.";
//                    nastavAtributy(3);
                }
                ukol++;
                break;
            default:
                break;
        }
        if (podm != 0) {
            setChanged();
            notifyObservers(message);
        }
    }

    /**
     * Metoda, jez, kdyz je volana, ma za ukol nacist data z textovych souboru
     * nachazejicich se v dane slozce.
     *
     * @param dir nese nazev nacitane hry, cemuz pak odpovida nazev slozky, ve
     * ktere se pak budou nachazet vise zminovane soubory a slozky
     */
    public void loadGameModel(String dir) {
        startSavedGame(dir);
        //load and set what is in players inventory
        try {
            File file = new File("save/" + dir + "/model/inventory.txt");
            FileInputStream fis = new FileInputStream(file);
            char c0;
            int amountOfItems = 0;
            do {
                c0 = (char) fis.read();
                if (c0 != '#') {
                    amountOfItems = Character.getNumericValue(c0);
                }
            } while (c0 != '#');
            for (int i = 0; i < amountOfItems; i++) {
                String s = null;
                do {
                    c0 = (char) fis.read();
                    if (c0 == 13) {
                        s = "";
                    }
                    if (s == null && c0 != '#') {
                        s = "" + c0;
                    } else {
                        if (c0 != '#') {
                            if (c0 >= '!' && c0 <= 'z') {
                                s += c0;
                            }
                        }
                    }
                } while (c0 != '#');
                player.inventar.pridejPredmet(new Item(s), false);
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
    public void saveGameModel(String name) {
        try {
            File dir1 = new File("save/" + name);
            dir1.mkdir();
            dir1 = new File("save/" + name + "/model");
            dir1.mkdir();
            dir1 = new File("save/" + name + "/model/contentOfRooms");
            dir1.mkdir();
            //save which rooms are blocked and visited
            PrintWriter writer = new PrintWriter("save/" + name + "/model/vytvorMapuBylZablok.txt", "UTF-8");
            for (int i = 0; i < cam.getPocetMistnosti(); i++) {
                writer.println(cam.getMistnost(i).isZablokovano() + "#" + cam.getMistnost(i).isByl() + "#");
            }
            writer.close();
            //save characters stats
            writer = new PrintWriter("save/" + name + "/model/character.txt", "UTF-8");
            player.setValueOfStats();
            for (int i = 0; i < player.getValueOfStats().length; i++) {
                writer.println(player.getValueOfStats(i) + "#");
            }
            writer.println(player.getJmeno() + "#");
            writer.println(ukol + "#");
            writer.println(getAktualniM().getRoomNumber() + "#");
            writer.close();
            //save what is in inventory
            writer = new PrintWriter("save/" + name + "/model/inventory.txt", "UTF-8");
            String s = null;
            int amountOfItems = 0;
            for (int i = 0; i < player.inventar.inventory.size(); i++) {
                amountOfItems++;
                if (s == null) {
                    s = player.inventar.nactiPredmet(i).getNazevP() + "#";
                } else {
                    s += player.inventar.nactiPredmet(i).getNazevP() + "#";
                }
            }
            s = amountOfItems + "#" + s;
            writer.println(s);
            writer.close();
            //save what is in rooms
            for (int i = 0; i < cam.getPocetMistnosti(); i++) {
                writer = new PrintWriter("save/" + name + "/model/contentOfRooms/" + i + ".txt", "UTF-8");
                writer.println(cam.getMistnost(i).obsah.size() + "#");
                for (int j = 0; j < cam.getMistnost(i).obsah.size(); j++) {
                    writer.println(cam.getMistnost(i).obsah.get(j) + "#");
                }
                writer.close();
            }
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Chyba při ukládání hry.", "Zkus to uložit znova, když to pořád nepujde, tak máš smůlu.",
                    JOptionPane.ERROR_MESSAGE);
        }
    }

    public void setStats(int str, int con, int intel, int cha, String name) {
        player.setSila(str);
        player.setOdolnost(con);
        player.setInteligence(intel);
        player.setCharisma(cha);
        player.setMaxZivot();
//         player.setZivoty( player.getMaxZivot());
        player.setObrana(0);
        player.setJmeno(name);
    }
}
