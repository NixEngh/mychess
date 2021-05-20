package game.piece;

import game.ChessBoard;
import grid.GridDirection;
import grid.Location;

import java.util.HashSet;
import java.util.Set;

public class Rook extends Piece{
    private static final char SYMBOL = 'R';

    public Rook(ChessBoard board, Location startLocation, PieceColor color) {
        super(board, startLocation, color);
        this.IMAGE_PATH = "pieces/" + color.getColorString() + "/rook.png";
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
        return new Rook(board, getLocation(), getColor());
    }
}
