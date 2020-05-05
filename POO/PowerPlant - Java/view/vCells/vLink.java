package view.vCells;

import isel.leic.pg.Console;
import model.Cell;
import model.ConnectedCells;
import view.CellTile;

public class vLink extends CellTile {
    public vLink(Cell cell) {
        super(cell);
    }

    @Override
    public void paint() {
        super.paint();
        Console.setBackground(cell.isCompleted() ? Console.YELLOW : Console.BLACK);
        Console.setForeground(cell.isCompleted() ? Console.BLACK : Console.WHITE);
        print(1, 1, 'o');
    }
}
