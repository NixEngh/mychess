package game;

import game.piece.IPiece;
import grid.Grid;
import grid.Location;
import javafx.scene.layout.GridPane;

import java.util.function.Function;

public class GameBoard extends Grid<IPiece> {


    public GameBoard(int rows, int columns) {
        super(rows, columns);
        locations().forEach(loc -> add(new Square(this, loc.col, loc.row), loc.col, loc.row));

    }

}
