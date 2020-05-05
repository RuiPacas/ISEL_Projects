package view.vCells;

import isel.leic.pg.Console;
import model.Cell;
import view.CellTile;

public class vSource extends CellTile {
    public vSource(Cell cell) {
        super(cell);
    }

    @Override
    public void paint() {
        super.paint();
        Console.setBackground(Console.YELLOW);
        Console.setForeground(Console.BROWN);
        print(1, 1, 'P');
        Console.setForeground(Console.BLACK);

    }
}
