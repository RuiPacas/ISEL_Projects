package model;

public enum Direction {
    UP(-1, 0), RIGHT(0, +1), DOWN(+1, 0), LEFT(0, -1);

    public final int deltaLine, deltaCol;

    private Direction(int dl, int dc) {
        deltaLine = dl;
        deltaCol = dc;
    }

    public static Direction random() {
        return values()[(int) (Math.random() * 4)];
    }

    public Direction next() {
        return add(1);
    }

    public Direction prev() {
        return add(3);
    }

    public Direction opposite() {
        return add(2);
    }

    private Direction add(int n) {
        return values()[(ordinal() + n) % 4];
    }
}
