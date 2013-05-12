/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure.model;

/**
 *
 * @author Dominik
 */
import java.io.*;

public class Item {

    private String nazevP;
    private int minS;
    private int minI;
    private int plusS;
    private int plusO;
    private int plusI;
    private int plusCh;
    private int plusObr;
    private int plusUt;
    private int plusZ;
    //1-k noseni, 2-k pouzivani, 3-k uziti, 4-ke kombin.
    private int typP;
    private String special = "";
    //popis[0]-popis pri sebrani/vytvoreni, popis[1]-pop. po pouziti/nasazazeni..., popis[2]-kdyz nelze pouzit apod
    public String[] popis = new String[3];
//    Player hrac = new Player();

    /**
     * konstruktor
     */
    Item(String nazevP) throws FileNotFoundException, IOException {
        this.nazevP = nazevP;

        File file = new File("predmety/" + nazevP + ".txt");

        if (!file.exists()) {
            System.out.println("Soubor " + nazevP + " neexistuje!");
            return;
        }

        if (!(file.isFile()
                && file.canRead())) {
            System.out.println("Nelze nacitat data z " + file.getName());
            return;
        }


        FileInputStream fis = new FileInputStream(file);

        String[] hodnota = new String[11];
        char[] c = new char[11];
        for (int i = 0; i < hodnota.length; i++) {
            do {
                c[i] = (char) fis.read();
                if (hodnota[i] == null && c[i] != '#') {
                    hodnota[i] = "" + c[i];
                } else {
                    if (c[i] != '#') {
                        if (c[i] >= ' ' && c[i] <= 'z') {
                            hodnota[i] += c[i];
                        }
                    }
                }
            } while (c[i] != '#');
        }
        typP = Integer.valueOf(hodnota[0]);
        minS = Integer.valueOf(hodnota[1]);
        minI = Integer.valueOf(hodnota[2]);
        plusS = Integer.valueOf(hodnota[3]);
        plusI = Integer.valueOf(hodnota[4]);
        plusO = Integer.parseInt(hodnota[5]);
        plusCh = Integer.valueOf(hodnota[6]);
        plusObr = Integer.valueOf(hodnota[7]);
        plusUt = Integer.valueOf(hodnota[8]);
        plusZ = Integer.valueOf(hodnota[9]);
        special = hodnota[10];


        /*
         * cyklus pres ktery se z textaku nactou tri popisy, ktere tam jsou rozliseny znakem #
         * text se rozdeluje do radku po 170 znacich(pocitadlo) vzdy s ohledem na uchovani
         * celistvosti slov,
         */
        for (int i = 0; i < popis.length; i++) {
            char pop;
            int pocitadlo = 0;  //promena ktera pocita pocet pridanych znaku
            do {
                pop = (char) fis.read();             //nacteni znaku
                //aby se v poli nevyskytovalo "null" - pro prvni znak se pole primo rovna znaku
                if (popis[i] == null && pop != '#') {
                    popis[i] = "" + pop;
                    pocitadlo++;
                } else {
                    if (pop != '#') {
                        //podminka, pro pridani radku
                        if (pocitadlo % 115 == 0 && pocitadlo != 0) {
                            //osetreni, aby se tak stalo mezi slovami
                            if (pop >= '!' && pop <= 'z') {
                                popis[i] += pop;
                                pocitadlo--;
                            } else {
                                popis[i] += (pop + "\n");
                            }
                        } else if (pop >= ' ' && pop <= 'z') {
                            popis[i] += pop;
                        }
                        pocitadlo++;
                    }
                }

            } while (pop != '#');
        }
    }

    //nasleduji gettery pro vsechny promenne, ktere se nadefinovaly v konstruktoru
    public String getNazevP() {
        return nazevP;
    }

    public int getMinS() {
        return minS;
    }

    public int getMinI() {
        return minI;
    }

    public int getPlusS() {
        return plusS;
    }

    public int getPlusO() {
        return plusO;
    }

    public int getPlusI() {
        return plusI;
    }

    public int getPlusCh() {
        return plusCh;
    }

    public int getPlusObr() {
        return plusObr;
    }

    public int getPlusUt() {
        return plusUt;
    }

    public int getPlusZ() {
        return plusZ;
    }

    public int getTypP() {
        return typP;
    }

    public String getSpecial() {
        return special;
    }

    @Override
    public String toString() {
        return nazevP;
    }
}
