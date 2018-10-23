/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.frontend;

import gazd.backend.domain.component.Piece;
import gazd.backend.domain.Player;
import gazd.backend.domain.component.Property;
import gazd.controller.MainController;
import gazd.frontend.windows.StartNewGameWindow;
import gazd.frontend.windows.PurchaseWindow;
import gazd.frontend.windows.GameWindow;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;

/**
 *
 * @author <Andó Sándor Zsolt>
 */
public final class GuiManager {

    private GameWindow screen;
    private StartNewGameWindow initGameScreen;
    private PurchaseWindow purchaseScreen;
    private MainController control;

    public void start() {
        screen = new GameWindow(this);
        screen.pack();
        screen.setVisible(true);
    }

    public void newGame() {
        control = new MainController(this);
        control.newGame();
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

    public void startGame() {
        control.start();
        screen.repaint();
    }

    public void doPurchase() {
        purchaseScreen = new PurchaseWindow(this);
        purchaseScreen.pack();
        purchaseScreen.setVisible(true);
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
    
    public void hackMove(int n){
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
