package pt.isel.poo.powerplantandroid.model.Cells;


import pt.isel.poo.powerplantandroid.model.ConnectedCells;
import pt.isel.poo.powerplantandroid.model.Direction;

public class Source extends ConnectedCells {
 public Source(){
     this.power=true;
 }

    @Override
    public boolean canLink(Direction d) {
        return dir==d ;
    }

    @Override
    public boolean isSource() {
        return true;
    }

    @Override
    public void resetPower() {
        //Nothing
    }

    @Override
    public void connect() {
     distributePower(dir);
    }

    @Override
    public String getName() {
        return "Source";
    }
}
