package pt.isel.poo.powerplantandroid.model.Cells;


import pt.isel.poo.powerplantandroid.model.Cell;
import pt.isel.poo.powerplantandroid.model.Direction;

public class Space extends Cell {
    @Override
    public boolean isCompleted() {
        return true;
    }

    @Override
    public void resetPower() {
        //nothing
    }

    @Override
    public void connect() { }

    @Override
    public boolean rotate() {
        return false;
    }

    @Override
    public boolean canLink(Direction d) {
        return false;
    }

    @Override
    public String getName() {
        return "Space";
    }
}
