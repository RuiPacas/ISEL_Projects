package pt.isel.poo.powerplantandroid.model;

import pt.isel.poo.powerplantandroid.model.Cells.*;

public abstract class Cell {
    protected static Plant model;
    protected int line,col ;

    public void setCords(int line,int col){
        this.line=line;
        this.col= col;
    }

    public abstract boolean isCompleted();
    public abstract boolean rotate();

    public static Cell newInstance(char type) {
        switch (type) {
            case 'P':
                return new Source();
            case 'H':
                return new House();
            case '-':
                return new Line();
            case 'c':
                return new Curve();
            case 'T':
                return new Branch();
            case '.':
                return new Space();

        }
        return null;
    }

    public abstract boolean canLink(Direction d);

    public  boolean isSource(){
        return false;
    }

    public abstract void resetPower();

    public String getName(){
        return "Link";
    }

    public abstract void connect();
}
