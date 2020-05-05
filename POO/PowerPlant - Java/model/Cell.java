package model;


import java.io.PrintWriter;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;

import model.Cells.*;

public abstract class Cell {
    protected Plant model;
    protected int line, col;
    private static Class[] cells = {Source.class, House.class, Line.class, Curve.class, Branch.class, Space.class, MultiSource.class, RestrictLine.class};

    public void setCords(int line, int col) {
        this.line = line;
        this.col = col;
    }

    public abstract boolean isCompleted();

    public abstract boolean rotate();

    public static Cell newInstance(char type) {
        try {
            for (Class c : cells)
                if ((boolean) c.getMethod("isLetter", char.class).invoke(null, type))
                    return (Cell) c.getConstructor().newInstance();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | InstantiationException e) {
            e.printStackTrace();
        }
        return null;
    }

    public void setModel(Plant model) {
        this.model = model;
    }

    public abstract boolean canLink(Direction d);

    public boolean isSource() {
        return false;
    }

    public abstract void resetPower();

    public String getName() {
        return "Link";
    }

    public abstract void connect();

    public abstract Direction getDir();

    public abstract void setDir(Direction dir);
}
