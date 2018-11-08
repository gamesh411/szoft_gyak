/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package hu.elte.gazdapp.frontend.util;

import java.awt.Point;

/**
 *
 * @author MetaPC
 */
public final class DrawHelper {

    public static Point getPoint(int width, int height, int position, int plus) {
        int diffToOtherPieces = plus * 10;
        if (position == Position.RIGHT_BOTTOM.getValue()) {
            return new Point(12 * width / 13, 8 * height / 9 + diffToOtherPieces);
        }
        if (position == Position.LEFT_BOTTOM.getValue()) {
            return new Point(width / 17, 8 * height / 9 + diffToOtherPieces);
        }
        if (position == Position.LEFT_TOP.getValue()) {
            return new Point(width / 17, height / 11 + diffToOtherPieces);
        }
        if (position == Position.RIGHT_TOP.getValue()) {
            return new Point(12 * width / 13, height / 11 + diffToOtherPieces);
        }
        if (position < Position.LEFT_BOTTOM.getValue()) {
            int twidth = width / 4;
            twidth = width - twidth / 2 - position * width / 17;
            return new Point(twidth, 7 * height / 8 + diffToOtherPieces);
        } else if (position < Position.LEFT_TOP.getValue()) {
            int tpos = position - 14;
            int theight = (int) (height / 2);
            theight = height - theight / 2 - tpos * height / 11;
            return new Point(width / 15 + diffToOtherPieces, theight);
        } else if (position < Position.RIGHT_TOP.getValue()) {
            int tpos = 13 - (position - 21);
            int twidth = width / 4;
            twidth = width - twidth / 2 - tpos * width / 17;
            return new Point(twidth, height / 9 + diffToOtherPieces);
        } else {
            int tpos = 6 - (position - 35);
            int theight = (int) (height / 2);
            theight = height - theight / 2 - tpos * height / 11;
            return new Point(14 * width / 15 + diffToOtherPieces, theight);
        }
    }

    private DrawHelper() {

    }

    private enum Position {
        RIGHT_BOTTOM(0),
        LEFT_BOTTOM(13),
        LEFT_TOP(21),
        RIGHT_TOP(34);

        private int posValue;

        Position(int posValue) {
            this.posValue = posValue;
        }

        private int getValue() {
            return this.posValue;
        }
    }
}
