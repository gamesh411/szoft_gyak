/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.backend;

import gazd.backend.state.State;
import gazd.backend.state.BasicState;

/**
 *
 * @author mmeta
 */
public class Player {

    private int money;
    private int position;
    private String name;
    private Piece piece;
    private State state;

    public Player(String name, Piece piece) {
        this.name = name;
        this.piece = piece;
        money = 17100;
        state = new BasicState();
    }

    public void spendMoney(int cost) {
        money -= cost;
    }

    public Piece getPiece() {
        return piece;
    }

    public void setPiece(Piece piece) {
        this.piece = piece;
    }

    public int getMoney() {
        return money;
    }

    public void setMoney(int money) {
        this.money = money;
    }

    public int getPosition() {
        return position;
    }

    public void setPosition(int position) {
        this.position = position;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }
    
    

}
