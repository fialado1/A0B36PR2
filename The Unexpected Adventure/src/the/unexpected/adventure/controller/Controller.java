package the.unexpected.adventure.controller;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.io.IOException;
import javax.swing.AbstractAction;
import javax.swing.JComponent;
import javax.swing.JOptionPane;
import javax.swing.KeyStroke;
import the.unexpected.adventure.GUI.BoxButton;
import the.unexpected.adventure.GUI.ButtonOfSavedGame;
import the.unexpected.adventure.GUI.Character;
import the.unexpected.adventure.GUI.DoorButton;
import the.unexpected.adventure.GUI.EscMenu;
import the.unexpected.adventure.GUI.GUI;
import the.unexpected.adventure.GUI.InfoAndInventory;
import the.unexpected.adventure.GUI.ItemButton;
import the.unexpected.adventure.GUI.MessagePanel;
import the.unexpected.adventure.GUI.OwnGame;
import the.unexpected.adventure.GUI.QuickPanel;
import the.unexpected.adventure.model.Game;

/**
 * Trida, ktera zajistuje komunikaci mezi modelem a view
 *
 * @author Dominik
 */
public class Controller implements ActionListener {

    protected Game game;
    protected GUI gui;
    private Controller.MoveUp moveUp;
    private Controller.MoveDown moveDown;
    private Controller.MoveLeft moveLeft;
    private Controller.MoveRight moveRight;
    private Controller.InfoAndInv infoAndInv;
    private Controller.Menu menu;

    public Controller(Game game, GUI gui) {
        this.game = game;
        this.gui = gui;
        moveUp = new Controller.MoveUp();
        moveDown = new Controller.MoveDown();
        moveLeft = new Controller.MoveLeft();
        moveRight = new Controller.MoveRight();
        infoAndInv = new Controller.InfoAndInv();
        menu = new Controller.Menu();
    }

