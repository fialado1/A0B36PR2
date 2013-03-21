/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure;


/**
 * trida reprezentujici hracovo vybaveni, ktere ma na sobe
 * 
 */
public class Equipment {
    private Item[] vybaveni;
    private int kdeNosit;
    private int bonusObr;
    private int bonusUt;

    Equipment() {
        //0-hlava, 1-telo, 2-prava ruka, 3-leva ruka, 4- nohy
        this.vybaveni = new Item[5];
        for (int i = 0; i < 5; i++) {
            vybaveni[i] = null;
        }
    }

    public void setBonusUt(Item nazevP, boolean pridat) {
        if (pridat) {
            bonusUt += nazevP.getPlusUt();
        } else {
            bonusUt -= nazevP.getPlusUt();
        }

    }
    public int getBonusUt(){
        return bonusUt;
    }

    public void setBonusObr(Item nazevP, boolean pridat) {
        if (pridat) {
            bonusObr += nazevP.getPlusObr();
        } else {
            bonusObr -= nazevP.getPlusObr();
        }
    }

    public int getBonusObr() {
        return bonusObr;
    }

    public Item getVybaveni(int i) {
        return vybaveni[i];
    }
//nastaveni predmetu na jemu prislusnou pozici na tele

    public void setVybaveni(Item nazevP, int i) {
        vybaveni[i] = nazevP;
        setBonusUt(nazevP, true);
        setBonusObr(nazevP, true);
    }

    public int getKdeNosit() {
        return kdeNosit;
    }

    public void setKdeNosit(int kdeNosit) {
        this.kdeNosit = kdeNosit;
    }
}
