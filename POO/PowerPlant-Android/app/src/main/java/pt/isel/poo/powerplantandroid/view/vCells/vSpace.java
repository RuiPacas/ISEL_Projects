package pt.isel.poo.powerplantandroid.view.vCells;
import android.graphics.Canvas;

import pt.isel.poo.powerplantandroid.model.Cell;
import pt.isel.poo.powerplantandroid.view.CellTile;

public class vSpace extends CellTile {


    public vSpace(Cell cell) { super(cell); }

    @Override
    public void draw(Canvas canvas, int side) {
    }

    @Override
    public boolean setSelect(boolean selected) {
        return false;
    }
}
