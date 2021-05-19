package game.piece;

import game.ChessBoard;
import game.Square;
import grid.GridDirection;
import grid.Location;
import javafx.scene.image.Image;

import java.util.HashSet;
import java.util.Set;

public class Pawn extends Piece{
    private static final char SYMBOL = 'P';

    private boolean isEnPassantPossible = false;
    private final GridDirection colorDir;

    public Pawn(ChessBoard board, Location startLocation, PieceColor color){
        super(board, startLocation, color);
        this.IMAGE_PATH = "pieces/" + color.getColorString() + "/pawn.png";
        this.colorDir = (color == PieceColor.LIGHT) ? GridDirection.NORTH : GridDirection.SOUTH;
    }


    @Override
    public Set<Location> getPossibleMoves() {
        Set<Location> ret = new HashSet<>();

        Location neighbor = getLocation().getNeighbor(colorDir);

        //If nothing straight ahead
        if(getBoard().canMove(this, neighbor) && getBoard().get(neighbor) == null){
            ret.add(neighbor);

            //If this pawn's first move and nothing in the next two squares
            if(getLocation() == getStartLocation()) {
                Location doubleNeighbor = neighbor.getNeighbor(colorDir);
                if(getBoard().canMove(this, doubleNeighbor) && getBoard().get(doubleNeighbor) == null) ret.add(doubleNeighbor);
            }
        }

        // Check if pawn can capture to the right
        Location lookRight = getLocation().getNeighbor(colorDir.turnRight45());

        if(getBoard().isOnGrid(lookRight)) {
            if (isEnemyPieceNotKing(getBoard().get(lookRight))) {
                ret.add(lookRight);
            }
        }

        // Check if pawn can capture to the left
        Location lookLeft = getLocation().getNeighbor(colorDir.turnLeft45());
        if(getBoard().isOnGrid(lookLeft)) {
            if (isEnemyPieceNotKing(getBoard().get(lookLeft))) {
                ret.add(lookLeft);
            }
        }

        return ret;
    }



    public boolean enPasssantPossible(Pawn piece) {
        return isEnPassantPossible;
    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }

    @Override
    public Piece copyForBoard (ChessBoard copyTo) {
        return new Pawn(copyTo, getStartLocation(), getColor());
    }

}
