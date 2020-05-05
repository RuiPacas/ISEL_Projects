package model.Cells;


import java.io.PrintWriter;

import model.ConnectedCells;
import model.Direction;

public class Source extends ConnectedCells {
    public Source() {
        this.power = true;
    }

    private static final char LETTER = 'P';

    public static boolean isLetter(char c) {
        return c == LETTER;
    }


    @Override
    public boolean canLink(Direction d) {
        return dir == d;
    }

    @Override
    public boolean isSource() {
        return true;
    }

    @Override
    public void resetPower() {
        //Nothing
    }

    @Override
    public void connect() {
        distributePower(dir);
    }

    @Override
    public String getName() {
        return "Source";
    }
}
