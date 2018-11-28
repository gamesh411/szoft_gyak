/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.backend.domain;

import hu.elte.gazdapp.backend.domain.component.Piece;
import hu.elte.gazdapp.backend.domain.component.Property;
import hu.elte.gazdapp.backend.state.BasicState;
import hu.elte.gazdapp.backend.state.State;
import java.rmi.Remote;
import java.rmi.RemoteException;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author MetaPC
 */
public interface PlayerInterface extends Remote {
    
    public Set<Property> getProperties() throws RemoteException;

    public void setProperty(Set<Property> properties) throws RemoteException;
    
    void addProperty(Property property) throws RemoteException;

    public void spendMoney(int cost) throws RemoteException;

    public Piece getPiece() throws RemoteException;

    public void setPiece(Piece piece) throws RemoteException;

    public int getMoney() throws RemoteException;

    public void setMoney(int money) throws RemoteException;

    public int getPosition() throws RemoteException;

    public void setPosition(int position) throws RemoteException;

    public String getName() throws RemoteException;

    public void setName(String name) throws RemoteException;

    public State getState() throws RemoteException;

    public void setState(State state) throws RemoteException;

    public boolean canStep() throws RemoteException;
    
    public boolean canStepWithRolledValue(int dice) throws RemoteException;

    public void decSkip() throws RemoteException;
    
    public int getDebt() throws RemoteException;

    public void setDebt(int debt) throws RemoteException;
}