    @Override
    public void actionPerformed(ActionEvent e) {
//        switch(e.getActionCommand()){
//            case "":
//                break;
//        }

        //BUTTONS FROM MAIN MENU
        if ("newGame".equals(e.getActionCommand())) {
            gui.mm.setMainMenuRuns(false);
            gui.setFazeHry(3);
        }
        if ("loadGame".equals(e.getActionCommand())) {
            if (!gui.isShowLoadGameMenu()) {
                gui.lasm.setShowLoadBackground(true);
                gui.addToLayer(gui.lasm, new Integer(6));
                gui.addListToLayer(gui.lasm.listOfComp, new Integer(6));
                gui.moveListToFront(gui.lasm.listOfComp);
                gui.setShowLoadGameMenu(true);
                gui.lasm.addActionListener(gui.getAl());
                if (gui.lasm.isLoadForMainMenu()) {
                    gui.mm.setCompDisable();
                } else {
                    gui.em.setCompDisable();
                    gui.removeListFromLayer(gui.em.listOfComp);
                    gui.removeFromLayer(gui.em);
                }
            } else {
                gui.getLayers().removeAll();
                gui.addToLayer(gui.loading, new Integer(3));

                createNewCharacter();
                String nameOfLG = gui.lasm.getNameOfLG();
                game.startSavedGame(nameOfLG);
//                game.loadGameModel(nameOfLG);
                createGame("save/" + nameOfLG);
                gui.og.setActualRoom(game.getAktualniM().getRoomNumber());
                gui.cha.setPlayersName(game.player.getJmeno());
                gui.og.addActionListenerForAllRooms(gui.getAl());
//                game.setStats(nameOfLG);
                gui.iai.setValueOfStats(game.player.getSila(), game.player.getOdolnost(), game.player.getInteligence(), game.player.getCharisma());
                gui.iai.setValueOfStatsLeft(game.player.getUtok(), game.player.getObrana(), game.player.getZivoty(), game.player.getMaxZivot(), game.player.specVl.getKoefZav(), game.player.getZkusenosti(), game.player.getUroven());
                System.out.println("load game view");
                gui.loadGameView(nameOfLG);
                gui.cha.setFlorHeightLenght(game.cam.getPocetMistnosti());
                gui.cha.setVanishingPointLenght(game.cam.getPocetMistnosti());
                for (int i = 0; i < game.cam.getPocetMistnosti(); i++) {
                    gui.cha.setVanishingPoint(game.cam.getVanishingPointX(i), game.cam.getVanishingPointY(i), i);
                    gui.cha.setFlorHeight(game.cam.getFlorHeight(i), i);
                }
                gui.cha.setMainAngle();
                gui.cha.loadImageOfBody();
                gui.iai.updateVariables();

                if (gui.lasm.isLoadForMainMenu()) {
                    gui.mm.setMainMenuRuns(false);
                }
                System.out.println("set faze hry");
                gui.setFazeHry(6);
            }
        }
        if ("backFromLGMenu".equals(e.getActionCommand())) {
            System.out.println("back from LG");
            gui.setShowLoadGameMenu(false);
            gui.removeListFromLayer(gui.lasm.listOfComp);
            gui.removeFromLayer(gui.lasm);
            if (gui.lasm.isLoadForMainMenu()) {
                System.out.println("back from LG");
                gui.mm.setCompEnable();
            } else {
                gui.em.setCompEnable();
                gui.addToLayer(gui.em, new Integer(5));
                gui.addListToLayer(gui.em.listOfComp, new Integer(5));
                gui.moveListToFront(gui.em.listOfComp);
            }

        }
        if ("quitGame".equals(e.getActionCommand())) {
            gui.mm.setCompDisable();
            gui.addToLayer(gui.qd, new Integer(3));
            gui.addListToLayer(gui.qd.listOfComp, new Integer(3));
            gui.moveListToFront(gui.qd.listOfComp);
        }
        if ("yes".equals(e.getActionCommand())) {
            gui.mm.setMainMenuRuns(false);
            gui.setFazeHry(0);
        }
        if ("no".equals(e.getActionCommand())) {
            gui.mm.setCompEnable();
            gui.removeFromLayer(gui.qd);
            gui.removeListFromLayer(gui.qd.listOfComp);
        }

        //BUTTONS FROM NEW GAME MENU

        if ("up0".equals(e.getActionCommand())) {
            gui.ngm.valueOfStatPlus(0);
        }
        if ("up1".equals(e.getActionCommand())) {
            gui.ngm.valueOfStatPlus(1);
        }
        if ("up2".equals(e.getActionCommand())) {
            gui.ngm.valueOfStatPlus(2);
        }
        if ("up3".equals(e.getActionCommand())) {
            gui.ngm.valueOfStatPlus(3);
        }
        if ("down0".equals(e.getActionCommand())) {
            gui.ngm.valueOfStatMinus(0);
        }
        if ("down1".equals(e.getActionCommand())) {
            gui.ngm.valueOfStatMinus(1);
        }
        if ("down2".equals(e.getActionCommand())) {
            gui.ngm.valueOfStatMinus(2);
        }
        if ("down3".equals(e.getActionCommand())) {
            gui.ngm.valueOfStatMinus(3);
        }
        if ("startNewGame".equals(e.getActionCommand())) {
            if (!gui.isShowLoading()) {
                gui.getLayers().removeAll();
                gui.addToLayer(gui.loading, new Integer(3));
                gui.setShowLoading(true);
//                gui.ngm.setNewGameMenuRuns(false);
            } else {
                createNewCharacter();
                game.startNewGame();
                createGame("map");
                gui.og.addActionListenerForAllRooms(gui.getAl());

                game.setStats(gui.ngm.getValueOfStat(0), gui.ngm.getValueOfStat(1), gui.ngm.getValueOfStat(2), gui.ngm.getValueOfStat(3), gui.ngm.nameField.getText());
                gui.iai.setValueOfStats(gui.ngm.valueOfStats);
                gui.iai.setValueOfStatsLeft(game.player.getUtok(), game.player.getObrana(), game.player.getZivoty(), game.player.getMaxZivot(), game.player.specVl.getKoefZav(), game.player.getZkusenosti(), game.player.getUroven());
                gui.cha.setFlorHeightLenght(game.cam.getPocetMistnosti());
                gui.cha.setVanishingPointLenght(game.cam.getPocetMistnosti());
                for (int i = 0; i < game.cam.getPocetMistnosti(); i++) {
                    gui.cha.setVanishingPoint(game.cam.getVanishingPointX(i), game.cam.getVanishingPointY(i), i);
                    gui.cha.setFlorHeight(game.cam.getFlorHeight(i), i);
                }
                gui.cha.setPlayersName(game.player.getJmeno());
                gui.cha.setWhichBody(gui.ngm.getActualBody());
                gui.cha.setWhichHead(gui.ngm.getActualHead());
                gui.cha.setMainAngle();
                gui.cha.updateImageOfBody();
                gui.iai.updateVariables();
                gui.ngm.setNewGameMenuRuns(false);
                gui.setFazeHry(6);
            }
        }
        if ("backB".equals(e.getActionCommand())) {
            gui.ngm.setNewGameMenuRuns(false);
            gui.setFazeHry(1);
        }

        //BUTTONS FROM OWNGAME
//        
//       FOR ALL ROOMS 
        if ("closeMPanel".equals(e.getActionCommand())) {
            gui.removeListFromLayer(gui.mp.listOfComp);
        }
        if ("toEscMenu".equals(e.getActionCommand())) {
            if (!gui.isShowEscMenu()) {
                gui.og.getActualRom().setCompDisable();
                gui.qp.setCompDisable();
                gui.og.getActualRom().setItemsDisable();
                gui.addToLayer(gui.em, new Integer(5));
                gui.addListToLayer(gui.em.listOfComp, new Integer(5));
                gui.moveListToFront(gui.em.listOfComp);
                gui.setShowEscMenu(true);
            }
        }
        if ("backFromEscMenu".equals(e.getActionCommand())) {
            gui.setShowEscMenu(false);
            gui.og.getActualRom().setCompEnable();
            gui.qp.setCompEnable();
            gui.og.getActualRom().setItemsEnable();
            gui.removeListFromLayer(gui.em.listOfComp);
            gui.removeFromLayer(gui.em);
        }
        if ("saveGame".equals(e.getActionCommand())) {
            if (!gui.isShowSaveGameMenu()) {
                gui.lasm.setShowLoadBackground(false);
                gui.em.setCompDisable();
                gui.lasm.update();
                gui.lasm.addActionListener(gui.getAl());
                gui.addToLayer(gui.lasm, new Integer(6));
                gui.addListToLayer(gui.lasm.listOfCompSG, new Integer(7));
                gui.moveListToFront(gui.lasm.listOfCompSG);
                gui.removeListFromLayer(gui.em.listOfComp);
                gui.removeFromLayer(gui.em);
                gui.setShowSaveGameMenu(true);
            } else {
                game.saveGameModel(gui.lasm.nameOfGame.getText());
                gui.saveGameView(gui.lasm.nameOfGame.getText());
                gui.lasm.update();
            }
        }
        if ("backFromSGMenu".equals(e.getActionCommand())) {
            gui.setShowSaveGameMenu(false);
            gui.em.setCompEnable();
            gui.lasm.nameOfGame.setText(null);
            gui.removeListFromLayer(gui.lasm.listOfCompSG);
            gui.removeFromLayer(gui.lasm);
            gui.addToLayer(gui.em, new Integer(5));
            gui.addListToLayer(gui.em.listOfComp, new Integer(5));
            gui.moveListToFront(gui.em.listOfComp);
        }
        if ("showInfoOfSG".equals(e.getActionCommand())) {
            ButtonOfSavedGame bosg = (ButtonOfSavedGame) e.getSource();
            if (gui.isShowSaveGameMenu()) {
                gui.lasm.nameOfGame.setText(bosg.getNameOfSG());
            } else if (gui.isShowLoadGameMenu()) {
                gui.lasm.setNameOfLG(bosg.getNameOfSG());
            }
        }
        if ("endGame".equals(e.getActionCommand())) {
            gui.setShowEscMenu(false);
            gui.og.run = false;
            gui.setFazeHry(1);
        }
        if ("openInfAndInv".equals(e.getActionCommand())) {
            if (!gui.isShowInfAndInv()) {
                gui.og.getActualRom().setCompDisable();
                gui.og.getActualRom().setItemsDisable();
                gui.addToLayer(gui.iai, new Integer(4));
                gui.addListToLayer(gui.iai.listOfComp, new Integer(4));
                gui.moveListToFront(gui.iai.listOfComp);
                gui.removeListFromLayer(gui.qp.listOfComp);
                gui.setShowInfAndInv(true);
            }
        }
        if ("backFromInv".equals(e.getActionCommand())) {
            gui.setShowInfAndInv(false);
            gui.removeListFromLayer(gui.iai.listOfComp);
            gui.removeFromLayer(gui.iai);
            gui.addListToLayer(gui.qp.listOfComp, new Integer(4));
            gui.og.getActualRom().setCompEnable();
            gui.og.getActualRom().setItemsEnable();
        }
        if ("addItem".equals(e.getActionCommand())) {
//            gui.iai.addToTab((gui.og.getActualItem().getType() + 1), gui.og.getActualItem());
//            gui.removeFromLayer(gui.og.addItem);
//            gui.og.getActualRom().removeItemFromRoom(gui.og.getActualItem());
            int index = gui.og.getActualRom().indexOfItem(gui.og.getActualRom().getActualItem());
            game.addToInventory(index);
            gui.og.getActualRom().getActualItem().setToolTipText(game.getMessage());
//            gui.removeFromLayer(gui.og.addItem);
        }
        if ("removeItem".equals(e.getActionCommand())) {
//            int index = gui.og.getActualRom().indexOfItem(gui.og.getActualItem());
            System.out.println(gui.og.getActualItem().getType());
            int index = gui.iai.indexOfItem(gui.iai.getActualItem());
            gui.iai.getActualItem().setLocation(gui.cha.getActualX(), gui.cha.getActualY() - gui.iai.getActualItem().getHeight());
            game.addToRoom(index);
//            gui.removeFromLayer(gui.og.removeItem);

//            gui.iai.removeFromTab((ib.getType() + 1), ib);
        }
        if ("item".equals(e.getActionCommand())) {
            ItemButton ib = (ItemButton) e.getSource();
            int index = gui.og.getActualRom().indexOfItem(gui.og.getActualRom().getActualItem());
            System.out.println("index0 " + index);
            if (ib.isItsInRoom()) {
                System.out.println("show popup menu");
                ib.getRoomPopupMenu().show(ib, 10, 10);
                gui.og.getActualRom().setActualItem(ib);
                gui.og.setActualItem(ib);
//                gui.og.addItem.setLocation(ib.getLocation());
//                gui.addToLayer(gui.og.addItem, new Integer(3));
            }
            if (ib.isItsInInv()) {
                ib.getInvPopupMenu().show(ib, 10, 10);
                gui.og.getActualRom().setActualItem(ib);
                gui.og.setActualItem(ib);
                gui.iai.setActualItem(ib);
//                gui.og.removeItem.setLocation(ib.getMousePosition().x + gui.iai.coorTransWidth(143), ib.getMousePosition().y + gui.iai.coorTransHeight(228));
////                gui.og.removeItem.setLocation(ib.getMousePosition());
//
//                //                gui.og.removeItem.setBounds(ib.getClickedX(), ib.getClickedY(), ib.getWidth(), ib.getHeight());
////                gui.og.removeItem.setLocation(ib.getClickedX(), ib.getClickedY());
//                gui.addToLayer(gui.og.removeItem, new Integer(5));
            }
        }

        if ("equip".equals(e.getActionCommand())) {
            int index = gui.iai.indexOfItem(gui.iai.getActualItem());
            game.useItem(index);
            gui.iai.setValueOfStats(gui.ngm.valueOfStats);
            gui.iai.setValueOfStatsLeft(game.player.getUtok(), game.player.getObrana(), game.player.getZivoty(), game.player.getMaxZivot(), game.player.specVl.getKoefZav(), game.player.getZkusenosti(), game.player.getUroven());
        }
        if ("use".equals(e.getActionCommand())) {
            System.out.println("use");
            int index = gui.iai.indexOfItem(gui.iai.getActualItem());
            game.useItem(index);
            switch (gui.iai.getActualItem().getType()) {
                case 1:
                case 2:
                    gui.iai.getActualItem().doClick();
                    break;
                case 3:
                    break;
//                case 4:
//                    break;
            }
            gui.iai.setValueOfStats(game.player.getStats());
            gui.iai.setValueOfStatsLeft(game.player.getUtok(), game.player.getObrana(), game.player.getZivoty(), game.player.getMaxZivot(), game.player.specVl.getKoefZav(), game.player.getZkusenosti(), game.player.getUroven());
            gui.iai.updateVariables();
        }
        if ("consume".equals(e.getActionCommand())) {
            System.out.println("consume");
        }
        if ("combine".equals(e.getActionCommand())) {
            if (!gui.iai.isFirstIsChosen()) {
                gui.og.getActualRom().getActualItem().setSelected(true);
                gui.og.getActualRom().getActualItem().setEnabled(false);
                gui.iai.setFirstForCombine(gui.og.getActualRom().getActualItem());

                showMessage("Vyber druhý předmět...");
            } else {
                int index1 = gui.iai.indexOfItem(gui.iai.getFirstForCombine());
                int index2 = gui.iai.indexOfItem(gui.iai.getActualItem());
                game.combineItems(index1, index2);
                gui.og.getActualRom().getActualItem().setSelected(false);
                gui.og.getActualRom().getActualItem().setEnabled(true);
                gui.iai.setFirstIsChosen(false);
            }
        }

        if ("box".equals(e.getActionCommand())) {
            BoxButton bb = (BoxButton) e.getSource();
            for (int i = 0; i < bb.getListOfItems().size(); i++) {
                ItemButton ib = (ItemButton) bb.getItemFromList(i);
                System.out.println(ib.getType() + " boxButton");
                ib.addActionListToMenuItem(this);
                ib.setActionCommand("item");
                gui.og.getActualRom().addItemToRoom(bb.getItemFromList(i));
            }
//            gui.og.getActualRom().addItemsToRoom(bb.getListOfItems());
            gui.removeListFromLayer(gui.og.getActualRom().listOfItems);
            gui.addListToLayer(gui.og.getActualRom().listOfItems, new Integer(3));
            gui.moveListToFront(gui.og.getActualRom().listOfItems);
            bb.clearListOfItems();

        }
        if ("door".equals(e.getActionCommand())) {
            DoorButton db = (DoorButton) e.getSource();
//            gui.cha.movee(db.getX(), db.getY());
            //player dojde ke dverim a zkusi je otevrit, kdyz pujdou otevrit zmeni se aktualni mistnost, kdyz ne, tak se mu vypise text na obrayovku ye to nejde
            if (game.presun(db.getDirection())) {
                System.out.println("aktual rom pred: " + gui.og.getActualRom());
                gui.removeListFromLayer(gui.og.getActualRom().listOfComp);
                gui.removeListFromLayer(gui.og.getActualRom().listOfItems);
                gui.og.setActualRoom(game.getAktualniM().getRoomNumber());
                gui.og.setActualRom();
                gui.cha.setActualRoom(game.getAktualniM().getRoomNumber());
                gui.cha.setActualPosition(gui.getWidth() / 2, gui.getHeight());
                System.out.println("aktual rom po: " + gui.og.getActualRom());
//                if (!game.getAktualniM().isByl()) {
                for (int i = 0; i < gui.og.getActualRom().listOfItems.size(); i++) {
                    ItemButton ib = (ItemButton) gui.og.getActualRom().listOfItems.get(i);
                    ib.addActionListToMenuItem(this);
//                   ib.setComponentPopupMenu(ib.invPopupMenu);
                }
//                }
                gui.addListToLayer(gui.og.getActualRom().listOfComp, new Integer(2));
                gui.addListToLayer(gui.og.getActualRom().listOfItems, new Integer(3));
                gui.moveListToFront(gui.og.getActualRom().listOfComp);
                gui.moveListToFront(gui.og.getActualRom().listOfItems);
            }
            showMessage(game.getMessage());
        }
        if ("lightSwitch".equals(e.getActionCommand())) {
            if (gui.og.getActualRom().isLightOn()) {
                gui.og.getActualRom().setWhichImg(1);
                gui.og.getActualRom().setLightOn(false);
                gui.og.getActualRom().setButtonsIconDark();
            } else if (!gui.og.getActualRom().isLightOn()) {
                gui.og.getActualRom().setWhichImg(0);
                gui.og.getActualRom().setLightOn(true);
                gui.og.getActualRom().setButtonsIconLight();
            }
        }
    }

