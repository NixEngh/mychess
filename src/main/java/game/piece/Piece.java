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
    public boolean hasMoved;

    public Piece(ChessBoard board, Location location, PieceColor color) {
        this(board, location, color, location);
    }
    public Piece(ChessBoard board, Location location, PieceColor color, Location startLocation) {
        this.board = board;
        this.startLocation = startLocation;
        this.currentLocation = location;
        this.color = color;
    }

    public Set<Location> getPossibleMoves() {
        Set<Location> ret = new HashSet<>();
        for (Location location : getPossibleMovesIgnoreCheck()) {
            if(board.canMoveCheck(this.getLocation(), location)) {
                ret.add(location);
            }
        }
        return ret;

    }

    /**
     * Handles inner changes of a piece when it is moved
     * Called only when the piece actually moves. Must be made AFTER board.makemove
     * @param to
     */
    public void makeMove(Location to) {
        setLocation(to);
        hasMoved = true;
    }

    public boolean isHasMoved() {
        return hasMoved;
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
    public boolean isEnemyPiece(IPiece piece) {
        if(piece==null) return false;

        return piece.getColor() != getColor();
    }

    public Set<Location> getStraightLine(Location start, GridDirection dir) {
        Set<Location> ret = new HashSet<>();

        Location test = start;
        while(getBoard().isOnGrid(test.getNeighbor(dir))) {
            test = test.getNeighbor(dir);
            if(getBoard().get(test) == null) {
                ret.add(test);
            } else if (isEnemyPiece(getBoard().get(test))) {
                ret.add(test);
                break;
            } else {
                break;
            }
        }
        return ret;
    }

}
