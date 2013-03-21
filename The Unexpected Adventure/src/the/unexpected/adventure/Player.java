/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure;

/**
 *
 * @author Dominik
 */
public class Player {
    private int sila = 5;
    private int bonusZeSily;
    private int odolnost = 5;
    private int inteligence = 5;
    private int charisma = 5;
    private int utok = 5;
    private int obrana = 5;
    private int maxZivot;
    private int zivoty;
    private int zkusenosti;
    private int uroven = 1;
    int i = 50;
    private String jmeno;
    private String aktualniM;
    private String pouzityPr = "";
    private int pocitZav = 0;
    //vytvore konstruktoru pro tridy primo souvysejici s tridou Hrac
    Inventory inventar = new Inventory(getSila());
    Equipment equip = new Equipment();
    SpecialAbilities specVl = new SpecialAbilities();
    Music hudba = new Music();
    AnotherText dT = new AnotherText();

    public void vypisAtributy() {
        System.out.println("<<<<ATRIBUTY>>>>");
        System.out.println("Uroven: " + getUroven());
        System.out.println("Sila: " + getSila());
        System.out.println("Odolnost: " + getOdolnost());
        System.out.println("Inteligence: " + getInteligence());
        System.out.println("Charisma: " + getCharisma());
        System.out.println("Utok: " + getUtok());
        System.out.println("Obrana: " + getObrana());
        System.out.println("Pocet zivotu: " + getZivoty());
        System.out.println("Uroven zavislosti: " + specVl.getKoefZav());
        System.out.println("Zkusenosti: " + getZkusenosti());
    }
    //kombinace se provede na zaklade schodnosti String promene special

    public void zkombinujPredmet(Item p1, Item p2) {
        if (p1.getSpecial().equals(p2.getSpecial())) {
            Item novy = new Item(p1.getSpecial() + "");
            System.out.println("" + novy.popis[0]);
            inventar.pridejPredmet(novy);
            inventar.odeberPredmet(p2);
        } else {
            inventar.pridejPredmet(p1);
            System.out.println("Jsi sice co se sikovnosti tyce stejnej jako kutil Tim, ale zvoleny predmety nijak nezkombinujes...");
        }
    }
//metodda vyvolana pri pouziti predmetu, ktere si lze nasadit

    public void nasadPredmet(Item nazevP) {
        equip.setKdeNosit(Integer.valueOf(nazevP.getSpecial()));
        System.out.println(nazevP.getSpecial());
        if (equip.getVybaveni(equip.getKdeNosit()) != null) {
            inventar.pridejPredmet(equip.getVybaveni(equip.getKdeNosit()));
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

    public void pouzijPredmet(Item pr) {
        switch (pr.getTypP()) {
            case 1:
                if (lzePouzit(pr) == true) {
                    nasadPredmet(pr);
                    inventar.odeberPredmet(pr);
                    System.out.println(pr.popis[1]);
                }
                if (lzePouzit(pr) == false) {
                    System.out.println(pr.popis[2]);
                }
                break;
            case 2:
                System.out.println(pr.popis[1]);
                switch (pr.getSpecial()) {
                    case "w":
                        hudba.hrej("K1");
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
                    inventar.odeberPredmet(pr);
                    System.out.println(pr.popis[1]);
                    if ("z1".equals(pr.getSpecial()) || "z2".equals(pr.getSpecial())) {
                        pocitZav++;
                        //po prvnim pouziti navykove latky se hraci vytiskne text s tim o co jde
                        if (pocitZav == 1) {
                            System.out.println(dT.getText(2));
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

                } else if (lzePouzit(pr) == false) {
                    System.out.println(pr.popis[2]);
                }
                break;
        }
    }
//metoda urcujici jestli hrac je schopen predmet pouzit (kazdy typ predmetu ma jine podminky)

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
        inventar.setVelikost(getSila());
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

    }

    public int getZkusenosti() {
        return zkusenosti;
    }

    /**
     * vzdy, kdyz se zavola metoda setZkusenosti, se nastavi i uroven postavy,
     * pokud doslo k jeji navyseni, dojde k vrceni true hodnoty, diky cemuz se
     * pak da dale urcit, kdy zavolat metodu, ktera prida dalsi body k atributu
     */
    public boolean setZkusenosti(int zkusenosti) {
        this.zkusenosti += zkusenosti;
        System.out.println("Ziskane zkusenosti: +" + zkusenosti);
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
