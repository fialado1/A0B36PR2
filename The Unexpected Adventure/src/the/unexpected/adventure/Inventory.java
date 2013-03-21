/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure;

/**
 *
 * @author Dominik
 */

import java.util.*;

public class Inventory {
    private int velikost;
    ArrayList<Item> inventory;

    Inventory(int velikost) {
        this.velikost = velikost;
        inventory = new ArrayList<>();

    }
//nastaveni velikosti inventare, kdyby mela byt mensi nez jedna nastavi se na jedna

    public void setVelikost(int velikost) {
        if (velikost < 1) {
            this.velikost = 1;
        } else {
            this.velikost = velikost/2;
        }
    }

    public boolean pridejPredmet(Item nazevP) {
        if (obsazenost() == false) {
            inventory.add(nazevP);
            return true;
        } else {
            return false;
        }
    }

    public void odeberPredmet(Item nazevP) {
        inventory.remove(nazevP);
    }

    //metoda co vraci predmet v podobe String nazvu
    public Item nactiPredmet(int indexPredmetu) {
        return inventory.get(indexPredmetu);

    }

    public boolean obsazenost() {
        return !(inventory.size() <= velikost);
    }

    public boolean nic() {
        if (inventory.isEmpty() == true) {
            return true;
        } else {
            return false;
        }
    }
    /*
     * metoda vypisujici obsah rozdeleny podle typu predmetu(viz. trida predmet)
     * trideni pomoci coparatoru
     */

    public String obsahI(int rozsah1, int rozsah2) {
        Collections.sort(inventory, new Inventory.ComTypP());
        String s = "<<<<SEZNAM TOHO CO TED VLASTNIS>>>>\n";
        for (int i = rozsah1; i <= rozsah2; i++) {
            switch (i) {
                case 1:
                    s += "equipment\n";
                    break;
                case 2:
                    s += "accessory\n";
                    break;
                case 3:
                    s += "useable\n";
                    break;
                case 4:
                    s += "combinable\n";
                    break;
            }
            for (int j = 0; j < inventory.size(); j++) {
                if (inventory.get(j).getTypP() == i) {
                    s += " " + (j + 1) + " - " + inventory.get(j) + "\n";
                }
            }
        }
        return s;
    }
    /*
     * vnitrni trida slouzici
     */

    public static class ComTypP implements Comparator<Item> {

        @Override
        public int compare(Item p1, Item p2) {
            return p1.getTypP() - p2.getTypP();
        }
    }
}
