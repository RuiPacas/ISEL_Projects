package pt.isel.poo.powerplantandroid.model;

import java.util.LinkedList;

public class Plant {
    private Cell[][] map;
    private int moves;

    private LinkedList<Cell> sources = new LinkedList<>();

    public Plant(int height, int width)
    {
        Cell.model= this;
        map = new Cell[height][width];
    }

    public void putCell(int l, int c, Cell cell) {
        map[l][c] = cell;
        cell.setCords(l,c);
        if(cell.isSource()){
            sources.add(cell);
        }
    }

    public void init() {
        spreadPower();
        moves = 0;
    }

    public int getHeight() {
        return map.length;
    }

    public int getWidth() {
        return map[0].length;
    }

    public Cell getCell(int l, int c) {
        if(l<0 || l>=getHeight() || c<0 || c>=getWidth()) return null;
        return map[l][c];
    }

    public int getMoves() {
        return moves;
    }

    public boolean isCompleted() {
        for (int i = 0; i < getHeight(); i++)
            for (int j = 0; j < getWidth(); j++)
                if (!map[i][j].isCompleted())
                    return false;
        return true;
    }

    private Listener l;

    public void setListener(Listener listener) {
        l = listener;
    }

    public void notifyChanged(Cell c){
        if(l!=null)
            l.cellChanged(c.line,c.col,c);
    }

    public boolean touch(int line, int col) {
        if(map[line][col].rotate()){
            spreadPower();
            ++moves;
            return true;
        }
        return false;
    }

    private void spreadPower() {
        clearPower();
        for(Cell c : sources){
            c.connect();
        }
    }

    private void clearPower() {
        for(int i = 0; i<map.length;++i)
            for (int j = 0; j < map[0].length ; j++) {
                map[i][j].resetPower();
                notifyChanged(map[i][j]);
            }

    }

    public interface Listener {
        void cellChanged(int lin, int col, Cell cell);
    }

}
