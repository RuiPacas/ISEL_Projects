
import isel.leic.pg.Console;
import java.awt.color.ColorSpace;
import java.awt.event.KeyEvent;

public class Game2048 {
    static final int LINES = 4, COLS = 4;               //Dimensions of the grid.
    static final int MIN_VALUE = 2;                     //Minimum value of the tile.
    private static TopScores top = new TopScores();     //Object of the Class TopScores.
    private static int moves;                           //Number of moves.
    private static boolean exit = false;                //Boolean used to leave the game if requested.
    private static int key;
    private static int[][] tiles = new int[LINES][COLS];
    private static int score;                           //Optional - Score value
    private static int undoMove;                        //Optional - Number of moves on the past round
    private static int undoScore;                       //Optional - Score on the past round
    private static int[][] undoTiles = new int[LINES][COLS]; //Optional
    public static boolean toggle = false;              // Optional - Animation Toggle
    private static final long ANIMATION_TIME = 50L;             // Optional - Animation time
    /**
     * Main method of the game.
     */

    public static void main(String[] args) {
        Panel.open();
        init();
        for (; ; ) {
            AnimationColors();
            key = Console.waitKeyPressed(5000);
            if (!processKey(key)) break;
            if (won()) break;
            if (defeat()) break;
            while (Console.isKeyPressed(key)) ;
        }
        Panel.close();
        top.saveToFile();
    }

    /**
     * Method responsible for initializing the game.
     */
    private static void init() {
        Panel.showMessage("Use cursor keys to play");
        showScores();
        randomTile();
        randomTile();
        Panel.updateMoves(moves = 0);
        Panel.updateScore(score = 0);
    }

    /**
     * Returns the instruction of the pressed key.
     * @param key - The key pressed.
     * @return true if one of the assigned keys is pressed and calls the necessary method to execute it.
     */
    private static boolean processKey(int key) {


        switch (key) {
            case KeyEvent.VK_UP:
                saveUndoValues(); // Optional - Undo
                moveUp();
                break;
            case KeyEvent.VK_DOWN:
                saveUndoValues(); // Optional - Undo
                moveDown();
                break;
            case KeyEvent.VK_LEFT:
                saveUndoValues(); // Optional - Undo
                moveLeft();
                break;
            case KeyEvent.VK_RIGHT:
                saveUndoValues(); // Optional - Undo
                moveRight();
                break;
            case KeyEvent.VK_ESCAPE:
            case 'Q':
                quitGame();
                break;
            case 'N':
                newGame();
                break;
            case KeyEvent.VK_BACK_SPACE:
                undo();
            case 'A':
                toggle=!toggle;
                break;
        }
        return !exit;
    }

    /**
     * Ends the game if called.
     */
    private static void quitGame() {
        int resp = Panel.questionChar("Exit game (Y/N)");
        if (resp == 'Y')
            exit = true;
        if (top.canAdd(score)) {
            createRow();
        }
        key = 0;
    }

    /**
     * Give a value to the tile that it's going to be created.
     * @return 2 with a 90% chance and a 4 with 10%.
     */

    private static int value() {
        int random = (int) (Math.random() * 10);
        return random == 0 ? MIN_VALUE*2 : MIN_VALUE;
    }

    /**
     * Method that generate the coordinates to the new tile that's going to appear in the panel.
     */

    private static void randomTile() {
        int j, k;
        do {
            j = (int) (Math.random() * LINES);
            k = (int) (Math.random() * COLS);
        } while (tiles[j][k] != 0);
        tiles[j][k] = value();
        Panel.showTile(j, k, tiles[j][k]);
    }

    /**
     * Method responsible for moving the possible tiles to the left.
     */
    private static void moveLeft() {
        int dCol = -1;
        int lin, col, x, i, colaux = -1;
        int linaux = -1;
        boolean t = false;
        for (i = 0; i < LINES; i++) {
            lin = i;
            for (int y = 1; y < COLS; y++) {
                if (tiles[i][y] != 0) {
                    x = y;
                    do {
                        col = x + dCol;
                        if (tiles[lin][col] == 0) {
                            infotojoin(i, x, lin, col);
                            t = true;
                        } else if (tiles[lin][col] == tiles[i][x] && t && col == colaux && lin == linaux)
                            break;
                        else if (tiles[lin][col] == tiles[i][x]) {
                            infotojoin2(i, x, lin, col);
                            linaux = lin;
                            colaux = col;
                            t = true;
                            updateScore(tiles[lin][col]);
                            break;
                        } else break;
                        x--;
                    } while (col > 0);

                }
            }
        }
        if (t) {
            randomTile();
            Panel.updateMoves(++moves);
        }
    }

