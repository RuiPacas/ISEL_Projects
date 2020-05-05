package pt.isel.poo.powerplantandroid.model.Cells;


import pt.isel.poo.powerplantandroid.model.ConnectedCells;
import pt.isel.poo.powerplantandroid.model.Direction;

public class House extends ConnectedCells {


    @Override
    public boolean canLink(Direction d) {
        return(dir==d);
    }

    @Override
    public void connect() {
            setPower();
    }

    @Override
    public String getName() {
        return "House";
    }
}
