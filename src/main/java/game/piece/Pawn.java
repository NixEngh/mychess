package game.piece;

import game.ChessBoard;
import grid.GridDirection;
import grid.Location;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece{
    private static final char SYMBOL = 'P';

    private Location enPassantLocation;
    private final GridDirection colorDir;

    public Pawn(ChessBoard board, Location startLocation, PieceColor color){
        super(board, startLocation, color);
        this.IMAGE_PATH = "pieces/" + color.getColorString() + "/pawn.png";
        this.colorDir = (color == PieceColor.LIGHT) ? GridDirection.NORTH : GridDirection.SOUTH;
    }

    //Is called only when the piece is set to move
    @Override
    public void makeMove(Location loc) {
        //before the pawn is moved
        if(loc.equals(getLocation().getNeighbor(colorDir, 2))){

        }
        setLocation(loc);
        setEnPassantLocation(null);
    }


    @Override
    public Set<Location> getPossibleMovesIgnoreCheck() {
        Set<Location> ret = new HashSet<>();

        Location neighbor = getLocation().getNeighbor(colorDir);

        //If nothing straight ahead
        if( getBoard().isOnGrid(neighbor) && getBoard().get(neighbor) == null){
            ret.add(neighbor);

            //If this pawn's first move and nothing in the next two squares
            if(getLocation() == getStartLocation()) {
                Location doubleNeighbor = neighbor.getNeighbor(colorDir);
                if(getBoard().isOnGrid(doubleNeighbor) && getBoard().get(doubleNeighbor) == null) ret.add(doubleNeighbor);
            }
        }

        // Check if pawn can capture to the right
        Location lookRight = getLocation().getNeighbor(colorDir.turnRight45());

        if(getBoard().isOnGrid(lookRight)) {
            if (isEnemyPiece(getBoard().get(lookRight))) {
                ret.add(lookRight);
            }
        }

        // Check if pawn can capture to the left
        Location lookLeft = getLocation().getNeighbor(colorDir.turnLeft45());
        if(getBoard().isOnGrid(lookLeft)) {
            if (isEnemyPiece(getBoard().get(lookLeft))) {
                ret.add(lookLeft);
            }
        }



        return ret;
    }

    public void setEnPassantLocation(Location enPassantLocation) {
        this.enPassantLocation = enPassantLocation;
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }

    @Override
    public Piece copyForBoard (ChessBoard copyTo) {
        return new Pawn(copyTo, getLocation(), getColor());
    }

}
