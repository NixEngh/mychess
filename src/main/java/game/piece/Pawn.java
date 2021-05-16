package game.piece;

import game.GameBoard;
import grid.Location;

import java.util.List;

public class Pawn implements IPiece{
    private static final char SYMBOL = 'P';
    private GameBoard board;

    public Pawn(Location startLocation, String Color){

    }
    @Override
    public List<Location> getPossibleMoves() {
        return null;
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }
}
