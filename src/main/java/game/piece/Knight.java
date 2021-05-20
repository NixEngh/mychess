package game.piece;

import game.ChessBoard;
import grid.GridDirection;
import grid.Location;

import java.util.HashSet;
import java.util.Set;

public class Knight extends Piece{
    private static final char SYMBOL = 'N';

    public Knight(ChessBoard board, Location startLocation, PieceColor color) {
        super(board, startLocation, color);
        this.IMAGE_PATH = "pieces/" + color.getColorString() + "/knight.png";
    }

    @Override
    public Set<Location> getPossibleMovesIgnoreCheck() {
        Set<Location> ret = new HashSet<>();
        for(GridDirection dir : GridDirection.FOUR_DIRECTIONS) {
            Location elbow = getLocation().getNeighbor(dir, 2);

            Location possible = elbow.getNeighbor(dir.turnRight());
            if(getBoard().isOnGrid(possible)) {
                if(getBoard().get(possible)==null) ret.add(possible);
                else if(isEnemyPiece(getBoard().get(possible))) ret.add(possible);
            }
            possible = elbow.getNeighbor(dir.turnLeft());
            if(getBoard().isOnGrid(possible)) {
                if(getBoard().get(possible)==null) ret.add(possible);
                else if(isEnemyPiece(getBoard().get(possible))) ret.add(possible);
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
        return new Knight(board, getLocation(), getColor());
    }
}
