/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.frontend;

import hu.elte.gazdapp.backend.domain.component.Piece;
import hu.elte.gazdapp.backend.domain.Player;
import hu.elte.gazdapp.backend.domain.PlayerInterface;
import hu.elte.gazdapp.backend.domain.component.Property;
import hu.elte.gazdapp.controller.MainController;
import hu.elte.gazdapp.frontend.util.GameClientType;
import hu.elte.gazdapp.frontend.util.ScreenConstants;
import hu.elte.gazdapp.frontend.windows.StartNewGameWindow;
import hu.elte.gazdapp.frontend.windows.PurchaseWindow;
import hu.elte.gazdapp.frontend.windows.GameWindow;
import hu.elte.gazdapp.frontend.windows.PropertyWindow;
import java.awt.Font;
import java.rmi.RemoteException;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.swing.UIManager;

/**
 *
 * @author <Andó Sándor Zsolt>
 */
public class GuiManager {

    private GameWindow screen;
    private StartNewGameWindow initGameScreen;
    private PurchaseWindow purchaseScreen;
    private PropertyWindow propertyScreen;
    private MainController control;
    private GameClientType gameClientType = GameClientType.SINGLE;
    private String ourPlayerName;

    private final int TEXT_SIZE = ScreenConstants.FONT_SIZE.getValue();

    public void start() {
        setUIFont(new javax.swing.plaf.FontUIResource("Dialog", Font.ITALIC, TEXT_SIZE));
        screen = new GameWindow(this);
        screen.pack();
        screen.setVisible(true);
    }

    public static void setUIFont(javax.swing.plaf.FontUIResource f) {
        java.util.Enumeration keys = UIManager.getDefaults().keys();
        while (keys.hasMoreElements()) {
            Object key = keys.nextElement();
            Object value = UIManager.get(key);
            if (value instanceof javax.swing.plaf.FontUIResource) {
                UIManager.put(key, f);
            }
        }
    }

    public void newGame() {
        control = new MainController(this);
        //control.newGame();
        initGameScreen = new StartNewGameWindow(this);
        initGameScreen.pack();
        initGameScreen.setVisible(true);
    }

    public void doRoll() {
        control.onRoll();
        screen.repaint();
    }

    public List<PlayerInterface> getPlayers() {
        return control == null ? new LinkedList<>() : control.getPlayers();
    }

    public PlayerInterface getCurrentPlayer() {
        return control == null ? null : control.getCurrentPlayer();
    }

    public void update() {
        screen.update();
        setInGameMessage(control.getMessage());
    }

    public void endRound() {
        control.endRound();
    }

    public Piece[] getColors() {
        return Piece.values();
    }


    public void startClient(Player player) {
        try {
            ourPlayerName = player.getName();
        } catch (RemoteException ex) {
            Logger.getLogger(GuiManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        control.startClient(player);
        screen.update();
    }

    public void addPlayer(String playerName, Piece color) {
        control.addPlayer(playerName, color);
        ourPlayerName = playerName;
    }

    public void startServer() {
        gameClientType = GameClientType.SERVER;
        control.startServer();
        screen.update();
    }

    public void doPurchase() {
        purchaseScreen = new PurchaseWindow(this);
        purchaseScreen.pack();
        purchaseScreen.setVisible(true);
    }

    public void viewProperties() {
        propertyScreen = new PropertyWindow(this);
        propertyScreen.pack();
        propertyScreen.setVisible(true);
    }

    public List<Property> getAllItems() {
        return new LinkedList<>(control.getFieldItems());
    }

    public Set<Property> getCurrentPlayersItems() {
        try {
            return getCurrentPlayer().getProperties();
        } catch (RemoteException ex) {
            Logger.getLogger(GuiManager.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void buySelectedItem(Property selectedItem) {
        control.buySelectedItem(selectedItem);
    }

    public void checkGame() {
        control.checkGame();
    }

    public boolean isAnyPurchasAbleItem() {
        return control.isAnyPurchasableItem();
    }

    public void hackMove(int n) {
        control.hackMove(n);
    }

    public boolean canLoan() {
        return control.canLoan();
    }

    public boolean canRepay() {
        return control.canRepay();
    }

    public void takeLoan() {
        control.takeLoan();
    }

    public void repay(int sum) {
        control.repay(sum);
    }

    public String ourPlayerName() {
        return ourPlayerName;
    }
    
    public void setInGameMessage(String message){
        screen.setInGameMessage(message);
    }

    public boolean multiGame() {
        return gameClientType.equals(GameClientType.CLIENT) || gameClientType.equals(GameClientType.SERVER);
    }

}
