/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.backend.domain.component;

/**
 *
 * @author mmeta
 */
public enum Piece {
    RED("piros"), GREEN("zöld"), BLUE("kék"), YELLOW("sárga"), PURPLE("lila");

    private String huColor;

    private Piece(String huColor) {
        this.huColor = huColor;
    }

    @Override
    public String toString() {
        return huColor;
    }
}
