/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.backend.domain;

import gazd.backend.domain.component.Piece;
import gazd.backend.domain.component.Property;
import gazd.backend.state.State;
import gazd.backend.state.BasicState;
import java.util.HashSet;
import java.util.Set;

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
    private Set<Property> properties; 

    public Set<Property> getProperties() {
        return properties;
    }

    public void setProperty(Set<Property> properties) {
        this.properties = properties;
    }
    
    public void addProperty(Property property) {
        properties.add(property);
    }
    

    public Player(String name, Piece piece) {
        this.name = name;
        this.piece = piece;
        money = 17100;
        state = new BasicState();
        properties = new HashSet<>();
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

    public boolean canStep() {
        return this.state.canStep(this, money);
    }
    
    public boolean canStepWithRolledValue(int dice){
        return this.state.canStep(this, dice);
    }

    public void decSkip() {
        this.state.turn();
    }
}