    /**
     * Method responsible for moving the possible tiles upwards.
     */
    private static void moveUp() {
        int dLin = -1, lin, col, x, y, linaux = -1, colaux = -1;
        boolean t = false;
        for (y = 0; y < COLS; y++) {
            for (int i = 1; i < LINES; i++) {
                col = y;
                if (tiles[i][y] != 0) {
                    x = i;
                    do {
                        lin = x + dLin;
                        if (tiles[lin][col] == 0) {
                            infotojoin(x, y, lin, col);
                            t = true;
                        } else if (tiles[lin][col] == tiles[x][y] && t && lin == linaux && col == colaux) {
                            break;
                        } else if (tiles[lin][col] == tiles[x][y]) {
                            infotojoin2(x, y, lin, col);
                            colaux = col;
                            linaux = lin;
                            updateScore(tiles[lin][col]);
                            t = true;
                            break;
                        } else break;
                        x--;
                    } while (lin > 0);
                }
            }
        }
        if (t) {
            randomTile();
            Panel.updateMoves(++moves);
        }
    }

    /**
     * Method responsible for moving the possible tiles to the right.
     */
    private static void moveRight() {
        int dCol = 1;
        int lin, col, x, i, colaux = COLS + 1;
        int linaux = LINES + 1;
        boolean t = false;
        for (i = 0; i < LINES; i++) {
            lin = i;
            for (int y = (COLS - 2); y >= 0; y--) {
                if (tiles[i][y] != 0) {
                    x = y;
                    do {
                        col = x + dCol;
                        if (tiles[lin][col] == 0) {
                            infotojoin(i, x, lin, col);
                            t = true;
                        } else if (tiles[lin][col] == tiles[i][x] && t && col == colaux && lin == linaux) {
                            break;
                        } else if (tiles[lin][col] == tiles[i][x]) {
                            infotojoin2(i, x, lin, col);
                            linaux = lin;
                            colaux = col;
                            t = true;
                            updateScore(tiles[lin][col]);
                            break;
                        } else break;
                        x++;
                    } while (col < COLS - 1);
                }
            }
        }
        if (t) {
            randomTile();
            Panel.updateMoves(++moves);
        }
    }

    /**
     * Method responsible for moving the possible tiles to the downwards.
     */
    private static void moveDown() {
        int dLin = 1;
        int lin, col, x, y, linaux = LINES + 1;
        int colaux = LINES + 1;
        boolean t = false;
        for (y = 0; y < COLS; y++) {
            for (int i = (LINES - 2); i >= 0; i--) {
                col = y;
                if (tiles[i][y] != 0) {
                    x = i;
                    do {
                        lin = x + dLin;
                        if (tiles[lin][col] == 0) {
                            infotojoin(x, y, lin, col);
                            t = true;
                        } else if (tiles[lin][col] == tiles[x][y] && t && lin == linaux && col == colaux) {
                            break;
                        } else if (tiles[lin][col] == tiles[x][y]) {
                            infotojoin2(x, y, lin, col);
                            linaux = lin;
                            colaux = col;
                            t = true;
                            updateScore(tiles[lin][col]);
                            break;

                        } else break;
                        x++;
                    } while (lin < LINES - 1);
                }
            }
        }
        if (t) {
            randomTile();
            Panel.updateMoves(++moves);
        }
    }

    /**
     * Method called to move the tile to a empty position.
     * @param a   - Line of the tile that it's going to be moved.
     * @param b   - Column of the tile that it's going to be moved.
     * @param lin - Line of the position that the tile is going to be moved to.
     * @param col - Column of the position that the tile is going to be moved to.
     */
    private static void infotojoin(int a, int b, int lin, int col) {
        if(toggle)Console.sleep(ANIMATION_TIME);
        Panel.hideTile(a, b);
        tiles[lin][col] = tiles[a][b];
        tiles[a][b] = 0;
        Panel.showTile(lin, col, tiles[lin][col]);

    }

    /**
     * Method called to fuse 2 tiles that have the same value.
     * @param a   - Line of the tile that it's going to be moved.
     * @param b   - Column of the tile that it's going to be moved.
     * @param lin - Line that contains a tile and it's also where the tiles will fuse.
     * @param col - Column that contains a tile and it's also where the tiles will fuse.
     */

    private static void infotojoin2(int a, int b, int lin, int col) {
        if(toggle)Console.sleep(ANIMATION_TIME);
        Panel.hideTile(a, b);
        tiles[lin][col] = (tiles[a][b] * 2);
        tiles[a][b] = 0;
        Panel.showTile(lin, col, tiles[lin][col]);
    }

    /**
     * Method responsible for showing the Scores on the game panel.
     */

    private static void showScores() {
        for (int i = 0; i < top.getNumOfRows(); i++) {
            Panel.updateBestRow(i + 1, top.getRow(i).name, top.getRow(i).points);
        }

    }

