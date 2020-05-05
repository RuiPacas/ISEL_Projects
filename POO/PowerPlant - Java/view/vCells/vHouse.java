package view.vCells;

import isel.leic.pg.Console;
import model.Cell;
import view.CellTile;

public class vHouse extends CellTile {
    public vHouse(Cell cell) {
        super(cell);
    }

    @Override
    public void paint() {
        super.paint();
        Console.setBackground(cell.isCompleted() ? Console.YELLOW : Console.RED);
        Console.setForeground(cell.isCompleted() ? Console.RED : Console.WHITE);
        print(1, 1, 'H');
        Console.setBackground(Console.BLACK);

    }
}
