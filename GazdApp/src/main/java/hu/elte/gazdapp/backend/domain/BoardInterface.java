/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.backend.domain;

import hu.elte.gazdapp.controller.action.GameAction;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.List;

/**
 *
 * @author MetaPC
 */
public interface BoardInterface extends Remote{
    
    public void addPlayer(Player player) throws RemoteException;
    
    public List<Player> getPlayers() throws RemoteException;

    public void setPlayers(List<Player> players) throws RemoteException;

    public Player getCurrentPlayer() throws RemoteException;

    public void setCurrentPlayer(Player currentPlayer) throws RemoteException;

    public void start() throws RemoteException;

    public boolean queueLateAction(GameAction action) throws RemoteException;

    public boolean queueImmediateAction(GameAction action) throws RemoteException;

    public void doTurn() throws RemoteException;

    public void step() throws RemoteException;

    public void stepOn(int newPosition) throws RemoteException;

    public void nextPlayer() throws RemoteException;

    public void drawCard() throws RemoteException;

    public Field[] getFields() throws RemoteException;

    void checkGame() throws RemoteException;
    
    public int getCurrentPlayersPosition() throws RemoteException;
}