import isel.leic.pg.Console;

/**
 * This class is responsible for the presentation of the game.
 * Probably, it is not necessary to change this class.
 */
public class Panel {

    // General dimensions
    private static final int
            TILE_WIDTH = 8, TILE_HEIGHT = 5,                // Dimensions of each tile
            STATUS_WIDTH = 19,                              // Width of the right side of Panel
            GRID_LINES = Game2048.LINES * (TILE_HEIGHT + 1) + 1,
            GRID_COLS = Game2048.COLS * (TILE_WIDTH + 1) + 1,
            LINES = GRID_LINES + 1,                         // Panel rows
            COLS = GRID_COLS + STATUS_WIDTH;                // Panel columns

    // Colors used for each tile in grid
    private static final int[] COLORS = {
            Console.WHITE, Console.LIGHT_GRAY, Console.GRAY,
            Console.GREEN, Console.BLUE, Console.CYAN,
            Console.PINK, Console.ORANGE, Console.BROWN,
            Console.MAGENTA, Console.YELLOW
    };

    // Keys menu text
    private static final String[] KEYS = {
            "Q - Quit", "N - New game", "<= - Undo", "Cursor - To play", "--------------", "A - Animation"
    };

    // Text of persistent message in bottom line.
    private static String messageTxt = null;


    //Optional - Color when activate
    public static void colorOn() {
        if (Game2048.toggle) {
            Console.color(Console.WHITE, Console.GREEN);
            int line = LINES - KEYS.length - 2;
            Console.cursor(line + 5, STATUS_LEFT);
            Console.print(KEYS[5]);
        }
    }
    public static void colorOff(){
        if (!Game2048.toggle){
            Console.color(Console.WHITE, Console.BLACK);
            int line = LINES - KEYS.length - 2;
            Console.cursor(line + 5, STATUS_LEFT);
            Console.print(KEYS[5]);

        }
    }

    /**
     * Open the Console and draw the entire panel.
     */
    public static void open() {
        Console.fontSize(25);
        Console.scaleFactor(1.0, 0.6);
        Console.open("PG 2048", LINES, COLS);
        //Console.exit(true);  // Enable exit console. CAUTION: Force Abnormal Termination
        drawGrid();
        drawTitles();
        drawKeys();
        message("");
    }

    /**
     * Close the Console.
     */
    public static void close() {
        Console.close();
    }

    /**
     * Draws a tile in a particular position on the grid
     *
     * @param l - line in grid (0 .. Game2048.LINES-1)
     * @param c - column in grid (0 .. Game2048.COLS-1)
     * @param n - number to present and background color of tile (Game2048.MIN_VALUE .. Game2048.MAX_VALUE)
     */
    public static void showTile(int l, int c, int n) {
        int idxColor=0;
        for (int value=n; value > Game2048.MIN_VALUE && idxColor < COLORS.length - 1; value >>= 1)
            idxColor++;
        printTile(l, c, n, COLORS[idxColor]);
    }

    /**
     * Hide a tile in a particular position on the grid
     *
     * @param l - line in grid (0 .. Game2048.LINES-1)
     * @param c - column in grid (0 .. Game2048.COLS-1)
     */
    public static void hideTile(int l, int c) {
        printTile(l, c, 0, Console.BLACK);
    }

    // Print a tile. Used by showTile() and hideTile()
    private static void printTile(int l, int c, int n, int backColor) {
        l = l * (TILE_HEIGHT + 1) + 1;      // line in Console
        c = c * (TILE_WIDTH + 1) + 1;       // Column in Console
        Console.color(Console.BLACK, backColor);
        for (int i = 0; i < TILE_HEIGHT; i++) {
            Console.cursor(l + i, c);
            if (i == TILE_HEIGHT / 2 && n > 0)
                printCentered("" + n, TILE_WIDTH);
            else
                printSpaces(TILE_WIDTH);
        }
    }

    // Draw grid around tiles. Only called by open()
    private static void drawGrid() {
        Console.color(Console.WHITE, Console.BLACK);
        for (int l = 0; l < GRID_LINES; l++)
            if (l % (TILE_HEIGHT + 1) == 0) {
                Console.cursor(l, 0);
                for (int c = 0; c < GRID_COLS; c++)
                    Console.print(c % (TILE_WIDTH + 1) == 0 ? '+' : '-');
            } else
                for (int c = 0; c < GRID_COLS; c += TILE_WIDTH + 1) {
                    Console.cursor(l, c);
                    Console.print('|');
                }
    }

    // Constants of Status part
    private static final int
            STATUS_LEFT = GRID_COLS + 1,
            SCORE_LINE = 2, SCORE_COL = 0, SCORE_WIDTH = 5,
            MOVES_LINE = 2, MOVES_COL = 7, MOVES_WIDTH = 5,
            BEST_LINE = 5, BEST_COL = 0, BEST_WIDTH = STATUS_WIDTH-2;

    // Draw titles in Status part. Only called by open()
    private static void drawTitles() {
        Console.color(Console.WHITE, Console.GRAY);
        drawTitle(SCORE_LINE, SCORE_COL, SCORE_WIDTH, "Score");
        drawTitle(MOVES_LINE, MOVES_COL, MOVES_WIDTH, "Moves");
        drawTitle(BEST_LINE, BEST_COL, BEST_WIDTH, "Best Scores");
    }

