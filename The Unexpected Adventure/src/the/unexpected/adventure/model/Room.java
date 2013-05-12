package the.unexpected.adventure.model;

/**
 *
 * @author Dominik
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.*;
import javax.swing.JOptionPane;

public class Room {

    private String nazevM;
    private int roomNumber;
    int amountOfItems;
    ArrayList<Item> obsah;           //pole predmetu vyskytujicich se v dane mistnosti
    private boolean byl = false;
    private boolean zablokovano = false;
    private Room[] svetStrana;
    /*
     * pribeh[0] - text, ktery se obejvi, kdyz do mistnosti prijde hrac poprve
     * pribeh[1] - vytiskne se po zadani prikazu prohledat v mistnostech kde je hrac poprve
     * pribeh [2] az pribeh[x] - text(y), ktery se vytiskne po nejakem specifickem hracove kroku
     */
    private String[] pribeh;

    Room(String nazevM) {
        this.svetStrana = new Room[4];
        for (int i = 0; i < 4; i++) {
            svetStrana[i] = null;
        }
        this.nazevM = nazevM;

        File file = new File("map/model/" + nazevM + ".txt");

        if (!file.exists()) {
            System.out.println("Soubor " + nazevM + " neexistuje!");
            JOptionPane.showMessageDialog(null, "Chyba při načítání souborů.", "Soubor " + nazevM + " neexistuje!", JOptionPane.ERROR_MESSAGE);
            return;
        }
        if (!(file.isFile()
                && file.canRead())) {
            JOptionPane.showMessageDialog(null, "Chyba při načítání souborů.", "Nelze nacitat data z " + file.getName(), JOptionPane.ERROR_MESSAGE);
            return;
        }
        try {
            FileInputStream fis = new FileInputStream(file);
            char c0, c1;
            do {
                c0 = (char) fis.read();
                if (c0 != '#') {
                    roomNumber = Character.getNumericValue(c0);
                }
            } while (c0 != '#');

            do {
                c1 = (char) fis.read();
            } while (!(c1 >= ' ' && c1 <= 'z'));
            int pocetPribehu = Character.getNumericValue(c1); //konverze z char na int
            pribeh = new String[pocetPribehu];           //nastaveni velikosti pole pribeh
                /*
             * cyklus, kterym se nacteny text vlozi do pole(jeho velikost odpovida kolik tech textu pro danou mistnost je)
             * osetrene proti nezadoucim znakum a entrum v .txt soouboru
             */
            for (int i = 0; i < pribeh.length; i++) {   //hlavni cyklus probiha tolikart, kolik je prvku pole pribeh
                char pr;
                int pocitadlo = 0;  //promena ktera pocita pocet pridanych znaku
                do {
                    pr = (char) fis.read();             //nacteni znaku
                    //aby se v poli nevyskytovalo "null" - pro prvni znak se pole primo rovna znaku
                    if (pribeh[i] == null && pr != '#') {
                        pribeh[i] = "" + pr;
                        pocitadlo++;
                    } else {
                        if (pr != '#') {
                            //podminka, ktera urcuje, po kolika znacich se ma do pole pridat \n (novy radek)
                            if (pocitadlo % 115 == 0 && pocitadlo != 0) {
                                if (pr >= '!' && pr <= 'z') {
                                    pribeh[i] += pr;
                                    pocitadlo--;
                                } else {
                                    pribeh[i] += (pr + "\n");
                                }
                            } else if (pr >= ' ' && pr <= 'z') {
                                pribeh[i] += pr;
                            }
                            pocitadlo++;
                        }
                    }

                } while (pr != '#');
            }
            File file2 = new File("map/model/contentOfRooms/" + roomNumber + ".txt");
            FileInputStream fis2 = new FileInputStream(file2);

            char c2;
            do {
                c2 = (char) fis2.read();
                if (c2 != '#') {
                    amountOfItems = Character.getNumericValue(c2);
                }
            } while (c2 != '#');

            obsah = new ArrayList<>();
            for (int i = 0; i < amountOfItems; i++) {
                char ob;
                String s = "";

                do {
                    ob = (char) fis2.read();
                    if (ob != '#') {
                        if (ob >= ' ' && ob <= 'z') {
                            s += ob;
                        }
                    }
                } while (fis2.available() > 0 && ob != '#');
                Item p = new Item(s);
                obsah.add(p);
            }
        } catch (IOException e) {
            JOptionPane.showMessageDialog(null, "Chyba při načítání souborů.", "Tády dády dá!", JOptionPane.ERROR_MESSAGE);
        }
    }

    public String getPribeh(int i) {
        return pribeh[i];
    }

    public Room moveTo(int i) {
        if (svetStrana[i] != null) {
            return svetStrana[i];
        } else {
            return null;
        }
    }

    // metoda, ktera odebere dany predmet z mistnosti a vrati jeho nazev, takze se bude vedet co za predmet se ma pridat
    public void vezmiPredmet(Item nazevP) {
        obsah.remove(nazevP);
//        obsah.trimToSize();
       for (int i = 0; i < obsah.size(); i++) {
            System.out.println(obsah.get(i).getNazevP());
        }
    }

    public void vezmiPredmet(int index) {
        obsah.remove(index);
    }

    //metoda co prida predmet do mistnosti (napr. kdyz hrac bude chtit neco vyhodiit z inventare)
    public void pridejPredmet(Item nazevP) {
        obsah.add(nazevP);
    }

    public void setSmer(int indexM, Room mistnost) {
        svetStrana[indexM] = mistnost;
    }

    public int getSmer(int i) {
        int b = svetStrana[i].roomNumber;
        return b;
    }

    public String getNazevM() {
        return nazevM;
    }

    public int getRoomNumber() {
        return roomNumber;
    }

    public boolean isByl() {
        return byl;
    }

    public void setByl(boolean byl) {
        this.byl = byl;
    }

    public boolean isZablokovano() {
        return zablokovano;
    }

    public void setZablokovano(boolean zablokovano) {
        this.zablokovano = zablokovano;
    }

    @Override
    public String toString() {
        return "" + nazevM + "";
    }

    //metoda slouzici k vypisu seznamu predmetu, ktere hrac v mistnosti nasel a muze je sebrat
    public String obsahM() {
        Collections.sort(obsah, new Room.ComTypP());
        String s = "Veci co maji potencial k uzitecnosti:\n";
        for (int i = 1; i <= 4; i++) {
            switch (i) {
                case 1:
                    s += "<equipment>\n";
                    break;
                case 2:
                    s += "<accessory>\n";
                    break;
                case 3:
                    s += "<useable>\n";
                    break;
                case 4:
                    s += "<combinable>\n";
                    break;
            }
            for (int j = 0; j < obsah.size(); j++) {
                if (obsah.get(j).getTypP() == i) {
                    s += " " + (j + 1) + " - " + obsah.get(j) + "\n";
                }
            }
        }
        return s;
    }

    public static class ComTypP implements Comparator<Item> {

        @Override
        public int compare(Item p1, Item p2) {
            return p1.getTypP() - p2.getTypP();
        }
    }
}
