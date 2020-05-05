package model.Cells;


import java.io.PrintWriter;

import model.Cell;
import model.Direction;

public class Space extends Cell {
    private static final char LETTER = '.';

    public static boolean isLetter(char c) {
        return c == LETTER;
    }

    @Override
    public boolean isCompleted() {
        return true;
    }


    @Override
    public void setDir(Direction dir) {
    }

    @Override
    public void resetPower() {
        //nothing
    }

    @Override
    public void connect() {
    }

    @Override
    public Direction getDir() {
        return null;
    }

    @Override
    public boolean rotate() {
        return false;
    }


    @Override
    public boolean canLink(Direction d) {
        return false;
    }

    @Override
    public String getName() {
        return "Space";
    }
}
