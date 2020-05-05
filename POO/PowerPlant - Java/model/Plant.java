package model;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.LinkedList;
import java.util.Scanner;

import model.Cells.Space;

public class Plant {
    private Cell[][] map;
    private int moves;

    private LinkedList<Cell> sources = new LinkedList<>();

    public Plant(int width, int height) {
        map = new Cell[width][height];
    }

    public void putCell(int l, int c, Cell cell) {
        map[l][c] = cell;
        cell.setCords(l, c);
        cell.setModel(this);
        if (cell.isSource()) {
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
        if (l < 0 || l >= getHeight() || c < 0 || c >= getWidth()) return null;
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

    public void notifyChanged(Cell c) {
        if (l != null)
            l.cellChanged(c.line, c.col);
    }

    public boolean touch(int line, int col) {
        if (map[line][col].rotate()) {
            spreadPower();
            ++moves;
            return true;
        }
        return false;
    }

    private void spreadPower() {
        clearPower();
        for (Cell c : sources) {
            c.connect();
        }
    }

    private void clearPower() {
        for (int i = 0; i < getHeight(); ++i)
            for (int j = 0; j < getWidth(); j++) {
                map[i][j].resetPower();
                notifyChanged(map[i][j]);
            }
    }

    public interface Listener {
        void cellChanged(int lin, int col);
    }

    public void save(PrintWriter out) {
        Cell c;
        for (int i = 0; i < getHeight(); ++i)
            for (int j = 0; j < getWidth(); j++) {
                c = getCell(i, j);
                out.print(c.getDir() + " ");
            }
    }

    public void load(Scanner in) {
        for (int i = 0; i < getHeight(); ++i)
            for (int j = 0; j < getWidth(); j++) {
                String s = in.next();
                if (!s.equals("null")) {
                    Direction dir = Direction.valueOf(s);
                    map[i][j].setDir(dir);
                }
            }
        spreadPower();
    }
}
