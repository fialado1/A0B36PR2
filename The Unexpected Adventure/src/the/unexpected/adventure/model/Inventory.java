package the.unexpected.adventure.model;

/**
 *
 * @author Dominik
 */
import java.util.*;

public class Inventory extends Observable {

    private int velikost;
    ArrayList<Item> inventory = new ArrayList<>();

    Inventory(int velikost) {
        this.velikost = velikost;
    }
//nastaveni velikosti inventare, kdyby mela byt mensi nez jedna nastavi se na jedna

    public void setVelikost(int velikost) {
        if (velikost < 1) {
            this.velikost = 1;
        } else {
            this.velikost = velikost / 2;
        }
    }

    public void vytvorPredmet(Item item) {
        inventory.add(item);
        setChanged();
        notifyObservers(item.getNazevP());

    }

    public void pridejPredmet(Item nazevP, boolean notify) {
        inventory.add(nazevP);
        if (notify) {
            setChanged();
            notifyObservers("add");
        }
    }

    public void odeberPredmet(Item nazevP, String s) {
        inventory.remove(nazevP);
        setChanged();
        notifyObservers(s);
//        inventory.get(type).remove(nazevP);
    }

    public void odeberPredmet(int index) {
        inventory.remove(index);
        setChanged();
        notifyObservers("removeAndAdd");
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
    //comparator??
}
