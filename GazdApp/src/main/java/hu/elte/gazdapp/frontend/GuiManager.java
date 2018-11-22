/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.frontend;

import hu.elte.gazdapp.backend.domain.component.Piece;
import hu.elte.gazdapp.backend.domain.Player;
import hu.elte.gazdapp.backend.domain.component.Property;
import hu.elte.gazdapp.controller.MainController;
import hu.elte.gazdapp.frontend.util.ScreenConstants;
import hu.elte.gazdapp.frontend.windows.StartNewGameWindow;
import hu.elte.gazdapp.frontend.windows.PurchaseWindow;
import hu.elte.gazdapp.frontend.windows.GameWindow;
import hu.elte.gazdapp.frontend.windows.PropertyWindow;
import java.awt.Font;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
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

    public List<Player> getPlayers() {
        return control == null ? new LinkedList<>() : control.getPlayers();
    }

    public Player getCurrentPlayer() {
        return control == null ? null : control.getCurrentPlayer();
    }

    public void update() {
        screen.update();
    }

    public void endRound() {
        control.endRound();
    }

    public Piece[] getColors() {
        return Piece.values();
    }

    public void addPlayer(String playerName, Piece color) {
        control.addPlayer(playerName, color);
    }

    public void startClient(Player player) {
        control.startClient(player);
        screen.repaint();
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
        return getCurrentPlayer().getProperties();
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
}
