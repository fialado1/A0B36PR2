/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure;

import java.util.InputMismatchException;
import java.util.Scanner;

/**
 *
 * @author Dominik
 */
public class Game {
    Scanner scan = new Scanner(System.in);
    CreateAMap cam = new CreateAMap();
    private Room aktualniM = CreateAMap.getZacatek();
    Player hrac = new Player();
    int podm = 0;
    int ukol = 1;


    public Room getAktualniM() {
        return aktualniM;
    }

    public void setAktualniM(Room aktualniM) {
        this.aktualniM = aktualniM;
    }

    /*
     * v uvodu se vyvola metida atributy, ktera umozni hraci nastavit patricne vlastnosti sve postavy
     * nastavi se velikost hracova inventare
     */
    public void uvod() throws InterruptedException {
        hrac.setAktualniM("Toaleta");
        System.out.println("-----Vitej ve hre!-----");
        System.out.println(hrac.dT.getText(0));
        jmeno();
        nastavAtributy(20);
        System.out.println("\n" + hrac.dT.getText(1));
    }

    //metoda urcujici konec prvni kapitoly
    public boolean konecK1() {
        if ("Schodiste".equals(hrac.getAktualniM())) {
            return true;
        } else {
            return false;
        }
    }
//metoda urcujici konec cele hry

    public boolean konecHry() {
        if (konecK1() == true) {
            return true;
        } else {
            return false;
        }
    }
//metoda vracejici prislusnou hodnotu promene podm p5i splneni danneho ukolu

    public int ukoly() {
        switch (ukol) {
            case 1:
                //ukol 1 - dojit s UV lampou na zachod a pouzit ji
                Item uv = new Item("UV lampa");
                if (hrac.getPouzityPr().equals(uv.getNazevP()) && "Toaleta".equals(hrac.getAktualniM())) {
                    podm = 1;
                }
                break;
            case 2:
                //ukol 2 - zabranit laseru ve snimani prostoru prede dvermi pomoci zrcatka
                Item zr = new Item("zrcatko");
                if (hrac.getPouzityPr().equals(zr.getNazevP()) && "Toaleta".equals(hrac.getAktualniM()) && ukol == 2) {
                    podm = 2;
                }
                break;
            case 3:
                //ukol 3 - poskladat prehravac a pprehrat si vzkaz od tajny osoby
                Item mp = new Item("funkcni mpTrojka");
                if (hrac.getPouzityPr().equals(mp.getNazevP()) && ukol == 3) {
                    podm = 3;
                }
                break;
            case 4:
                //posledni ukol - kdyz hrac odblokuje dvere ke schodisti (sperhak z hulek a pilniku)
                Item sp = new Item("sperhak");
                if (hrac.getPouzityPr().equals(sp.getNazevP()) && "Obyvak".equals(hrac.getAktualniM()) && ukol == 4) {
                    podm = 4;
                }
                break;
        }
        return podm;
    }

    public void kapitolaPrvni() {
        System.out.println(getAktualniM().getPribeh(0));
        getAktualniM().setByl(true);
        while (!konecHry()) {
            OUTER_1:
            for (;;) {

                if (konecK1()) {
                    System.out.println(hrac.dT.getText(3));
                    System.out.println("Pro ukonceni hry stiskni libovolnou klavesu.");
                    String volba = scan.nextLine();
                    break OUTER_1;
                }
                OUTER_2:
                while (!konecK1()) {
                    //kdyz se ukonci metoda hlvniCyklus(), znamena to, ze se zmenila hodnota metody ukoly(), 
                    //nebo ze nastal konec 1. kapitoly
                    //tak ci tak, podle te hodnoty se pak stane urcita vec (switch)
                    hlavniCyklus();

                    podm = 0;
                    switch (ukoly()) {
                        case 1:
                            System.out.println(getAktualniM().getPribeh(2));
                            hrac.setZkusenosti(20);
                            ukol++;
                            break OUTER_2;
                        case 2:
                            System.out.println(getAktualniM().getPribeh(3));
                            getAktualniM().moveTo(0).setZablokovano(false);
                            hrac.setZkusenosti(20);
                            ukol++;
                            break OUTER_2;
                        case 3:
                            hrac.setZkusenosti(30);
                            ukol++;
                            break OUTER_2;
                        case 4:
                            System.out.println(getAktualniM().getPribeh(2));
                            getAktualniM().moveTo(1).setZablokovano(false);
                            if (hrac.setZkusenosti(35)) {
                                System.out.println("Dosahl jsi noveh urovne! Muzes si ted priradit az 3 body k atributum.");
                                nastavAtributy(3);
                            }
                            ukol++;
                            break OUTER_2;
                    }
                    break OUTER_2;
                }
            }
            break;
        }
    }

