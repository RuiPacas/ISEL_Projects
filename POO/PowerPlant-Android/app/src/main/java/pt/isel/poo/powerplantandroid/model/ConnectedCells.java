package pt.isel.poo.powerplantandroid.model;

public abstract class ConnectedCells extends Cell {

    protected boolean power = false;
    protected Direction dir= Direction.random();

    @Override
    public boolean isCompleted() {
        return power;
    }

    public boolean rotate(){
        dir=dir.next();
        return true;
    }

    @Override
    public void connect() {
        if(power) return;
        setPower();
        for(Direction d : Direction.values())
            if(canLink(d))
                distributePower(d);

    }

    protected void setPower(){
        power =true;
        model.notifyChanged(this);
    }

    protected void distributePower(Direction d) {
        Cell c= model.getCell(line+d.deltaLine,col + d.deltaCol);
        if(c!=null && c.canLink(d.opposite()))
            c.connect();
    }

    @Override
    public void resetPower() {
        power=false;
    }
}
