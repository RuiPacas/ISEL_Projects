package model.Cells;


import java.io.PrintWriter;

import model.ConnectedCells;
import model.Direction;

public class Branch extends ConnectedCells {
    private static final char LETTER = 'T';


    public static boolean isLetter(char c) {
        return c == LETTER;
    }

    @Override
    public boolean canLink(Direction d) {
        return d != dir.opposite();
    }


}
