package model.Cells;

import model.Direction;

public class MultiSource extends Source {
    public MultiSource() {
        super();
    }

    private static final char LETTER = 'M';

    public static boolean isLetter(char c) {
        return c == LETTER;
    }

    @Override
    public boolean canLink(Direction d) {
        return (dir == d || dir == d.next());
    }


    @Override
    public void connect() {
        for (Direction d : Direction.values())
            if (canLink(d))
                distributePower(d);

    }

    @Override
    public String getName() {
        return "MultiSource";
    }
}



