package game.piece;

import game.ChessBoard;
import grid.Location;

import java.util.HashSet;
import java.util.Set;

public class King extends Piece{
    private static final char SYMBOL = 'K';

    public King(ChessBoard board, Location startLocation, PieceColor color) {
        super(board, startLocation, color);
        IMAGE_PATH = "pieces/" + color.getColorString() + "/king.png";
    }
    @Override
    public Set<Location> getPossibleMovesIgnoreCheck() {
        Set<Location> ret = new HashSet<>();
        for(Location loc : getLocation().allNeighbors()) {
            if(getBoard().isOnGrid(loc)) {
                if(getBoard().get(loc) == null) {
                    ret.add(loc);
                }
                else if (isEnemyPiece(getBoard().get(loc))){
                    ret.add(loc);
                }
            }
        }
        return ret;

    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }

    @Override
    public Piece copyForBoard(ChessBoard board) {
        return new King(board, getLocation(), getColor());
    }
}
