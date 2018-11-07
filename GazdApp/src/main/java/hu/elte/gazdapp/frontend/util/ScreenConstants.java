/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.frontend.util;

import java.awt.Toolkit;

/**
 *
 * @author <Andó Sándor Zsolt>
 */
public enum ScreenConstants {
    SCREEN_WIDTH((int) Toolkit.getDefaultToolkit().getScreenSize().getWidth()),
    SCREEN_HEIGHT((int) Toolkit.getDefaultToolkit().getScreenSize().getHeight()),
    PURCHASE_WINDOW_WIDTH((int) (SCREEN_WIDTH.getValue() / 2.2)),
    PURCHASE_WINDOW_HEIGHT((int) (SCREEN_HEIGHT.getValue() / 3)),
    PROPERTY_WINDOW_WIDTH((int) (SCREEN_WIDTH.getValue() / 4.4)),
    PROPERTY_WINDOW_HEIGHT((int) (SCREEN_HEIGHT.getValue() / 4)),
    FONT_SIZE((int) (SCREEN_WIDTH.getValue() / 100)),
    ROW_HEIGHT(FONT_SIZE.getValue() + 10),
    BOARD_PANEL_HEIGHT(SCREEN_HEIGHT.getValue() - 300);

    private final int value;

    private ScreenConstants(int value) {
        this.value = value;
    }

    public int getValue() {
        return value;
    }
}
