/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.backend.domain;

import gazd.backend.domain.component.Property;
import java.util.HashSet;
import java.util.Set;
import gazd.controller.action.GameAction;

/**
 *
 * @author MetaPC
 */
public class Field {
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
