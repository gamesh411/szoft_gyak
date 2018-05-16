/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.backend;

import java.util.EnumSet;
import java.util.HashSet;
import java.util.Set;

/**
 *
 * @author MetaPC
 */
public class Field {
    Set<Property> properties = new HashSet<>();
    IGameAction action;
    boolean interactive= false;

    Field(IGameAction a){
        this.action = a;
    }
    
    public Set<Property> getProperties() {
        return properties;
    }

    public void setProperties(Set<Property> properties) {
        interactive = true;
        this.properties = properties;
    }

    public IGameAction getAction() {
        return action;
    }
    
    public boolean isInteractive() {
        return interactive;
    }

    public void setInteractive(boolean interactive) {
        this.interactive = interactive;
    }
    
    
   
    
    
    
    
    
    
}