    public void hlavniCyklus() {
        do {
            System.out.println("\nMuzes:\n[1] premistit se\n[2] zchecknout co mas u sebe\n"
                    + "[3] prohledat mistnost\n[4] kouknout se na staty");
            int vybranaAkce = 0;
            boolean valid = false;
            do {
                try {
                    vybranaAkce = readNumber();
                    valid = true;
                    if (vybranaAkce > 4 || vybranaAkce < 0) {
                        System.out.println("Hele musis zadat nejakou platnou hodnotu, tedy 1 az 4");
                        valid = false;
                    }
                } catch (InvalidInputException | IndexOutOfBoundsException ex) {
                    System.out.println("Hele musis zadat nejakou platnou hodnotu, tedy 1 az 4!");
                }
            } while (!valid);
            //podle hodnoty vybranaAkce se zavola prislusna metoda
            switch (vybranaAkce) {
                case 1:
                    presun();
                    break;
                case 2:
                    praceSInv();
                    break;
                case 3:
                    prohledani();
                    break;
                case 4:
                    hrac.vypisAtributy();
                    break;
            }

        } while (ukoly() == 0 && konecK1());
    }

//metoda, ktera je volana, kdyz se chce hrac premistit
    public void presun() {
        System.out.println("Muzes se presunout na:");
        for (int i = 0; i < 4; i++) {
            if (getAktualniM().moveTo(i) != null) {
                switch (i) {
                    case 0:
                        if (getAktualniM().moveTo(i).isByl() == true) {
                            System.out.println("[S]ever, kde je " + getAktualniM().moveTo(i));
                        } else {
                            System.out.println("[S]ever, kde to jeste neznas");
                        }
                        break;
                    case 1:
                        if (getAktualniM().moveTo(i).isByl() == true) {
                            System.out.println("[J]ih, kde je " + getAktualniM().moveTo(i));
                        } else {
                            System.out.println("[J]ih, kde to jeste neznas");
                        }
                        break;
                    case 2:
                        if (getAktualniM().moveTo(i).isByl() == true) {
                            System.out.println("[V]ychod, kde je " + getAktualniM().moveTo(i));
                        } else {
                            System.out.println("[V]ychod, kde to jeste neznas");
                        }
                        break;
                    case 3:
                        if (getAktualniM().moveTo(i).isByl() == true) {
                            System.out.println("[Z]apad, kde je " + getAktualniM().moveTo(i));
                        } else {
                            System.out.println("[Z]apad, kde to jeste neznas");
                        }
                        break;
                }
            }
        }
        String volba = scan.nextLine();
        int i = 0;
        OUTER:
        for (;;) {
            switch (volba) {
                case "S":
                    i = 0;
                    break OUTER;
                case "J":
                    i = 1;
                    break OUTER;
                case "V":
                    i = 2;
                    break OUTER;
                case "Z":
                    i = 3;
                    break OUTER;
                default:
                    System.out.println("Chybne zadani! Znova urci jestli se chces presunout na [S]ever, [J]ih, [V]ychod nebo [Z]apad.");
                    volba = scan.nextLine();
                    break;
            }
        }
        //v pripade, ze se chce premistit do jeste zablokovane mistnosti, tak ho to tam nepusti
        if (getAktualniM().moveTo(i).isZablokovano() == true) {
            System.out.println("Beres za kliku s tim, ze se teda kouknes co je za temi dvermi, "
                    + "ale bohuzel to nejde. Asi jsou zamceny, ci nejak jinak zablokovany...");
        } else {
            hrac.setAktualniM(getAktualniM().moveTo(i).getNazevM());
            setAktualniM(getAktualniM().moveTo(i));
            //pokud se v dane mistnosti hrac objevil poprve vytiskne se mu popis mistnosti a nastavi se promena byl na true
            if (getAktualniM().isByl() == false) {
                System.out.println(getAktualniM().getPribeh(0));
                getAktualniM().setByl(true);
            } else {
                System.out.println("Nachazis se v mistnosti " + getAktualniM().getNazevM());
            }
        }
    }

