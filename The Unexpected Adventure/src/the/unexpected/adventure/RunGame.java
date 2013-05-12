/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure;

import java.awt.Graphics;
import java.awt.Image;
import java.awt.Toolkit;
import javax.sound.sampled.LineUnavailableException;
import javax.sound.sampled.UnsupportedAudioFileException;
import javax.swing.JOptionPane;
import javax.swing.JWindow;
import the.unexpected.adventure.GUI.GUI;
import the.unexpected.adventure.controller.Controller;
import the.unexpected.adventure.model.Game;
import the.unexpected.adventure.model.Music;

/**
 * Trida jejimz ukolem je nejprve zobrazit "splashove" okno v podobe gifu, ktere
 * zmizi hned pote, co se vytvori vsechny potrebne instance.
 *
 * @author Dominik
 */
public class RunGame extends JWindow {

    Image img = Toolkit.getDefaultToolkit().getImage("splash.gif");

    public RunGame() {
        img = img.getScaledInstance(300, 300, Image.SCALE_DEFAULT);
        setSize(300, 300);
        setLocationRelativeTo(null);
        setVisible(true);

        Music music;
        try {
            music = new Music();
            music.startThread();
        } catch (UnsupportedAudioFileException | LineUnavailableException ex) {
            JOptionPane.showMessageDialog(null, "Chyba při načítání hudebních souborů.", "Tády dády dá!", JOptionPane.ERROR_MESSAGE);
            System.exit(1);
        }
        Game game = new Game();
        GUI gui = new GUI();
        game.addObserver(gui);

        game.player.addObserver(gui);
        game.player.inventar.addObserver(gui);

        Controller controller = new Controller(game, gui);
        gui.addController(controller);
        //rozpusteni okna s gifem
        dispose();
    }

    @Override
    public void paint(Graphics g) {
        g.drawImage(img, 0, 0, this);
    }
}