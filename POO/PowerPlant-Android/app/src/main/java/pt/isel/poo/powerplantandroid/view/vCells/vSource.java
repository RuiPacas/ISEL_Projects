package pt.isel.poo.powerplantandroid.view.vCells;

import android.content.Context;
import android.graphics.Canvas;

import pt.isel.poo.powerplantandroid.R;
import pt.isel.poo.powerplantandroid.model.Cell;
import pt.isel.poo.powerplantandroid.view.CellTile;
import pt.isel.poo.powerplantandroid.view.Img;

public class vSource extends CellTile {
    private static Img img = null;


    public vSource(Context ctx,Cell cell) {
        super(cell);
        if (img == null) img = new Img(ctx, R.drawable.power); }


    @Override
    public void draw(Canvas canvas, int side) {
        super.draw(canvas,side);
        getImg().draw(canvas,side,side,p);
    }


    protected Img getImg() { return img;  }
}