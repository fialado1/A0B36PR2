package the.unexpected.adventure.model;

/**
 *
 * @author Dominik
 */
public class SpecialAbilities {
     private int koefZav = 0;

    public void vlivZavislosti() {
        if (getKoefZav() > 0) {
        }
    }

    public int silaZ() {
        int sila = getKoefZav() / 2;
        return sila;
    }

    public int odolnostZ() {
        int odolnost = getKoefZav() / 2;
        return odolnost;

    }

    public int inteligenceZ() {
        int inteligence = getKoefZav() / 2;
        return inteligence;

    }

    public int charismaZ() {
        int charisma = getKoefZav() / 3;
        return charisma;

    }

    public void setKoefZav(int koefZav) {
        this.koefZav += koefZav;
    }

    public int getKoefZav() {
        return koefZav;
    }
}
