package game.piece;

import game.ChessBoard;
import grid.Location;

public abstract class Piece implements IPiece{
    private PieceColor color;
    private ChessBoard board;
    private final Location startLocation;
    private Location currentLocation;

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

    @Override
    public PieceColor getColor() { return color; }

    /**
     * This is when I realized that planning is a good idea.
     * This method creates a copy of the piece, so that when copying a ChessBoard, the original instances of IPiece are left untouched
     * @param board
     * @return The copy
     */
    public abstract Piece copyForBoard(ChessBoard board);

}
