package model.Cells;

public class RestrictLine extends Line {
    private static final char LETTER = 'R';
    private int movements = 2;

    public static boolean isLetter(char c) {
        return c == LETTER;
    }

    @Override
    public boolean rotate() {
        if (movements != 0) {
            --movements;
            return super.rotate();
        }
        return false;
    }

    @Override
    public String getName() {
        return "RestricLine";
    }

    public int getMovements() {
        return movements;
    }
}
