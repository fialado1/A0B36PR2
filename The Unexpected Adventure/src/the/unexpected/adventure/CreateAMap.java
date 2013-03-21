/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure;

/**
 *
 * @author Dominik
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
/**
 * Trida slouzici k vytvoreni "mapy" pomoci ctyrnasobneho spojoveho seznamu spoje
 * se vytvari pomoci metody setSmer (viz. trida mistnost)
 */
public class CreateAMap {

    private static Room[] mistnost;
    private static String[] nazevM;
    private static String[] zablok;
    private static int[][] index;

    public CreateAMap() {
        
        File file = new File("map/vytvorMapu.txt");

        if (!file.exists()) {
            System.out.println("Soubor vytvorMapu neexistuje!");
            return;
        }

        if (!(file.isFile() && file.canRead())) {
            System.out.println("Nelze nacitat data z " + file.getName());
            return;
        }

        try {
            FileInputStream fis = new FileInputStream(file);

            char c1;
            do {
                c1 = (char) fis.read();
            } while (!(c1 >= ' ' && c1 <= 'z'));
            int pocetMistnosti = Character.getNumericValue(c1); //konverze z char na int

            nazevM = new String[pocetMistnosti];
            mistnost = new Room[pocetMistnosti];
            zablok = new String[pocetMistnosti];
            index = new int[pocetMistnosti][4];

            for (int i = 0; i < nazevM.length; i++) {
                char ch1, ch2, ch3;
                do {
                    ch1 = (char) fis.read();             //nacteni znaku
                    if (ch1 == 13) {
                        nazevM[i] = "";
                    }
                    //aby se v poli nevyskytovalo "null" - pro prvni znak se pole primo rovna znaku
                    if (nazevM[i] == null && ch1 != '#') {
                        nazevM[i] = "" + ch1;
                    } else {
                        if (ch1 != '#') {
                            if (ch1 >= ' ' && ch1 <= 'z') {
                                nazevM[i] += ch1;
                            }
                        }
                    }
                } while (ch1 != '#');

                do {
                    ch2 = (char) fis.read();
                    if (ch2 == 13) {
                        zablok[i] = "";
                    }
                    if (zablok[i] == null && ch2 != '#') {
                        zablok[i] = "" + ch2;
                    } else {
                        if (ch2 != '#') {
                            if (ch2 >= '!' && ch2 <= 'z') {
                                zablok[i] += ch2;
                            }
                        }
                    }
                } while (ch2 != '#');

                for (int j = 0; j < 4; j++) {
                    do {
                        ch3 = (char) fis.read();
                        if (ch3 != '#') {
                            if (ch3 >= '!' && ch3 <= 'z') {
                                if (ch3 < '0' || ch3 > '9') {
                                    index[i][j] = -1;
                                }
                                index[i][j] = Character.getNumericValue(ch3);
                            }
                        }
                    } while (ch3 != '#');

                }
                mistnost[i] = new Room(nazevM[i]);
                //nastaveni docasne nepristupnych mistnost
                if ("true".equals(zablok[i])) {
                    mistnost[i].setZablokovano(true);
                } else {
                    mistnost[i].setZablokovano(false);
                }


            }
        } catch (IOException e) {
        }
        for (int i = 0; i < (mistnost.length); i++) {
            for (int j = 0; j < 4; j++) {
                if (index[i][j] < 0 || index[i][j] > 9) {
                    mistnost[i].setSmer(j, null);
                } else {
                    mistnost[i].setSmer(j, mistnost[index[i][j]]);
                }
            }
        }
    }

    public static Room getZacatek() {
        return mistnost[0];
    }
//    public static Room create() {
//        Room m1 = new Room("Toaleta");
//        Room m2 = new Room("Koupelna");
//        Room m3 = new Room("Chodba");
//        Room m4 = new Room("Kuchyn");
//        Room m5 = new Room("Jidelna");
//        Room m6 = new Room("Obyvak");
//        Room m7 = new Room("Schodiste");
//        Room m8 = new Room("ven z baraku");
//
//        //nastaveni docasne nepristupnych mistnost
//        m3.setZablokovano(true);
//        m7.setZablokovano(true);
//        m8.setZablokovano(true);
//
//
//        //0-S, 1-J, 2-V, 3-Z
//        m1.setSmer(0, m3);
//        m1.setSmer(2, m2);
//        m2.setSmer(3, m1);
//        m3.setSmer(0, m8);
//        m3.setSmer(1, m1);
//        m3.setSmer(2, m4);
//        m4.setSmer(0, m5);
//        m4.setSmer(2, m6);
//        m4.setSmer(3, m3);
//        m5.setSmer(1, m4);
//        m6.setSmer(1, m7);
//        m6.setSmer(3, m4);
//        m7.setSmer(0, m5);
//
//        return m1;
//    }
}
