package the.unexpected.adventure.model;

/**
 *
 * @author Dominik
 */
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * Trida slouzici k vytvoreni "mapy" pomoci ctyrnasobneho spojoveho seznamu
 * spoje se vytvari pomoci metody setSmer (viz. trida mistnost)
 */
public class CreateAMap {

    private static Room[] mistnost;
    private String[] nazevM;
    private String[] zablok;
    private String[] visited;
    private int[][] index;
    private int pocetMistnosti;
    private int[] florHeight;
    private int[] vanishingPointX;
    private int[] vanishingPointY;

    /**
     *
     * @param dir nese nazev slozky, ze ktere se budou nacitat data.
     */
    public CreateAMap(String dir) {
        File file = new File("map/model/vytvorMapu.txt");
        File file2 = new File(dir + "/model/vytvorMapuBylZablok.txt");

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
            FileInputStream fis2 = new FileInputStream(file2);

            char c1;
            do {
                c1 = (char) fis.read();
            } while (!(c1 >= ' ' && c1 <= 'z'));
            pocetMistnosti = Character.getNumericValue(c1); //konverze z char na int

            nazevM = new String[pocetMistnosti];
            mistnost = new Room[pocetMistnosti];
            zablok = new String[pocetMistnosti];
            visited = new String[pocetMistnosti];
            florHeight = new int[pocetMistnosti];
            vanishingPointX = new int[pocetMistnosti];
            vanishingPointY = new int[pocetMistnosti];
            index = new int[pocetMistnosti][4];

            for (int i = 0; i < nazevM.length; i++) {
                char ch1, ch3, ch4, ch5;
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

                int numRange = 1;
                do {
                    ch4 = (char) fis.read();
                    if (ch4 != '#') {
                        if (ch4 >= '0' && ch4 <= '9') {
                            florHeight[i] += numRange * Character.getNumericValue(ch4);
                            numRange *= 10;
                        }
                    }
                } while (ch4 != '#');

                numRange = 1;
                boolean writeToX = true;
                do {
                    ch5 = (char) fis.read();
                    if (ch5 != '#') {
                        if (ch5 >= '0' && ch5 <= '9') {
                            if (writeToX) {
                                vanishingPointX[i] += numRange * Character.getNumericValue(ch5);
                                numRange *= 10;
                            } else {
                                vanishingPointY[i] += numRange * Character.getNumericValue(ch5);
                                numRange *= 10;
                            }
                        }
                        if (ch5 == ',') {
                            writeToX = false;
                            numRange = 1;
                        }
                    }
                } while (ch5 != '#');

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
            }
            for (int i = 0; i < pocetMistnosti; i++) {
                char ch2;
                do {
                    ch2 = (char) fis2.read();
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

                do {
                    ch2 = (char) fis2.read();
                    if (ch2 == 13) {
                        visited[i] = "";
                    }
                    if (visited[i] == null && ch2 != '#') {
                        visited[i] = "" + ch2;
                    } else {
                        if (ch2 != '#') {
                            if (ch2 >= '!' && ch2 <= 'z') {
                                visited[i] += ch2;
                            }
                        }
                    }
                } while (ch2 != '#');
                //nastaveni docasne nepristupnych mistnost
                if (zablok[i].equalsIgnoreCase("true") || zablok[i].equalsIgnoreCase("false")) {
                    mistnost[i].setZablokovano(Boolean.valueOf(zablok[i]));
                } else {
                    // throw some exception
                }
                if (visited[i].equalsIgnoreCase("true") || visited[i].equalsIgnoreCase("false")) {
                    mistnost[i].setByl(Boolean.valueOf(visited[i]));
                } else {
                    // throw some exception
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

    public Room getMistnost(int i) {
        return mistnost[i];
    }

    public static Room getZacatek(int roomNum) {
        return mistnost[roomNum];
    }

    public int getPocetMistnosti() {
        return pocetMistnosti;
    }

    public String getNazevM(int i) {
        return nazevM[i];
    }

    public int getFlorHeight(int i) {
        return florHeight[i];
    }

    public int getVanishingPointX(int i) {
        return vanishingPointX[i];
    }

    public int getVanishingPointY(int i) {
        return vanishingPointY[i];
    }
}
