package the.unexpected.adventure.model;

import java.io.IOException;
import java.util.Observable;

/**
 *
 * @author Dominik
 */
public final class Player extends Observable {

    private int sila;
    private int bonusZeSily;
    private int odolnost;
    private int inteligence;
    private int charisma;
    private int utok;
    private int obrana;
    private int maxZivot;
    private int zivoty;
    private int zkusenosti;
    private int uroven = 1;
    private int[] valueOfStats = new int[11];
    int i = 50;
    private String jmeno;
    private String aktualniM;
    private String pouzityPr = "";
    private String message;
    private int pocitZav = 0;
    //vytvore konstruktoru pro tridy primo souvysejici s tridou Hrac
    public Inventory inventar = new Inventory(getSila());
    Equipment equip = new Equipment();
    public SpecialAbilities specVl = new SpecialAbilities();
//    Music hudba = new Music();
    AnotherText dT = new AnotherText();
    //kombinace se provede na zaklade schodnosti String promene special

    /**
     *
     * @param p1
     * @param p2
     * @throws IOException pokud doslo k chybe pri vytvreni noveho predmetu
     */
    public void zkombinujPredmet(Item p1, Item p2) throws IOException {
        if (p1.getSpecial().equals(p2.getSpecial())) {
            Item novy = new Item(p1.getSpecial() + "");
            message = novy.popis[0];
            inventar.pridejPredmet(novy, true);
            inventar.odeberPredmet(p1, "onlyRemove");
            inventar.odeberPredmet(p2, "onlyRemove");
        } else {
            message = "Jsi sice co se sikovnosti tyce stejnej jako kutil Tim, ale zvoleny predmety nijak nezkombinujes...";
        }
        setChanged();
        notifyObservers(message);
    }

    /**
     * metodda vyvolana pri pouziti predmetu, ktery si lze nasadit
     *
     * @param nazevP odpovida promenne typu Item, ktereho se to tyka
     */
    public void nasadPredmet(Item nazevP) {
        equip.setKdeNosit(Integer.valueOf(nazevP.getSpecial()));
        System.out.println(nazevP.getSpecial());
        if (equip.getVybaveni(equip.getKdeNosit()) != null) {
            inventar.pridejPredmet(equip.getVybaveni(equip.getKdeNosit()), true);
            equip.setBonusObr(equip.getVybaveni(equip.getKdeNosit()), false);
            equip.setBonusUt(equip.getVybaveni(equip.getKdeNosit()), false);

            equip.setVybaveni(nazevP, equip.getKdeNosit());
            setUtok('p', equip.getBonusUt() + getUtok());
            setObrana(equip.getBonusObr());
        } else {
            equip.setVybaveni(nazevP, equip.getKdeNosit());
            setUtok('p', equip.getBonusUt() + getUtok());
            setObrana(equip.getBonusObr());
        }
    }

    /**
     * Metoda volana, kdyz hrac chce pouzit predmet. Nejprve se zjisti, jestli
     * jej lze pouzit nebo ne. Tomu taky potom odpovida promenna message
     *
     * @param pr
     */
    public void pouzijPredmet(Item pr) {
        switch (pr.getTypP()) {
            case 1:
                if (lzePouzit(pr) == true) {
                    nasadPredmet(pr);
                    inventar.odeberPredmet(pr, "onlyRemove");
                    message = pr.popis[1];
                }
                if (lzePouzit(pr) == false) {
                    message = pr.popis[2];
                }
                break;
            case 2:
                message = pr.popis[1];
                switch (pr.getSpecial()) {
                    case "w":
//                        hudba.hrej("K1");
                        break;
                    case "z1":
                        specVl.setKoefZav(1);
                        break;
                    case "z2":
                        specVl.setKoefZav(2);
                        break;
                    default:
                        break;
                }
                //nastavi se jaky predmet byl pouzit naposledy
                setPouzityPr(pr.getNazevP());
                break;
            case 3:
                if (lzePouzit(pr) == true) {
                    setSila(pr.getPlusS() + getSila());
                    setOdolnost(pr.getPlusO() + getOdolnost());
                    setInteligence(pr.getPlusI() + getInteligence());
                    setCharisma(pr.getPlusCh() + getCharisma());
                    setZivoty(pr.getPlusZ());
//                    inventar.odeberPredmet(pr);
                    message = pr.popis[1];
                    if ("z1".equals(pr.getSpecial()) || "z2".equals(pr.getSpecial())) {
                        pocitZav++;
                        //po prvnim pouziti navykove latky se hraci vytiskne text s tim o co jde
                        if (pocitZav == 1) {
                            message += "\n \n" + dT.getText(2);
                        }
                        int[] korekce = new int[4];
                        korekce[0] = specVl.silaZ();
                        korekce[1] = specVl.odolnostZ();
                        korekce[2] = specVl.inteligenceZ();
                        korekce[3] = specVl.charismaZ();
                        switch (pr.getSpecial()) {
                            case "z1":
                                specVl.setKoefZav(1);
                                break;
                            case "z2":
                                specVl.setKoefZav(2);
                                break;
                        }
                        setSila(getSila() - specVl.silaZ() + korekce[0]);
                        setOdolnost(getOdolnost() - specVl.odolnostZ() + korekce[1]);
                        setInteligence(getInteligence() - specVl.inteligenceZ() + korekce[2]);
                        setCharisma(getCharisma() - specVl.charismaZ() + korekce[3]);
                    }
                    inventar.odeberPredmet(pr, "onlyRemove");
                } else if (lzePouzit(pr) == false) {
                    message = pr.popis[2];
                }
                break;
            case 4:
                break;
        }
        setChanged();
        notifyObservers(message);
    }

