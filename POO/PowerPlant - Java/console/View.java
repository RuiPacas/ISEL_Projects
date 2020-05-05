package console;

import isel.leic.pg.Console;

/**
 * Base class of an element displayed in the window.<br/>
 * One View can be contained in another View.<br/><br/>
 * A concrete view (derived from View) must implement the {@link #paint()} method.<br/><br/>
 * Each view has a coordinate (its upper left corner), a dimension (number of rows and columns) and its background color.<br/>
 * If the view is contained in another, the coordinates are relative to it.<br/>
 */
public class View {
    protected int top, left, height, width;
    protected View parent = null;  // The parent view (null if is a root View)
    protected int bkColor;

    /**
     * Create a view with all its features.
     * @param top the line (in Window or parent View) of upper left corner
     * @param left the column (in Window or parent View) of upper left corner
     * @param height number of lines of the view
     * @param width number of columns of thre view
     * @param bkColor the background color
     */
    public View(int top, int left, int height, int width, int bkColor) {
        this(top,left,bkColor);
        setSize(height,width);
    }

    /**
     * Create a view without dimension.<br>
     * The {@link #setSize(int,int)} method should be called later.
     * @param top the line (in Window or parent View) of upper left corner
     * @param left the column (in Window or parent View) of upper left corner
     * @param bkColor the background color
     */
    public View(int top, int left, int bkColor) {
        setOrig(top,left);
        this.bkColor = bkColor;
    }

    /**
     * Create a view in (0,0) position and without dimension.<br>
     * The {@link #setSize(int,int)} and {@link #setOrig(int,int)} methods should be called later.<br>
     *
     */
    public View() { this(0,0, Console.GRAY); }

    /**
     * Sets the coordinates of the upper left corner of the view.
     * @param top the line (in Window or parent View) of upper left corner
     * @param left the column (in Window or parent View) of upper left corner
     */
    public void setOrig(int top, int left) { this.top=top; this.left=left; }

    /**
     * Sets the dimension of the view.
     * @param height number of lines of the view
     * @param width number of columns of thre view
     */
    public void setSize(int height, int width) { this.height=height; this.width=width; }

    /**
     * Appends a view as child.<br>
     * This operation currently only changes the parent field of the child view.
     * @param v The child view
     */
    public void addView(View v) { v.parent=this; }

    /**
     * Clears area and call {@link #paint()}.
     */
    public void repaint() {
        clear();
        paint();
    }

    /**
     * Writes the contents of the View.
     * Must be override by extended classes.
     * Implementations of this method must call the {@link #cursor(int,int)} or {@link #print(int,int,char)} methods
     * inherited from View to write the content.
     */
    public void paint() { };

    /**
     * Clears the entire View area.
     */
    public void clear() { printBox(0,0,height,width,bkColor); }

    /**
     * paint a box inside the View
     * @param top line of upper left corner of box
     * @param left column of upper left corner of box
     * @param height number of lines of box
     * @param width number of columns of box
     * @param bkColor color of box
     */
    public void printBox(int top, int left, int height, int width, int bkColor) {
        Console.setBackground(bkColor);
        for (int l = 0; l < height ; l++)
            for (int c = 0; c < width; c++) print(top+l,left+c,' ');
    }

    /**
     * Position the cursor inside the view, using relative coordinates.<br>
     * If the view is child of another the positioning is performed by the parent view (to add with its coordinates)
     * @param lin the relative line
     * @param col the relative column
     * @return true if position is inside the view.
     */
    protected boolean cursor(int lin, int col) {
        int l = lin+top, c = col+left;
        if (l<0 || l>=top+height || c<0 || c>=left+width) return false;
        if (parent!=null) parent.cursor(l,c);
        else Console.cursor(l,c);
        return true;
    }

    /**
     * Print a char inside the view, using relative coordinates.<br>
     * @param lin the relative line
     * @param col the relative column
     * @param c the char to print
     */
    public void print(int lin, int col, char c) {
        if (cursor(lin, col))
            Console.print(c);
    }
    /**
     * Print a text inside the view, using relative coordinates.<br>
     * @param lin the relative line
     * @param col the relative column
     * @param s the text to print
     */
    public void print(int lin, int col, String s) {
        if (cursor(lin, col))
            Console.print(s);
    }

    /**
     * Center a View in area.<br/>
     * Changes the coordinates of the View (top,left) to be centered.
     * @param height of area
     * @param width of area
     */
    public void center(int height, int width) {
        setOrig( (height-this.height)/2, (width-this.width)/2 );
    }

    /**
     * Checks if there is overlap with the given view
     * @param other The other view
     * @return true if the views are overlapped
     */
    public boolean isOver(View other) {
        return (top >= other.top && top <= other.top + other.height || other.top >= top && other.top <= top + height)
          && (left >= other.left && left <= other.left + other.width || other.left >= left && other.left <= left + width);
    }
}
