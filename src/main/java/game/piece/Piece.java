package game.piece;

import game.ChessBoard;
import game.Square;
import grid.GridDirection;
import grid.Location;
import javafx.scene.image.Image;

import java.util.HashSet;
import java.util.Set;

public abstract class Piece implements IPiece{
    private PieceColor color;
    private ChessBoard board;
    private final Location startLocation;
    private Location currentLocation;
    public String IMAGE_PATH;

    public Piece(ChessBoard board, Location startLocation, PieceColor color) {
        this.board = board;
        this.startLocation = startLocation;
        this.currentLocation = startLocation;
        this.color = color;
    }

    public ChessBoard getBoard() {
        return board;
    }

    public Location getStartLocation() {
        return startLocation;
    }

    @Override
    public Location getLocation() {
        return currentLocation;
    }

    public void setLocation(Location loc) { currentLocation = loc; }

    @Override
    public PieceColor getColor() { return color; }

    /**
     * This is when I realized that planning is a good idea.
     * This method creates a copy of the piece, so that when copying a ChessBoard, the original instances of IPiece are left untouched
     * @param board
     * @return The copy
     */
    public abstract Piece copyForBoard(ChessBoard board);

    @Override
    public Image getImage() {
        return new Image(IMAGE_PATH, Square.getSIZE(), Square.getSIZE(), true, true);
    }

    /**
     * Returns whether given piece is a capturable enemy piece
     * @param piece
     * @return
     */
    public boolean isEnemyPieceNotKing(IPiece piece) {
        if(piece==null) return false;

        if (!(piece instanceof King)) {
            if ( piece.getColor() != getColor()) {
                return true;
            }
        }
        return false;
    }

    public Set<Location> getStraightLine(Location start, GridDirection dir) {
        Set<Location> ret = new HashSet<>();

        Location test = start;
        while(getBoard().isOnGrid(test.getNeighbor(dir))) {
            test = test.getNeighbor(dir);
            if(getBoard().get(test) == null) {
                ret.add(test);
            } else if (isEnemyPieceNotKing(getBoard().get(test))) {
                ret.add(test);
                break;
            } else {
                break;
            }
        }
        return ret;
    }

}