    /**
     * metoda urcujici jestli hrac je schopen predmet pouzit (kazdy typ predmetu
     * ma jine podminky)
     *
     * @param pr odpovida promenne typu Item, ktereho se to tyka
     * @return vraci true pokud predmet lze pouzit, opacne false
     */
    public boolean lzePouzit(Item pr) {
        int pomocna = 2;
        switch (pr.getTypP()) {
            case 1:
                if (getSila() >= pr.getMinS()) {
                    pomocna = 1;
                } else {
                    pomocna = 0;
                }
                break;
            case 2:
                pomocna = 1;
                break;
            case 3:
                if (getSila() >= pr.getMinS() && getInteligence() >= pr.getMinI()) {
                    pomocna = 1;
                } else {
                    pomocna = 0;
                }
                break;
            case 4:
                pomocna = 1;
                break;
        }
        if (pomocna == 1) {
            return true;
        } else {
            return false;
        }
    }

    //  nasleduje celkem dost getteru a setteru, ktere se tykaji atributu
    public String getPouzityPr() {
        return pouzityPr;
    }

    public void setPouzityPr(String pouzityPr) {
        this.pouzityPr = pouzityPr;
    }

    public String getAktualniM() {
        return aktualniM;
    }

    public void setAktualniM(String aktualniM) {
        this.aktualniM = aktualniM;
    }

    public int[] getValueOfStats() {
        return valueOfStats;
    }

    public int getValueOfStats(int index) {
        return valueOfStats[index];
    }

    public void setValueOfStats(int index, int value) {
        this.valueOfStats[index] = value;
    }

    public void setValueOfStats() {
        this.valueOfStats[0] = sila;
        this.valueOfStats[1] = odolnost;
        this.valueOfStats[2] = inteligence;
        this.valueOfStats[3] = charisma;
        this.valueOfStats[4] = utok;
        this.valueOfStats[5] = obrana;
        this.valueOfStats[6] = zivoty;
        this.valueOfStats[7] = maxZivot;
        this.valueOfStats[8] = specVl.getKoefZav();
        this.valueOfStats[9] = zkusenosti;
        this.valueOfStats[10] = uroven;
    }

    public void setStats() {
        setSila(valueOfStats[0]);
        setOdolnost(valueOfStats[1]);
        setInteligence(valueOfStats[2]);
        setCharisma(valueOfStats[3]);
    }

    public int[] getStats() {
        int[] a = new int[4];
        a[0] = sila;
        a[1] = odolnost;
        a[2] = inteligence;
        a[3] = charisma;
        return a;
    }

    public String getJmeno() {
        return jmeno;
    }

    public void setJmeno(String name) {
        this.jmeno = name;
    }

    public int getSila() {
        return sila;
    }

    public void setSila(int strength) {
        sila = strength;
        int oprava = bonusZeSily;
        bonusZeSily = sila / 8;
        setUtok('s', getUtok() - oprava + bonusZeSily);
        inventar.setVelikost(sila);
    }

    public void setBonusZeSily(int sila) {
        bonusZeSily = sila / 8;
    }

    public int getBonusZeSily() {
        return bonusZeSily;
    }

    public int getOdolnost() {
        return odolnost;
    }

    public void setOdolnost(int constit) {
        odolnost = constit;
        setMaxZivot();
    }

    public int getInteligence() {
        return inteligence;
    }

    public void setInteligence(int intel) {
        inteligence = intel;
    }

    public int getCharisma() {
        return charisma;
    }

    public void setCharisma(int charism) {
        charisma = charism;
    }

    public int getUtok() {
        return utok;
    }

    public void setUtok(char b, int utok) {
        switch (b) {
            case 's':
                this.utok = utok;
                break;
            case 'p':
                int oprava = bonusZeSily;
                bonusZeSily = sila / 8;
                this.utok = utok - oprava + bonusZeSily;
                break;
        }

    }

    public int getObrana() {
        return obrana;
    }

    public void setObrana(int obrana) {
        this.obrana += obrana;
    }

    public int getZivoty() {
        return zivoty;
    }

    public void setZivoty(int zivoty) {
        this.zivoty += zivoty;
        if (this.zivoty > maxZivot) {
            this.zivoty = maxZivot;
        }

    }

    public int getMaxZivot() {
        return maxZivot;
    }

    public void setMaxZivot() {
        maxZivot = (getOdolnost() * 5) + 15;
        setZivoty(maxZivot);

    }

    public int getZkusenosti() {
        return zkusenosti;
    }

    /**
     * vzdy, kdyz se zavola metoda setZkusenosti, se nastavi i uroven postavy,
     * pokud doslo k jeji navyseni, dojde k vrceni true hodnoty, diky cemuz se
     * pak da dale urcit, kdy zavolat metodu, ktera prida dalsi body k atributu
     *
     * @param zkusenosti odpovida cislu, kolik se ma pridat zkusenosti
     */
    public boolean setZkusenosti(int zkusenosti) {
        this.zkusenosti += zkusenosti;
        if (setUroven()) {
            return true;
        } else {
            return false;
        }
    }

    public int getUroven() {
        return uroven;
    }

    public boolean setUroven() {
        if (getZkusenosti() >= i * 2) {
            this.uroven++;
            i = i * 2;
            return true;
        } else {
            return false;
        }
    }
}
