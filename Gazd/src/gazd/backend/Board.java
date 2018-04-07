/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.backend;

import java.util.LinkedList;
import java.util.Random;

/**
 *
 * @author mmeta
 */
public class Board {
    
    private LinkedList<Player> players;
    private Player currentPlayer;
    private final LinkedList<Field> fields;
    private final int BOARDSIZE = 42;

    public Board() {
        players = new LinkedList<>();
        fields = Field.createFields();
    }
    
    
    public void addPlayer(Player player){
        players.add(player);
    }

    public LinkedList<Player> getPlayers() {
        return players;
    }

    public void setPlayers(LinkedList<Player> players) {
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
    
    public void round(){   
        step();
        nextPlayer();  
    }
    
    public void step(){
        Random rand = new Random();
        int dice = rand.nextInt(6)+1;
        int newPosition = (currentPlayer.getPosition() + dice) % BOARDSIZE;
        currentPlayer.setPosition(newPosition);     
    }

    private void nextPlayer() {
        currentPlayer = players.get( (players.indexOf(currentPlayer) + 1) % players.size() );
    }
    
    
    
    
    
    
    
    
    
    
}
