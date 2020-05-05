package console;

import isel.leic.pg.Console;
import static isel.leic.pg.Console.*;

/**
 * The Window object represents the single Console window.<br/>
 * The window opens when an object is created.<br/>
 * The Title and dimension of the window are indicated as arguments in the construction.<br/>
 * When another Window object is created, the previous window is closed automatically.<br/>
 *<br/>
 * This window has a message bar at bottom (extra line of window).<br/>
 * The {@link #message(String)} method and the {@link #question(String)} method use the message bar.<br/>
 * The {@link #clear()} method deletes the entire contents of the window. <br/>
 * The {@link #close()} method closes the window. <br/>
 */

public class Window {
    //+UML: <<handle>> View
    private final int height, width;
    private static Window instance = null;

    /**
     * Creates and open the window console.<br/>
     * Turns on the detection of mouse events.
     * @param title of window
     * @param height in lines (excluding the message bar)
     * @param width in columns
     */
    public Window(String title, int height, int width) {
        if (instance!=null)
            Console.close();
        this.height = height;
        this.width = width;
        instance = this;
        Console.open(title,height+1, width);
        clear(height, GRAY, width);
        Console.enableMouseEvents(false);
    }

    /**
     * Write the text in position and with the colors indicated.
     * @param line number of line in window (0..height-1)
     * @param col number of column in window (0..widht-1)
     * @param text to write
     * @param foreground color
     * @param background color
     */
    public void write(int line, int col, String text, int foreground, int background) {
        cursor(line,col);
        color(foreground,background);
        print(text);
    }

    /**
     * Clears the entire window area.
     */
    public void clear() {
        setBackground(BLACK);
        cursor(0,0);
        for (int i = 0; i < height*width; i++) print(' ');
    }

    /**
     * Close the window.
     */
    public void close() {
        if (instance!=null) Console.close();
        instance = null;
    }

    /**
     * Keep text in the message bar for 3 seconds or until a key is pressed.
     * @param txt to write in message bar
     */
    public void message(String txt) {
        clear(height, GRAY, width);
        write(height,0,txt,YELLOW,GRAY);
        disableMouseEvents();
        clearAllChars();
        int key = waitKeyPressed(3000);
        clear(height, GRAY,txt.length());
        if (key>=0) waitKeyReleased(key);
        enableMouseEvents(false);
    }

    /**
     * Ask a question with yes or no and wait for the answer.<br/>
     * Returns the response given by the user.
     * @param txt Question text, without "? (Y/N)"
     * @return true if user press Y or S.
     */
    public boolean question(String txt) {
        clear(height, GRAY, width);
        write(height,0,txt+"? (Y/N)",YELLOW,GRAY);
        disableMouseEvents();
        clearAllChars();
        cursor(true);
        int key;
        do {
            while ((key = waitKeyPressed(0)) < 0) ;
            waitKeyReleased(key);
            if (key>='a' && key<='z') key += 'A'-'a';
        } while(key!='S' && key!='Y' & key!='N');
        clear(height, GRAY, width);
        cursor(false);
        enableMouseEvents(false);
        return key!='N';
    }

    private void clear(int line, int background, int size) {
        cursor(line, 0);
        setBackground(background);
        for (int i = 0; i < size; i++) print(' ');
    }

    // Test code using Window and View classes

    public static void main(String[] args) {
        Window win = new Window("Test",20,30);
        win.message("Message bar");
        win.write(5,20,"Hello",WHITE,BROWN);

        CounterView ctr = new CounterView();
        count(ctr,-2);
        win.clear();

        View panel = new View(0,0,7,12,RED);
        panel.center(20,30);
        panel.repaint();
        panel.addView(ctr);
        count(ctr,+2);

        win.write(6,4,"Press any key",BLACK,YELLOW);
        while( ! win.question("Close window") )
            Console.waitKeyPressed(5000);
        win.close();
    }

    static class CounterView extends View {
        private int value = 20;
        private CounterView() { super(2,2,3,8,GREEN); repaint(); }
        @Override public void paint() { print(1,1,"val="+value); }
    };

    private static void count(CounterView cv, int delta) {
        for (int i = 0; i < 10; i++) {
            Console.sleep(1000);
            cv.value += delta;
            cv.repaint();
        }
    }
}
