/*
 * To change this template, choose Tools | Templates
 * and open the template in the editor.
 */
package the.unexpected.adventure.GUI;

import java.awt.Color;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.event.ActionListener;
import java.util.ArrayList;
import javax.swing.JComponent;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

/**
 *
 * @author Dominik
 */
public class MessagePanel extends MyClass2 {

    private JTextArea msg;
    private JScrollPane sp;
    public MyButton close;
    public ArrayList<JComponent> listOfComp = new ArrayList<>();

    public MessagePanel() {
        createAndShowGUI();
    }

    @Override
    public void createAndShowGUI() {
        super.createAndShowGUI();

        msg = new JTextArea(5, 80);
        msg.setEditable(false);
        msg.setBackground(new Color(0, 0, 0, 100));
//        msg.setForeground(new Color(153,3,3));
        msg.setForeground(new Color(250,250,250));
        msg.setFont(new Font("Arial", Font.BOLD, 15));

        sp = new JScrollPane(msg, JScrollPane.VERTICAL_SCROLLBAR_AS_NEEDED, JScrollPane.HORIZONTAL_SCROLLBAR_AS_NEEDED);
        sp.setBounds(coorTransWidth(467), coorTransHeight(22), coorTransWidth(2266), coorTransHeight(240));
        sp.setBackground(new Color(0, 0, 0, 150));
        
        close = new MyButton("closeMPanel", "others/close1_1.png", "others/close1_2.png", coorTransWidth(2963), coorTransHeight(22), coorTransWidth(103), coorTransHeight(103));
        close.setBounds(coorTransWidth(2963), coorTransHeight(22), coorTransWidth(103), coorTransHeight(103));

        listOfComp.add(sp);
        listOfComp.add(close);
    }

    public void setMessage(String message) {
        msg.setText(message);
    }

    public void addActionListenerToList(ActionListener al, ArrayList<JComponent> alc) {
        for (int i = 0; i < alc.size(); i++) {
            if (alc.get(i) instanceof MyButton) {
                MyButton mb = (MyButton) alc.get(i);
                mb.addActionListener(al);
            }
        }
    }
}
