package game.piece;

import game.ChessBoard;
import grid.GridDirection;
import grid.Location;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece{
    private static final char SYMBOL = 'P';

    private final GridDirection colorDir;


    public Pawn(ChessBoard board, Location location, PieceColor color){
        this(board, location, color, location);
    }
    public Pawn(ChessBoard board, Location location, PieceColor color, Location startLocation){
        super(board, location, color, startLocation);
        this.IMAGE_PATH = "pieces/" + color.getColorString() + "/pawn.png";
        this.colorDir = (color == PieceColor.LIGHT) ? GridDirection.NORTH : GridDirection.SOUTH;
    }

    //Is called only when the piece is set to move
    @Override
    public void makeMove(Location loc) {
        //before the pawn is moved
        if(loc.equals(getLocation().getNeighbor(colorDir, 2))){
            getBoard().setJustMovedTwoPawn(this);
        }
        setLocation(loc);
    }
    public Location getBehindLocation() {
        return getLocation().getNeighbor(colorDir.turn180());
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
        lookRight = getLocation().getNeighbor(colorDir.turnRight());
        lookLeft = getLocation().getNeighbor(colorDir.turnLeft());

        testForEnPassant(ret, lookRight);
        testForEnPassant(ret, lookLeft);


        return ret;
    }

    private void testForEnPassant(Set<Location> ret, Location lookLeft) {
        if(getBoard().isOnGrid(lookLeft)) {
            if (isEnemyPiece(getBoard().get(lookLeft))) {

                Piece piece = getBoard().get(lookLeft);
                if (piece instanceof Pawn) {
                    if(piece.equals(getBoard().getJustMovedTwoPawn())){
                        ret.add(((Pawn) piece).getBehindLocation());
                    }
                }
            }
        }
    }


    @Override
    public char getSymbol() {
        return SYMBOL;
    }

    @Override
    public Piece copyForBoard (ChessBoard copyTo) {
        return new Pawn(copyTo, getLocation(), getColor(), getStartLocation());
    }

}
