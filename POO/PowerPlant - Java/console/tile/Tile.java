package console.tile;

import console.View;

/**
 * Base class hierarchy of Tiles
 */
public abstract class Tile extends View {

    public void setBackground(int color) { bkColor = color; }
    @Override
    public void paint() { }
}
