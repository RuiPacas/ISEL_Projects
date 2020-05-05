package view;


import isel.leic.pg.Console;
import model.Cell;
import console.tile.Tile;
import model.Cells.*;
import model.Direction;
import view.vCells.*;


public abstract class CellTile extends Tile {
    public static final int SIDE = 3;
    protected Cell cell;

    protected CellTile(Cell cell) {
        this.cell = cell;
        setSize(SIDE, SIDE);
    }


    public static Tile newInstance(Cell cell) {
        if (cell instanceof House) return new vHouse(cell);
        if (cell instanceof Source) return new vSource(cell);
        if (cell instanceof Space) return new vSpace(cell);
        return new vLink(cell);
    }

    @Override
    public void paint() {
        Console.setBackground(cell.isCompleted()? Console.YELLOW : Console.BLACK);
        Console.setForeground(cell.isCompleted()? Console.BLACK : Console.WHITE);
        if (cell.canLink(Direction.UP)) {
            print(0, 1, '|');
        }
        if (cell.canLink(Direction.DOWN)) {
            print(2, 1, '|');
        }
        if (cell.canLink(Direction.RIGHT)) {
            print(1, 2, '-');
        }
        if (cell.canLink(Direction.LEFT)) {
            print(1, 0, '-');
        }
    }

    @Override
    public void repaint() {
        clear();
        paint();
    }
}