    /**
     * nejprve se vzpise obsah inv a nasledne si hrac, pokud chce, zvoli predmet
     * s kterym chce manipulovat podle typu zvoleneho predmetu se mu vypise
     * patricna nabidka
     */
    public void praceSInv() {
        if (hrac.inventar.nic() == true) {
            System.out.println("Momentalne si rad, ze mas vubec alespon obleceni...");
        } else {
            //vypise se obsah inventar a hraci se umozni pracovat s inventarem a jeho obsahem
            System.out.println(hrac.inventar.obsahI(1, 4));
            System.out.println("Chces s necim z toho jakkoliv manipulovat? [A]no/[N]e");
            String volba = scan.nextLine();
            OUTER_1:
            for (;;) {
                switch (volba) {
                    case "A":
                        System.out.print("Tak ");
                        Item vybranyP = nactiPredmet("inv");
                        System.out.print(vybranyP.getNazevP() + " muzes [Z]ahodit nebo");
                        //s kazdym typem predmetu se da manipulovat jinak
                        switch (vybranyP.getTypP()) {
                            case 1:
                                System.out.println(" se timto predmetem [V]ybavit... ");
                                break;
                            case 2:
                                System.out.println(" [P]ouzit");
                                break;
                            case 3:
                                System.out.println(" to muzes [S]potrebovat");
                                break;
                            case 4:
                                System.out.println(" vyuzit ke [K]ombinaci s jinym predmetem...");
                                break;
                        }
                        volba = scan.nextLine();
                        for (;;) {
                            OUTER_2:
                            if ("Z".equals(volba)) {
                                hrac.inventar.odeberPredmet(vybranyP);
                                getAktualniM().pridejPredmet(vybranyP);
                                System.out.println("Predmet odebran!");
                                break OUTER_1;
                            } else {
                                switch (vybranyP.getTypP()) {
                                    case 1:
                                        if ("V".equals(volba)) {
                                            hrac.pouzijPredmet(vybranyP);
                                            break OUTER_1;
                                        }
                                        for (; !"V".equals(volba) && !"Z".equals(volba);) {
                                            System.out.println("Chybne zadani! Znova urci jestli chces " + vybranyP
                                                    + " zahodit [Z] nebo se jim [V]ybavit!");
                                            volba = scan.nextLine();
                                            break OUTER_2;
                                        }
                                    case 2:
                                        if ("P".equals(volba)) {
                                            hrac.pouzijPredmet(vybranyP);
                                            break OUTER_1;
                                        }
                                        for (; !"P".equals(volba) && !"Z".equals(volba);) {
                                            System.out.println("Chybne zadani! Znova urci jestli chces " + vybranyP
                                                    + " zahodit [Z] nebo ho [P]ouzit!");
                                            volba = scan.nextLine();
                                            break OUTER_2;
                                        }
                                    case 3:
                                        if ("S".equals(volba)) {
                                            hrac.pouzijPredmet(vybranyP);
                                            break OUTER_1;
                                        }
                                        for (; !"S".equals(volba) && !"Z".equals(volba);) {
                                            System.out.println("Chybne zadani! Znova urci jestli chces " + vybranyP
                                                    + " zahodit [Z] nebo se jej [S]potrebovat!");
                                            volba = scan.nextLine();
                                            break OUTER_2;
                                        }
                                    case 4:
                                        if ("K".equals(volba)) {
                                            hrac.inventar.odeberPredmet(vybranyP);
                                            if ("<<<<SEZNAM TOHO CO TED VLASTNIS>>>>\ncombinable\n".equals(hrac.inventar.obsahI(4, 4))) {
                                                System.out.println("Nemas u sebe uz nic vhodnyho pro tvy kutilsky hratky...");
                                                hrac.inventar.pridejPredmet(vybranyP);
                                                break OUTER_1;
                                            } else {

                                                System.out.println(hrac.inventar.obsahI(4, 4));
                                                System.out.println("OK, k tomu je zapotrebi jeste jednoho predmetu, ");
                                                Item vybranyP2 = nactiPredmet("inv");
                                                hrac.zkombinujPredmet(vybranyP, vybranyP2);
                                                break OUTER_1;
                                            }
                                        }
                                        for (; !"K".equals(volba) && !"Z".equals(volba);) {
                                            System.out.println("Chybne zadani! Znova urci jestli chces " + vybranyP
                                                    + " zahodit [Z] nebo se jej vyuzit ke [K]ombinaci!");
                                            volba = scan.nextLine();
                                            break OUTER_2;
                                        }
                                }
                            }
                        }
                    case "N":
                        break OUTER_1;
                    default:
                        System.out.println("Chybne zadani! Znova urci jestli chces neco pouzit [A] nebo ne [N]");
                        volba = scan.nextLine();
                }
            }
        }
    }
//univerzalni metoda pro nacitani predmetu a to at z inventare nebo z mistnosti (podle promenne odkud)