    // Draw each title
    private static void drawTitle(int line, int col, int width, String title) {
        Console.cursor(line, STATUS_LEFT + col);
        printCentered(title, width);
    }

    // Draw menu text. Only called by open()
    private static void drawKeys() {
        Console.color(Console.WHITE, Console.BLACK);
        int line = LINES - KEYS.length - 2;
        for (int i = 0; i < KEYS.length; i++) {
            Console.cursor(line + i, STATUS_LEFT);
            Console.print(KEYS[i]);
        }
    }

    /**
     * Visually update the current score
     * @param value - Current value
     */
    public static void updateScore(int value) {
        updateValue("" + value, SCORE_LINE, SCORE_COL, SCORE_WIDTH);
    }

    /**
     * Visually update the current moves
     * @param value - Current value
     */
    public static void updateMoves(int value) {
        updateValue("" + value, MOVES_LINE, MOVES_COL, MOVES_WIDTH);
    }

    /**
     * Visually updates one row of the best scores table
     * @param n - Number of the row (1..N)
     * @param name - Name of the player
     * @param score - Score of the player
     */
    public static void updateBestRow(int n, String name, int score) {
        updateValue("" + score, BEST_LINE + n-1 , BEST_COL  , SCORE_WIDTH);
        updateValue(name, BEST_LINE + n -1 , BEST_COL + SCORE_WIDTH, BEST_WIDTH - SCORE_WIDTH);
    }

    // Writes a value within a field. Called by updateScore(), updateMoves() and updateBestRow()
    private static void updateValue(String value, int line, int col, int width) {
        Console.color(Console.YELLOW, Console.DARK_GRAY);
        Console.cursor(line + 1, STATUS_LEFT + col);
        printCentered(value, width);
    }

    /**
     * Write a persistent message in bottom line.
     * @param txt - Message to write.
     */
    public static void showMessage(String txt) {
        messageTxt = txt;
        message(txt);
    }

    /**
     * Hide the persistent message in bottom line.
     */
    public static void hideMessage() {
        messageTxt = null;
        message("");
    }

    /**
     * Displays a message during a certain time or until a key is pressed.
     * At the end, the persistent message will be displayed again.
     * @param txt - Message to write.
     * @param time - Display time in miliseconds.
     */
    public static void showTempMessage(String txt, int time) {
        long tm = System.currentTimeMillis();
        message(txt);
        while (Console.isKeyPressed()) ;    // Wait if key is pressed
        Console.waitKeyPressed(time - (System.currentTimeMillis() - tm));
        if (messageTxt != null)
            message(messageTxt);
    }

    /**
     * Ask a question and wait up to 5 seconds for a key to be pressed as the answer.
     * Returns the code of the key pressed or (-1) if no key was pressed.
     * @param quest - The question displayed before the '?'
     * @return The key pressed or (-1)
     */
    public static int questionChar(String quest) {
        printQuest(quest);
        int key = Console.waitKeyPressed(5000);    // Wait 5 seconds for any key
        restoreFromQuest();
        return key;
    }

    /**
     * Read the name of the player and returns the String.
     * @param quest - The question displayed before the '?'
     * @return The name entered
     */
    public static String questionName(String quest) {
        return questionString(quest, BEST_WIDTH - SCORE_WIDTH - 1);
    }

    /**
     * Read one string to answer the question.
     * @param quest The question displayed before the '?'
     * @param field Maximum number of chars to read.
     * @return The read text.
     */
    public static String questionString(String quest, int field) {
        printQuest(quest);
        Console.clearAllChars();
        Console.setBackground(Console.LIGHT_GRAY);
        String resp = Console.nextLine(field);  // Read String
        restoreFromQuest();
        return resp;
    }

    // Called by questionChar() and questionString()
    private static void restoreFromQuest() {
        while (Console.isKeyPressed()) ;    // Wait if key is pressed
        Console.cursor(false);
        if (messageTxt != null)
            message(messageTxt);
    }

    // Called by questionChar() and questionString()
    private static void printQuest(String quest) {
        int pos = message(quest);
        while (Console.isKeyPressed()) ;    // Wait if key is pressed
        Console.cursor(LINES - 1, pos);
        Console.cursor(true);
        Console.print('?');
    }

    // Displays a message centered in bottom line.
    private static int message(String txt) {
        Console.color(Console.RED, Console.YELLOW);
        Console.cursor(LINES - 1, 0);
        if (txt == null || txt.length() == 0) {
            printSpaces(COLS);
            return 0;
        }
        return printCentered(txt, COLS);
    }

    // Write text centered within a field and return the number of column after last char.
    private static int printCentered(String txt, int field) {
        int len = txt.length();
        int half = (field - len) / 2;
        printSpaces(field - len - half);
        Console.print(txt);
        printSpaces(half);
        return field - half;
    }

    private static void printSpaces(int size) {
        for (; size > 0; size--) Console.print(' ');
    }
}


