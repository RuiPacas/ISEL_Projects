package pt.isel.poo.powerplantandroid.ctrl;

import android.app.Activity;
import android.os.Bundle;
import android.widget.TextView;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

import pt.isel.poo.powerplantandroid.R;
import pt.isel.poo.powerplantandroid.model.Loader;
import pt.isel.poo.powerplantandroid.model.Plant;
import pt.isel.poo.powerplantandroid.tile.OnTileTouchListener;
import pt.isel.poo.powerplantandroid.tile.TilePanel;
import pt.isel.poo.powerplantandroid.view.CellTile;

public class Game extends Activity implements OnTileTouchListener {

    public static final int WIN_HEIGHT = 30, WIN_WIDTH = 30;
    private static final String LEVELS_FILE = "Levels.txt"; // Name of levels file
    TextView level;
    TextView moves;
    private Plant model;
    private TilePanel view;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);
        view = findViewById(R.id.tilePanel);
        level = findViewById(R.id.level);
        moves = findViewById(R.id.moves);
        loadLevel(1);
    }

    private boolean loadLevel(int n) {
        Scanner in = null;
        try {
            in = new Scanner(new FileInputStream(LEVELS_FILE)); // Scanner to read the file
            model = new Loader(in).load(n);                     // Load level from scanner
            view = new TilePanel(this);
            view.setSize(model.getHeight(), model.getWidth());
            level.setText(n);
            moves.setText(0);
            return true;
        } catch (FileNotFoundException | InputMismatchException e) {
            System.out.println("Error loading file \"" + LEVELS_FILE + "\":\n" + e.getMessage());
            return false;
        } catch (Loader.LevelFormatException e) {
            System.out.println(e.getMessage() + " in file \"" + LEVELS_FILE + "\"");
            System.out.println(" " + e.getLineNumber() + ": " + e.getLine());
            return false;
        } finally {
            if (in != null) in.close();   // Close the file
        }
    }


    @Override
    public boolean onClick(int xTile, int yTile) {
        return false;
    }

    @Override
    public boolean onDrag(int xFrom, int yFrom, int xTo, int yTo) {
        return false;
    }

    @Override
    public void onDragEnd(int x, int y) {
    }

    @Override
    public void onDragCancel() {
    }
}