    public Item nactiPredmet(String odkud) {
        Item p = null;
        int velPole = 0;
        int indexPredmetu;
        System.out.print("vyber tedy, co to ma bejt...");
        switch (odkud) {
            case "inv":
                velPole = hrac.inventar.inventory.size();
                System.out.println("");
                break;
            case "mist":
                velPole = getAktualniM().obsah.size();
                System.out.println("(pro sebrani vseho zadej 0)");
                break;
        }
        OUTER_2:
        for (;;) {
            try {
                OUTER_1:
                for (;;) {
                    indexPredmetu = (readNumber() - 1);
                    if ("inv".equals(odkud)) {
                        if (indexPredmetu == -1) {
                            System.out.print("Hele musis zadat nejakou platnou hodnotu, tedy 1");
                            if (velPole == 1) {
                                System.out.println("!");
                            } else {
                                System.out.println(" az " + velPole + "!");
                            }
                            break OUTER_1;
                        } else {
                            p = hrac.inventar.nactiPredmet(indexPredmetu);
                        }
                    } else {
                        if (indexPredmetu == -1) {
                            p = null;
                        } else {
                            p = getAktualniM().obsah.get(indexPredmetu);
                        }
                    }
                    break OUTER_2;
                }
            } catch (InvalidInputException | IndexOutOfBoundsException ex) {
                System.out.print("Hele musis zadat nejakou platnou hodnotu, tedy 1");
                if (velPole == 1) {
                    System.out.println("!");
                } else {
                    System.out.println(" az " + velPole + "!");
                }
            }
        }
        return p;
    }

    public boolean zaplnenyInventar(Item pridavanyP) {
        System.out.println("Inventar je zaplneny!");
        System.out.println("Chces teda neco zahodit, aby si " + pridavanyP + " pobral? [A]no/[N]e");
        String volba = scan.nextLine();
//        OUTER:
        for (;;) {
            switch (volba) {
                case "A":
                    System.out.println(hrac.inventar.obsahI(1, 4));
                    System.out.print("Tak ");
                    Item vybranyP = nactiPredmet("inv");
                    hrac.inventar.odeberPredmet(vybranyP);
                    getAktualniM().pridejPredmet(vybranyP);
                    getAktualniM().vezmiPredmet(pridavanyP);
                    hrac.inventar.pridejPredmet(pridavanyP);
                    System.out.println("Predmet pridan!");
                    System.out.println(pridavanyP.popis[0]);
//                    break OUTER;
                    return true;
                case "N":
//                    break OUTER;
                    return false;
                default:
                    System.out.println("Chybne zadani! Znova urci jestli chces neco zahodit ci pouzit [A] nebo ne [N]");
                    volba = scan.nextLine();
            }
        }

    }

    public void prohledani() {
        if (getAktualniM().obsah.isEmpty() == true) {
            System.out.println("Vse co jsi nasel, je na dve veci, na nic a na ho...");
        } else {
            if (getAktualniM().isProhledal() == false) {
                System.out.println(getAktualniM().getPribeh(1));
                getAktualniM().setProhledal(true);
            }
            System.out.print(getAktualniM().obsahM()); //vypise se co je za predmety v dane mistnostti                  
            System.out.println("Chces si z toho neco \"privlastnit\"? [A]no/[N]e");
            String volba = scan.nextLine();
            OUTER_2:
            for (;;) {
                switch (volba) {    //volba jestli sebrat neco z nalezenych predmetu
                    case "A":
                        System.out.print("Tak ");
                        Item pridavanyP = nactiPredmet("mist");
                        getAktualniM().vezmiPredmet(pridavanyP);
                        if (pridavanyP == null) {
                            for (int i = 0; !getAktualniM().obsah.isEmpty();) {
                                pridavanyP = getAktualniM().obsah.get(i);
                                if (hrac.inventar.pridejPredmet(pridavanyP) == false) {
                                    if (zaplnenyInventar(pridavanyP)) {
                                        getAktualniM().vezmiPredmet(pridavanyP);
                                    }
                                    break;
                                } else {
                                    System.out.println(pridavanyP.popis[0]);
                                    getAktualniM().vezmiPredmet(pridavanyP);
                                }
                            }
                        } else {
                            if (hrac.inventar.pridejPredmet(pridavanyP) == false) {
                                zaplnenyInventar(pridavanyP);
                            } else {
                                System.out.println("Predmet pridan!!" + pridavanyP.popis[0]);
                            }
                        }
                        break OUTER_2;
                    case "N":
                        break OUTER_2;
                    default:
                        System.out.println("Chybne zadani! Znova urci jestli to chces sebrat [A] nebo ne [N]");
                        volba = scan.nextLine();
                }
            }

        }
    }