    private void createGame(String dir) {
        try {
            gui.og = new OwnGame(game.cam.getPocetMistnosti(), dir);
            gui.iai = new InfoAndInventory();
            gui.qp = new QuickPanel();
            gui.mp = new MessagePanel();
            gui.em = new EscMenu();

            gui.iai.addActionListenerToList(gui.getAl(), gui.iai.listOfComp);
            gui.qp.addActionListenerToList(gui.getAl(), gui.qp.listOfComp);
            gui.mp.addActionListenerToList(gui.getAl(), gui.mp.listOfComp);
            gui.em.addActionListenerToList(gui.getAl(), gui.em.listOfComp);
            gui.lasm.addActionListenerToList(gui.getAl(), gui.lasm.listOfComp);
            gui.lasm.addActionListenerToList(gui.getAl(), gui.lasm.listOfCompSG);
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.", "Inane error", JOptionPane.ERROR_MESSAGE);
        }
    }

    /**
     * Metoda ktera vytvori instanci tridy Character a nasledne nastavy
     * klavesove zkratky a akce(pohyb).
     */
    private void createNewCharacter() {
        try {
            gui.cha = new Character();
        } catch (IOException ex) {
            JOptionPane.showMessageDialog(null, "Eggs are not supposed to be green.", "Inane error", JOptionPane.ERROR_MESSAGE);
        }
        gui.cha.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("W"), "moveUp");
        gui.cha.getActionMap().put("moveUp", moveUp);
        gui.cha.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("S"), "moveDown");
        gui.cha.getActionMap().put("moveDown", moveDown);
        gui.cha.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("A"), "moveLeft");
        gui.cha.getActionMap().put("moveLeft", moveLeft);
        gui.cha.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("D"), "moveRight");
        gui.cha.getActionMap().put("moveRight", moveRight);
        gui.cha.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke("I"), "infoAndInv");
        gui.cha.getActionMap().put("infoAndInv", infoAndInv);
        gui.cha.getInputMap(JComponent.WHEN_IN_FOCUSED_WINDOW).put(KeyStroke.getKeyStroke(KeyEvent.VK_ESCAPE, 0), "toMenu");
        gui.cha.getActionMap().put("toMenu", menu);
    }

    /**
     * Metoda, ktera umoznuje zobrazeni textu v message panelu.
     *
     * @param s String retezec, ktery se ma zobrazit
     */
    private void showMessage(String s) {
        gui.mp.setMessage(s);
        gui.addListToLayer(gui.mp.listOfComp, new Integer(5));
    }
    /*
     * Nasleduji tridy, ktere vytvari ruzne abstraktni akce, jez jsou pak 
     * vyuzity ve vytvaareni klavesouvych ykratek a akci.
     */

    public class MoveUp extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent tf) {
            gui.cha.moveUp(gui.cha.getActualX(), gui.cha.getActualY());
        }
    }

    public class MoveDown extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent tf) {
            gui.cha.moveDown(gui.cha.getActualX(), gui.cha.getActualY());
        }
    }

    public class MoveLeft extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent tf) {
            System.out.println((gui.cha.getActualX() - gui.cha.getVelocity()));
            System.out.println((-1) * (((-gui.cha.heightScreen * (gui.cha.heightScreen - gui.cha.getVanishingPoint()[gui.og.getActualRoom()].y)) - (gui.cha.getVanishingPoint()[gui.og.getActualRoom()].y - gui.cha.heightScreen) * gui.cha.getActualY()) / (gui.cha.getVanishingPoint()[gui.og.getActualRoom()].x)));
            if ((gui.cha.getActualX() - gui.cha.getVelocity()) > (-1) * (((-gui.cha.heightScreen * (gui.cha.heightScreen - gui.cha.getVanishingPoint()[gui.og.getActualRoom()].y)) - (gui.cha.getVanishingPoint()[gui.og.getActualRoom()].y - gui.cha.heightScreen) * gui.cha.getActualY()) / (gui.cha.getVanishingPoint()[gui.og.getActualRoom()].x))) {
                gui.cha.setActualX(gui.cha.getActualX() - gui.cha.getVelocity());
            }
        }
    }

    public class MoveRight extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent tf) {
            System.out.println((gui.cha.getActualX() + gui.cha.getVelocity()));
            System.out.println((-1) * (((-gui.cha.heightScreen * (gui.cha.getVanishingPoint()[gui.og.getActualRoom()].y - gui.og.heightScreen)) - (gui.og.widthScreen * (gui.og.widthScreen - gui.cha.getVanishingPoint()[gui.og.getActualRoom()].x)) - (gui.cha.getVanishingPoint()[gui.og.getActualRoom()].y - gui.og.heightScreen) * gui.cha.getActualY()) / (gui.cha.getVanishingPoint()[gui.og.getActualRoom()].x - gui.og.widthScreen)));
            if ((gui.cha.getActualX() + gui.cha.getVelocity()) < (-1) * (((-gui.cha.heightScreen * (gui.cha.getVanishingPoint()[gui.og.getActualRoom()].y - gui.og.heightScreen)) - (gui.og.widthScreen * (gui.og.widthScreen - gui.cha.getVanishingPoint()[gui.og.getActualRoom()].x)) - (gui.cha.getVanishingPoint()[gui.og.getActualRoom()].y - gui.og.heightScreen) * gui.cha.getActualY()) / (gui.cha.getVanishingPoint()[gui.og.getActualRoom()].x - gui.og.widthScreen))) {
                gui.cha.setActualX(gui.cha.getActualX() + gui.cha.getVelocity());
            }
        }
    }

    public class InfoAndInv extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent tf) {
            if (!gui.isShowInfAndInv()) {
                gui.qp.toInfAndInv.doClick();
            } else if (gui.isShowInfAndInv()) {
                gui.iai.backB.doClick();
            }
        }
    }

    public class Menu extends AbstractAction {

        @Override
        public void actionPerformed(ActionEvent tf) {
            if (!gui.isShowEscMenu()) {
                gui.qp.toEscMenu.doClick();
            } else if (gui.isShowEscMenu()) {
                gui.em.backFromEscMenu.doClick();
            }
        }
    }
}
