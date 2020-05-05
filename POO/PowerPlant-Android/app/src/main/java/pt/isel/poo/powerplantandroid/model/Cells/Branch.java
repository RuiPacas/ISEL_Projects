package pt.isel.poo.powerplantandroid.model.Cells;


import pt.isel.poo.powerplantandroid.model.ConnectedCells;
import pt.isel.poo.powerplantandroid.model.Direction;

public class Branch extends ConnectedCells {


    @Override
    public boolean canLink(Direction d) {
        return d!=dir.opposite();
    }


}
