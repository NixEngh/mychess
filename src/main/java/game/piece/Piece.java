package game.piece;

import game.ChessBoard;
import grid.Location;

public abstract class Piece implements IPiece{
    private String color;
    private ChessBoard board;
    private final Location startLocation;
    private Location currentLocation;

    public Piece(ChessBoard board, Location startLocation, String color) {
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
    public String getColor() { return color; }
}