    /**
     * Method responsible for making the sum of the score during a game.
     * @param a value of the sum of 2 tiles.
     */
    private static void updateScore(int a) {
        score += a;
        Panel.updateScore(score);
    }

    /**
     * Method responsible for restarting the game.
     */
    private static void newGame() {
        int resp = Panel.questionChar("New Game (Y/N)");
        if (resp == 'Y') {
            if (top.canAdd(score))
                createRow();
            resetTiles();
            init();
        }
        key = 0;
    }

    /**
     * Method responsible for creating a score row.
     */
    private static void createRow() {
        String playerName = Panel.questionName("Nome");
        top.addRow(playerName, score);
    }

    /**
     * Method responsible that determines if the player has won the game.
     * @return true if player has won.
     */

    private static boolean won() {
        for (int i = 0; i < LINES; i++) {
            for (int j = 0; j < COLS; j++) {
                if (tiles[i][j] == 2048) {
                    Panel.showTempMessage("Congratulations, you won!!", 5000);
                    if (top.canAdd(score))
                        createRow();
                    return true;
                }
            }
        }

        return false;
    }

    /**
     * Method that resets the tiles values.
     */

    private static void resetTiles() {
        for (int i = 0; i < COLS; i++) {
            for (int j = 0; j < LINES; j++) {
                tiles[i][j] = 0;
                Panel.hideTile(j, i);

            }
        }
    }

    /**
     * Method that analise if the grid is full of tiles.
     */

    private static boolean gridFull() {
        int n = COLS * LINES;
        int a = 0;
        for (int i = 0; i < LINES; i++) {
            for (int j = 0; j < COLS; j++) {
                if (tiles[i][j] != 0) {
                    a += 1;
                }
            }
        }
        if (a == n) return true;
        return false;
    }

    /**
     * Method that determines if there's still any possible move.
     * @return true if there's no more moves left.
     */

    private static boolean noMoves() {
        int a = 0;
        if (gridFull()) {
            for (int i = 0; i < LINES; i++) {
                for (int j = 0; j < COLS; j++) {
                    int lin = i + 1;
                    int col = j + 1;
                    if (i == LINES - 1 && j == COLS - 1) break;
                    if (i == (LINES - 1))
                        if (tiles[i][j] != tiles[i][col])
                            ++a;
                    if (j == (COLS - 1))
                        if (tiles[i][j] != tiles[lin][j])
                            ++a;
                    if (i < LINES - 1 && j < COLS - 1)
                        if ((tiles[i][j] != tiles[lin][j]) && (tiles[i][j] != tiles[i][col]))
                            ++a;
                }
            }

        }
        if (a == (COLS * LINES) - 1) return true;
        return false;
    }

    /**
     * Method that determines if the player lose the game.
     * @return true if the player lost.
     */
    private static boolean defeat() {
        if (gridFull() && noMoves()) {
            Panel.showTempMessage("YOU LOSE!!  SCORE=" + score, 5000);
            if (top.canAdd(score))
                createRow();
            return true;
        }
        return false;

    }

    /**
     * Optional Part - Undo
     */

    /**
     * Hide all the tiles on the panel.
     */
    public static void hideAll() {
        for (int i = 0; i < LINES; ++i) {
            for (int j = 0; j < COLS; j++) {
                Panel.hideTile(i, j);
            }
        }
    }

    /**
     * Show all the tiles that exist on the tiles array.
     */
    public static void showAll() {
        for (int i = 0; i < LINES; ++i) {
            for (int j = 0; j < COLS; j++) {
                if (tiles[i][j] != 0)
                    Panel.showTile(i, j, tiles[i][j]);

            }
        }
    }

    /**
     * Method that copy one array to another.
     *
     * @param to   - is the array that you want to copy to.
     * @param from - is the array that you want to copy from.
     */
    public static void arrayCopy(int[][] to, int[][] from) {
        for (int i = 0; i < from.length; ++i) {
            for (int j = 0; j < from[i].length; j++) {
                to[i][j] = from[i][j];
            }
        }
    }

    /**
     * Method that save all the values responsible for undoing.
     */
    public static void saveUndoValues() {
        undoMove = moves;
        undoScore = score;
        arrayCopy(undoTiles, tiles);
    }

    /**
     * Method responsible for undoing one move.
     */
    public static void undo() {
        if (moves > 0) {
            moves = undoMove;
            score = undoScore;
            arrayCopy(tiles, undoTiles);
            Panel.updateScore(score);
            Panel.updateMoves(moves);
            hideAll();
            showAll();
            Panel.showTempMessage("UNDO last move", 3000);
        } else Panel.showTempMessage("No moves to undo", 3000);


    }

    /**
     * Optional Part - Animation
     */

    /**
     * Method that change the background color of the Animation key.
     */
    public static void AnimationColors(){
        if(toggle){
            Panel.colorOn();
        }
        else{
            Panel.colorOff();
        }
    }
}


