package game.piece;

import game.ChessBoard;
import grid.GridDirection;
import grid.Location;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece{
    private static final char SYMBOL = 'R';
    private boolean hasMoved;

    public Rook(ChessBoard board, Location startLocation, PieceColor color) {
        this(board, startLocation, color, false);
    }
    public Rook(ChessBoard board, Location location, PieceColor color, boolean hasMoved) {
        super(board, location, color);
        this.IMAGE_PATH = "pieces/" + color.getColorString() + "/rook.png";
        this.hasMoved = hasMoved;
    }

    @Override
    public Set<Location> getPossibleMovesIgnoreCheck() {
        Set<Location> ret = new HashSet<>();
        for(GridDirection dir : GridDirection.FOUR_DIRECTIONS) {
            ret.addAll((getStraightLine(getLocation(), dir)));
        }
        return ret;
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }

    @Override
    public Piece copyForBoard(ChessBoard board) {
        return new Rook(board, getLocation(), getColor(), hasMoved);
    }
}
