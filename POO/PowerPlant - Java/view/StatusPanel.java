package view;

import console.FieldView;
import console.View;
import isel.leic.pg.Console;

public class StatusPanel extends View {
    public static final int HEIGHT = 7, WIDTH = 6;
    private FieldView level = new FieldView("Level",1,0,"---");
    private FieldView moves = new FieldView("Moves",4,0,"---");

    public StatusPanel(int left) {
        super(0,left, HEIGHT, WIDTH, Console.BLACK);
        addView(level);
        addView(moves);
    }
    public void paint() {
        clear();
        level.paint();
        moves.paint();
    }
    public void setLevel(int level) { this.level.setValue(level); }
    public void setMoves(int moves) { this.moves.setValue(moves); }
}
