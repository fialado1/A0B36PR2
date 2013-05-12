package the.unexpected.adventure.model;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 *
 * @author Dominik
 */
public class AnotherText {
    private String[] text;
    public AnotherText() {
        File file = new File("dalsiText.txt");

        if (!file.exists()) {
            System.out.println("Soubor neexistuje!");
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
            int pocetPribehu = Character.getNumericValue(c1); //konverze z char na int
            text = new String[pocetPribehu];           //nastaveni velikosti pole pribeh

            /*
             * cyklus, kterym se nacteny text vlozi do pole(jeho velikost odpovida kolik tech textu pro danou mistnost je)
             * osetrene proti nezadoucim znakum a entrum v .txt soouboru
             */
            for (int i = 0; i < text.length; i++) {   //hlavni cyklus probiha tolikart, kolik je prvku pole pribeh
                char pr;
                int pocitadlo = 0;  //promena ktera pocita pocet pridanych znaku
                do {
                    pr = (char) fis.read();             //nacteni znaku
                    //aby se v poli nevyskytovalo "null" - pro prvni znak se pole primo rovna znaku
                    if (text[i] == null && pr != '#') {
                        text[i] = "" + pr;
                        pocitadlo++;
                    } else {
                        if (pr != '#') {
                            //podminka, ktera urcuje, po kolika znacich se ma do pole pridat \n (novy radek)
                            if (pocitadlo % 70 == 0 && pocitadlo != 0 && pr != 13) {
                                if (pr >= '!' && pr <= 'z') {
                                    text[i] += pr;
                                    pocitadlo--;
                                } else {
                                    text[i] += (pr + "\n");
                                }
                            } else if(pr == 13 ){
                                text[i] += "\n";
                                pocitadlo = 0;
                            } else if (pr >= ' ' && pr <= 'z') {
                                text[i] += pr;
                            }
                            pocitadlo++;
                        }
                    }

                } while (pr != '#');
            }
        } catch (IOException e) {
        }
    }
    public String getText(int i){
        return text[i];
    }
}
