package game.piece;

import game.ChessBoard;
import grid.GridDirection;
import grid.Location;

import java.util.ArrayList;
import java.util.List;

public class Pawn extends Piece{
    private static final char SYMBOL = 'P';

    private boolean isEnPassantPossible = false;
    private final GridDirection colorDir;

    public Pawn(ChessBoard board, Location startLocation, String color){
        super(board, startLocation, color);
        this.colorDir = (color == "white") ? GridDirection.NORTH : GridDirection.SOUTH;
    }


    @Override
    public List<Location> getPossibleMoves() {
        List<Location> ret = new ArrayList<>();
        Location neighbor = getLocation().getNeighbor(colorDir);

        //If nothing straight ahead
        if(getBoard().canMove(this, neighbor)){
            ret.add(neighbor);

            //If this pawn's first move and nothing in the next two squares
            if(getLocation() == getStartLocation()) {
                Location DoubleNeighbor = neighbor.getNeighbor(colorDir);
                if(getBoard().canMove(this, DoubleNeighbor)) ret.add(DoubleNeighbor);
            }
        }

        // Check if pawn can capture to the right
        Location lookRight = getLocation().getNeighbor(colorDir.turnRight45());
        if(isEnemyPieceNotKing(getBoard().get(lookRight))) {
            ret.add(lookRight);
        }
        // Check if pawn can capture to the left
        Location lookLeft = getLocation().getNeighbor(colorDir.turnLeft45());
        if(isEnemyPieceNotKing(getBoard().get(lookLeft))) {
            ret.add(lookLeft);
        }

        return ret;
    }

    public void move() {

    }

    /**
     * Returns whether given piece is a capturable enemy piece
     * @param piece
     * @return
     */
    public boolean isEnemyPieceNotKing(IPiece piece) {
        if (!(piece instanceof King)) {
            if ( piece.getColor() != getColor()) {
                return true;
            }
        }
        return false;
    }

    public boolean enPasssantPossible(Pawn piece) {
        return isEnPassantPossible;
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }

    @Override
    public IPiece copyForBoard (ChessBoard copyTo) {
        return new Pawn(copyTo, getLocation(), getColor());
    }
}
