/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure;

import javax.swing.SwingUtilities;

/**
 *
 * @author Dominik
 */
public class TheUnexpectedAdventure{

    
    public static void main(String[] args) {
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                new GUI();
            }
        });
    }

    

}
