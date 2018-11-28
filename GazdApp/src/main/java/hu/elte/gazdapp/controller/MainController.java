/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.controller;

import hu.elte.gazdapp.backend.domain.component.Property;
import hu.elte.gazdapp.backend.domain.component.Piece;
import hu.elte.gazdapp.backend.domain.Player;
import hu.elte.gazdapp.backend.domain.Board;
import hu.elte.gazdapp.backend.domain.BoardInterface;
import hu.elte.gazdapp.controller.action.NextPlayerGameAction;
import hu.elte.gazdapp.controller.action.StepAction;
import hu.elte.gazdapp.controller.action.CostAction;
import hu.elte.gazdapp.controller.action.GameAction;
import hu.elte.gazdapp.controller.action.MoveAction;
import hu.elte.gazdapp.controller.action.ShowMessageGameAction;
import hu.elte.gazdapp.frontend.GuiManager;

import java.io.IOException;
import java.rmi.NotBoundException;
import java.rmi.RemoteException;
import java.rmi.registry.LocateRegistry;
import java.rmi.registry.Registry;
import java.util.List;
import java.util.Set;
import java.util.Timer;
import java.util.TimerTask;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 *
 * @author MetaPC
 */
public class MainController  {

    private BoardInterface board;
    private GuiManager gui;
    public final static int REPAY_AMOUNT = 5000;
    public final static int LOAN = 20000;
    public static final int OWN_RESOURCE = 15000; 
    
    private Registry registry;
    private Timer updateTimer;
    

    public MainController(GuiManager gui) {
        this.gui = gui;
    }

    public void newGame() {
        try {
            board = new Board();
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
    
    public void startClient(Player player) {
    	try {
            registry = LocateRegistry.getRegistry(12345);
            board = (BoardInterface) registry.lookup("rmiServer");
            
            board.addPlayer(player);
            board.start();
            
            updateTimer = new Timer(true);
            updateTimer.scheduleAtFixedRate(new TimerTask() {
				
				@Override
				public void run() {
					gui.update();
				}
				
			}, 0, 200);
            
        } catch (IOException | NotBoundException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
        

    public void onRoll() {
        try {
            board.queueLateAction(new StepAction(board, gui));
            board.doTurn();
            gui.update();
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public List<Player> getPlayers() {
        try {
            return board.getPlayers();
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public Player getCurrentPlayer() {
        try {
            return board.getCurrentPlayer();
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void endRound() {
        try {
            board.queueLateAction(new NextPlayerGameAction(board, gui));
            board.doTurn();
            gui.update();
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void addPlayer(String playerName, Piece color) {
        try {
            board.addPlayer(new Player(playerName, color));
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void buySelectedItem(Property selectedItem)  {
        try {
            GameAction purchase = new CostAction(board, selectedItem.getPrice());
            board.queueImmediateAction(purchase);
            board.doTurn();
            board.getCurrentPlayer().addProperty(selectedItem);
            gui.update();
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public Set<Property> getFieldItems() {
        try {
            return board.getFields()[board.getCurrentPlayersPosition()].getProperties();
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return null;
    }

    public void checkGame() {
        try {
            board.checkGame();
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean isAnyPurchasableItem() {
        try {
            Set<Property> p = board.getFields()[board.getCurrentPlayersPosition()].getProperties();
            if(p.contains(Property.HOUSEHOLD) || p.contains(Property.KITCHEN) || p.contains(Property.LIVING)){
                return board.getCurrentPlayer().getProperties().contains(Property.HOUSE);
            }
            return !p.isEmpty();
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }
    
    public void hackMove(int n){
        try {
            board.queueImmediateAction(new MoveAction(board, gui, n));
            board.doTurn();
            gui.update();
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public boolean canLoan(){
        try {
            int pos = board.getCurrentPlayersPosition();
            Player p = board.getCurrentPlayer();
            return (pos == 19 || pos == 39) && !p.getProperties().contains(Property.HOUSE) && p.getMoney() >= 15000;
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public boolean canRepay(){
        try {
            return board.getCurrentPlayer().getDebt() > 0 && board.getCurrentPlayer().getMoney() >= REPAY_AMOUNT;
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
        return false;
    }

    public void takeLoan(){
        try {
            GameAction purchase = new CostAction(board, OWN_RESOURCE);
            board.queueImmediateAction(purchase);
            board.doTurn();
            board.getCurrentPlayer().addProperty(Property.HOUSE);
            board.getCurrentPlayer().setDebt(LOAN);
            gui.update();
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }

    public void repay(int sum){
        try {
            Player p = board.getCurrentPlayer();
            GameAction purchase = new CostAction(board, sum);
            board.queueImmediateAction(purchase);
            p.setDebt(p.getDebt()-sum);
            if(p.getDebt()==0){
                board.queueLateAction(new ShowMessageGameAction("Hitel sikeresn visszafizetve!"));
            }
            board.doTurn();
            gui.update();
            board.checkGame();
        } catch (RemoteException ex) {
            Logger.getLogger(MainController.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}
