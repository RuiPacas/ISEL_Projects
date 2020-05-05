package ctrl;

import isel.leic.pg.Console;
import isel.leic.pg.Location;
import isel.leic.pg.MouseEvent;

import console.Window;
import console.tile.TilePanel;

import model.Loader;
import model.Plant;

import view.StatusPanel;
import view.CellTile;

import java.awt.event.KeyEvent;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * Main class in console mode for the "PowerPlant" game.
 * Performs interaction with the user.<br/>
 * Implements the Controller in the MVC model,
 * making the connection between the model and the viewer specific to the console mode.
 */
public class Game {
    public static void main(String[] args) {
        Game game = new Game();
        game.run();
    }

    public static final int WIN_HEIGHT = 30, WIN_WIDTH = 30;
    private static final String LEVELS_FILE = "Levels.txt"; // Name of levels file
    private Plant model;                                    // Model of current level
    private TilePanel view;                                 // View of cells
    private StatusPanel status = new StatusPanel(WIN_WIDTH);
    private Window win = new Window("Power Plant",WIN_HEIGHT, WIN_WIDTH +StatusPanel.WIDTH); // The console window

    /**
     * Main game loop.
     * Returns when there are no more levels or the player gives up.
     */
    private void run() {
        int num = 0;    // Current level number
        LoadResult res;
        while ((res= loadLevel(++num)) ==LoadResult.SUCESS)
            if (!playLevel() || !win.question("Next level")) {
                exit("Bye.");
                return;
            }
        exit(res==LoadResult.NO_MORE_LEVELS ? "No more Levels" : "ERROR");
    }

    /**
     * Displays the message and closes the console window.
     * @param msg the message to show
     */
    private void exit(String msg) {
        win.message(msg);
        win.close();
    }

    /**
     * Main loop of each level.
     * @return true - the level has been completed. false - the player has given up.
     */
    private boolean playLevel() {
        // Opens panel of tiles with dimensions appropriate to the current level.
        // Starts the viewer for each model ConnecedCells.
        // Shows the initial state of all cells in the model.
        for (int l = 0; l < model.getHeight(); l++)
            for (int c = 0; c < model.getWidth(); c++)
                view.setTile(l,c, CellTile.newInstance(model.getCell(l,c)));
        while ( play() ) // Process one input event (mouse or keyboard)
            status.setMoves( model.getMoves() );
        return winGame();       // Verify win conditions; false: finished without complete
    }

    /**
     * Verify win conditions and print winner message.
     * @return true - if level is completed
     */
    private boolean winGame() {
        if (model.isCompleted()) {
            status.setMoves(model.getMoves());
            win.message("Winner");
            return true;
        }
        return false;
    }

    /**
     * Load the model of the indicated level from the LEVELS_FILE file
     * @param n The level to load (1..MAX)
     * @return true if the level is loaded
     */
    private LoadResult loadLevel(int n) {
        Scanner in = null;
        try {
            in = new Scanner(new FileInputStream(LEVELS_FILE)); // Scanner to read the file
            model = new Loader(in).load(n);                     // Load level from scanner
            model.setListener( listener );                      // Set the listener of modifications
            view = new TilePanel(model.getHeight(),model.getWidth(),CellTile.SIDE);
            win.clear();
            view.center(WIN_HEIGHT,WIN_WIDTH);
            status.setLevel(n);
            status.setMoves(0);
            return LoadResult.SUCESS;
        } catch (FileNotFoundException | InputMismatchException e) {
            System.out.println("Error loading file \""+LEVELS_FILE+"\":\n"+e.getMessage());
            return LoadResult.INSUCESS;
        } catch (Loader.LevelFormatException e) {
            if (e.getLine().equals("END")) return LoadResult.NO_MORE_LEVELS;
            System.out.println(e.getMessage()+" in file \""+LEVELS_FILE+"\"");
            System.out.println(" "+e.getLineNumber()+": "+e.getLine());
            return LoadResult.INSUCESS;
        } finally {
            if (in!=null) in.close();   // Close the file
        }
    }

    enum LoadResult {SUCESS,INSUCESS,NO_MORE_LEVELS};

    private class ModelListener implements Plant.Listener {
        @Override
        public void cellChanged(int lin, int col) {
            view.getTile(lin,col).repaint();
        }
    }
    private ModelListener listener = new ModelListener();

    /**
     * Makes the move corresponding to the mouse event.
     * @return false - If user game aborted (escape key) or terminate the level
     */
    private boolean play() {
        for(;;) {
            int key = Console.waitKeyPressed(250); // Wait for mouse event or a key
            if (key == KeyEvent.VK_ESCAPE)         // Escape key -> abort game
                return false;
            if (key == Console.MOUSE_EVENT) {      // Is a mouse event
                MouseEvent me = Console.getMouseEvent();
                if (me != null && me.type == MouseEvent.DOWN) {
                    Location pos = view.getModelPosition(me.line, me.col); // Convert mouse position to ConnecedCells coordinates
                    if (pos!=null && model.touch(pos.line, pos.col)){
                        listener.cellChanged(pos.line,pos.col);
                        return  !model.isCompleted();
                    }
                }
            }
        }
    }
}
