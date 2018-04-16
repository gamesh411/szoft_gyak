/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.backend;

import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Deque;
import java.util.LinkedList;
import java.util.List;
import java.util.Queue;
import java.util.Random;

/**
 *
 * @author mmeta
 */
public class Board {
    
    private List<Player> players;
    private Player currentPlayer;
    private final List<IField> fields;
    private final int BOARDSIZE = 42;
    
    private final Deque<IGameAction> actionQueue;

    public Board() {
        players = new ArrayList<>();
        fields = new ArrayList<>();
        actionQueue = new ArrayDeque<>();
    }
    
    
    public void addPlayer(Player player){
        players.add(player);
    }

    public List<Player> getPlayers() {
        return players;
    }

    public void setPlayers(List<Player> players) {
        this.players = players;
    }

    public Player getCurrentPlayer() {
        return currentPlayer;
    }

    public void setCurrentPlayer(Player currentPlayer) {
        this.currentPlayer = currentPlayer;
    }
    
    public void start(){
        currentPlayer = players.get(0);
    }
    
    public boolean queueLateAction(IGameAction action) {
        return actionQueue.offerLast(action);
    }
    
    public boolean queueImmediateAction(IGameAction action) {
        return actionQueue.offerFirst(action);
    }
    
    public void doTurn(){
        while (!actionQueue.isEmpty()) {
            actionQueue.poll().execute();
        }
    }
    
    public void step(){
        Random rand = new Random();
        int dice = rand.nextInt(6)+1;
        int newPosition = (currentPlayer.getPosition() + dice) % BOARDSIZE;
        currentPlayer.setPosition(newPosition);
        //TODO: get the tile the player landed on, and add its action to the action queue
    }

    public void nextPlayer() {
        currentPlayer = players.get( (players.indexOf(currentPlayer) + 1) % players.size() );
    }
    
    
    
    
    
    
    
    
    
    
}
