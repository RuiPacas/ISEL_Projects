package pt.isel.poo.powerplantandroid.view;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.os.Bundle;

import java.lang.reflect.Constructor;

import pt.isel.poo.powerplantandroid.model.Cell;
import pt.isel.poo.powerplantandroid.model.Direction;
import pt.isel.poo.powerplantandroid.tile.Tile;

public abstract class CellTile implements Tile {
    protected Cell cell;
    protected static Paint p = new Paint();
    public static final int SIDE = 3;

    public CellTile(Cell cell) {
        this.cell = cell;
        p.setStyle(Paint.Style.FILL);
        p.setStrokeCap(Paint.Cap.ROUND);
        p.setStrokeWidth(20);
    }

    @Override
    public void draw(Canvas canvas, int side) {
        int a = side / 2;
        p.setColor(cell.isCompleted() ? Color.GREEN : Color.BLACK);
        if (cell.canLink(Direction.UP)) {
            canvas.drawLine(a, 0, a, a, p);
        }
        if (cell.canLink(Direction.DOWN)) {
            canvas.drawLine(a, side, a, a, p);
        }
        if (cell.canLink(Direction.RIGHT)) {
            canvas.drawLine(side, a, a, a, p);
        }
        if (cell.canLink(Direction.LEFT)) {
            canvas.drawLine(0, a, a, a, p);
        }
    }

    public static CellTile newInstance(Cell cell) {
        CellTile tv=null;
        try {
            Class<?> cls = Class.forName("v"+cell.getName());
            Constructor c = cls.getConstructor(Cell.class);
            tv = (CellTile) c.newInstance(cell);
        } catch (Exception e) {
            System.out.println(e);
            }
        return tv;
    }

    @Override
    public boolean setSelect(boolean selected) {
        return false;
    }
}
