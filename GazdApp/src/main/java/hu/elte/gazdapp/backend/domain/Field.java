/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.backend.domain;

import hu.elte.gazdapp.backend.domain.component.Property;
import java.util.HashSet;
import java.util.Set;
import hu.elte.gazdapp.controller.action.GameAction;
import java.io.Serializable;

/**
 *
 * @author MetaPC
 */
public class Field implements Serializable {
    private Set<Property> properties = new HashSet<>();
    private GameAction action;
    private boolean interactive= false;

    public Field(GameAction a){
        this.action = a;
    }
    
    public Set<Property> getProperties() {
        return properties;
    }

    public void setProperties(Set<Property> properties) {
        interactive = true;
        this.properties = properties;
    }

    public GameAction getAction() {
        return action;
    }
    
    public boolean isInteractive() {
        return interactive;
    }

    public void setInteractive(boolean interactive) {
        this.interactive = interactive;
    }
    
    
   
    
    
    
    
    
    
}
