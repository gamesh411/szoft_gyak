/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package gazd.backend.domain.component;

/**
 *
 * @author MetaPC
 */
public enum Property {
    HOUSE(30000),CAR(1000),KITCHEN(3000),LIVING(6000),HOUSEHOLD(800);
    
    private int price;

    private Property(int price) {
        this.price = price;
    }

    public int getPrice() {
        return price;
    }
 
}
