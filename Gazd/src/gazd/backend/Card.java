/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.backend;

/**
 *
 * @author MetaPC
 */
class Card {
    
    private IGameAction action;
    private String message;

    public Card(IGameAction action, String message) {
        this.action = action;
        this.message = message;
    }

    public IGameAction getAction() {
        return action;
    }

    public void setAction(IGameAction action) {
        this.action = action;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    
    
    
}