    public void jmeno() {
        System.out.println("Nyni si urci sve jmeno!");
        String name = scan.nextLine();
        hrac.setJmeno(name);
    }

    public void nastavAtributy(int pocetb) {
        int[] atr = new int[4];
        String ss = "N";

        boolean[] valid = new boolean[4];   //promena slouzici jako podminka pro osetreni
        OUTER_1:
        for (; "N".equals(ss);) {
            int pocetBodu = pocetb;
            for (int i = 0; i < 4; i++) {
                if (pocetBodu == 0) {
                    break;  //podmina, diky niz nebude muset hrac vyplnovat 0 kdyby mu uz dosly body
                }
                do {
                    try {
                        System.out.print("Kolik bodu z " + pocetBodu + " chces pridat ke ");
                        switch (i) {
                            case 0:
                                System.out.println("sile?");
                                break;
                            case 1:
                                System.out.println("odolnosti?");
                                break;
                            case 2:
                                System.out.println("inteligenci?");
                                break;
                            case 3:
                                System.out.println("charisma?");
                                break;
                        }
                        atr[i] = readNumber();
                        valid[i] = true;
                        if (atr[i] > pocetBodu) {
                            System.out.println("Hele musis zadat nejakou platnou hodnotu, tedy 0 az " + pocetBodu);
                            valid[i] = false;
                        }
                    } catch (InvalidInputException ex) {
                        System.out.println("Hele musis zadat nejakou platnou hodnotu, tedy 0 az " + pocetBodu);
                    }
                } while (!valid[i]);
                pocetBodu -= atr[i];

            }
            System.out.println("Sila: " + (atr[0] + hrac.getSila()));
            System.out.println("Odolnost: " + (atr[1] + hrac.getOdolnost()));
            System.out.println("Inteligence: " + (atr[2] + hrac.getInteligence()));
            System.out.println("Charisma: " + (atr[3] + hrac.getCharisma()));

            System.out.println("\nVyhovuje? [A]no/[N]e");
            ss = scan.nextLine();
            if ("A".equals(ss)) {
                hrac.setSila(hrac.getSila() + atr[0]);
                hrac.setOdolnost(hrac.getOdolnost() + atr[1]);
                hrac.setInteligence(hrac.getInteligence() + atr[2]);
                hrac.setCharisma(hrac.getCharisma() + atr[3]);
                hrac.setZivoty(hrac.getMaxZivot());
                hrac.setObrana(0);
                break OUTER_1;
            }
            //nastaveni puvodnich hodnot atributu, poctu rozdelovatelnych bodu a valid[i]
            if ("N".equals(ss)) {
                for (int i = 0; i < 4; i++) {
                    valid[i] = false;
                }
            }
            //osetreni proti zadani jineho znaku nez A, N

            for (; !"N".equals(ss) && !"A".equals(ss);) {
                System.out.println("Chybne zadani! Znova urci jestli vyhovuje [A] nebo ne [N]");
                ss = scan.nextLine();
                switch (ss) {
                    case "N":
                        break;
                    case "A":
                        hrac.setSila(hrac.getSila() + atr[0]);
                        hrac.setOdolnost(hrac.getOdolnost() + atr[1]);
                        hrac.setInteligence(hrac.getInteligence() + atr[2]);
                        hrac.setCharisma(hrac.getCharisma() + atr[3]);
                        hrac.setZivoty(hrac.getMaxZivot());
                        hrac.setObrana(0);
                        break OUTER_1;
                }
            }
        }
    }

    /*
     * metoda, pres kterou nechavam nacitat veskere uzivatelem zadana hodnoty, 
     * ktere maji byt typu int, bylo zapotrebi take vytvorit vnitrni tridu (ta hned pod touto metodou)
     */
    public static int readNumber() throws InvalidInputException {
        Scanner s = new Scanner(System.in);
        try {
            return s.nextInt();

            //"zachiceni" vyjimky IME a vraceni nove IIE
        } catch (InputMismatchException e) {
            throw new InvalidInputException(e);
        }
    }

    private static class InvalidInputException extends Exception {

        public InvalidInputException(InputMismatchException e) {
        }
    }
}
