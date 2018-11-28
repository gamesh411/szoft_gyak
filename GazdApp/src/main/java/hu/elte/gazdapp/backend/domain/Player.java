/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.backend.domain;

import hu.elte.gazdapp.backend.domain.component.Piece;
import hu.elte.gazdapp.backend.domain.component.Property;
import hu.elte.gazdapp.backend.state.State;
import hu.elte.gazdapp.backend.state.BasicState;
import java.io.Serializable;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.rmi.server.UnicastRemoteObject;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author mmeta
 */
public class Player extends UnicastRemoteObject implements PlayerInterface{

    private int money;
    private int position;
    private String name;
    private Piece piece;
    private State state;
    private Set<Property> properties; 
    private int debt;

    public Set<Property> getProperties() throws RemoteException {
        return properties;
    }

    public void setProperty(Set<Property> properties) throws RemoteException {
        this.properties = properties;
    }
    
    public void addProperty(Property property)throws RemoteException {
        properties.add(property);
    }
    

    public Player(String name, Piece piece) throws RemoteException {
        this.name = name;
        this.piece = piece;
        money = 50000;
        state = new BasicState();
        properties = new HashSet<>();
        debt = 0;
    }

    public void spendMoney(int cost) throws RemoteException {
        money -= cost;
    }

    public Piece getPiece() throws RemoteException {
        return piece;
    }

    public void setPiece(Piece piece) throws RemoteException {
        this.piece = piece;
    }

    public int getMoney()  throws RemoteException{
        return money;
    }

    public void setMoney(int money) throws RemoteException {
        this.money = money;
    }

    public int getPosition() throws RemoteException {
        return position;
    }

    public void setPosition(int position) throws RemoteException {
        this.position = position;
    }

    public String getName() throws RemoteException {
        return name;
    }

    public void setName(String name)  throws RemoteException{
        this.name = name;
    }

    public State getState()  throws RemoteException{
        return state;
    }

    public void setState(State state)  throws RemoteException{
        this.state = state;
    }

    public boolean canStep()  throws RemoteException{
        return this.state.canStep(this, money);
    }
    
    public boolean canStepWithRolledValue(int dice) throws RemoteException{
        return this.state.canStep(this, dice);
    }

    public void decSkip()  throws RemoteException{
        this.state.turn();
    }
    
    public int getDebt() throws RemoteException{
        return debt;
    }

    public void setDebt(int debt) throws RemoteException {
        this.debt = debt;
    }
}
