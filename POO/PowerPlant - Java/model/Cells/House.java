package model.Cells;


import java.io.PrintWriter;

import model.ConnectedCells;
import model.Direction;

public class House extends ConnectedCells {
    private static final char LETTER = 'H';

    public static boolean isLetter(char c) {
        return c == LETTER;
    }


    @Override
    public boolean canLink(Direction d) {
        return (dir == d);
    }

    @Override
    public void connect() {
        setPower();
    }

    @Override
    public String getName() {
        return "House";
    }
}
