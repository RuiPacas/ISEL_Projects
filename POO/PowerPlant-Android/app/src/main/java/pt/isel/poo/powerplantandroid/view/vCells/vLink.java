package pt.isel.poo.powerplantandroid.view.vCells;

import android.content.Context;
import android.graphics.Canvas;

import pt.isel.poo.powerplantandroid.model.Cell;
import pt.isel.poo.powerplantandroid.view.CellTile;

public class vLink extends CellTile {

    public vLink(Cell cell) {
        super(cell);
    }

    @Override
    public void draw(Canvas canvas, int side) {
        super.draw(canvas,side);
    }

    @Override
    public boolean setSelect(boolean selected) {
        return false;
    }
}
