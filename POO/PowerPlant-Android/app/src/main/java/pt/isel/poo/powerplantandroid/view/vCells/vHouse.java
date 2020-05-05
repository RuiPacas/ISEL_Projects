package pt.isel.poo.powerplantandroid.view.vCells;

import android.content.Context;
import android.graphics.Canvas;

import pt.isel.poo.powerplantandroid.R;
import pt.isel.poo.powerplantandroid.model.Cell;
import pt.isel.poo.powerplantandroid.view.CellTile;
import pt.isel.poo.powerplantandroid.view.Img;

public class vHouse extends CellTile {
    private static Img img = null;
    Context ctx;


    public vHouse(Context ctx, Cell cell) {
        super(cell);
        if (img == null) img = new Img(ctx, cell.isCompleted()?R.drawable.house_on:R.drawable.house_off);}

    //TODO : MUDAR IMAGEM

    @Override
    public void draw(Canvas canvas, int side) {
        super.draw(canvas, side);
        img.draw(canvas,side,side,p);

    }
}
