/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.backend.domain.component;

/**
 *
 * @author MetaPC
 */
public enum Property {
    HOUSE(30000, "Ház"),CAR(1000, "Kisautó"),KITCHEN(3000, "Konyhabútor"),LIVING(6000, "Szobabútor"),HOUSEHOLD(800, "Háztartási gépek"),INSURANCE(350, "Biztosítás");
    
    private int price;
    private String huDesc;

    private Property(int price, String huDesc) {
        this.price = price;
        this.huDesc = huDesc;
    }

    public int getPrice() {
        return price;
    }

    @Override
    public String toString() {
        return huDesc;
    }
 
}
