package game.piece;

import game.ChessBoard;
import grid.Location;

import java.util.List;

/**
 * Interface for chessPieces to be used in this chess version
 */
public interface IPiece {
    /**
     * Method to get all valid moves in a List, EmptyList if none.
     * @return
     */
    public List<Location> getPossibleMoves();

    /**
     * Get symbol representation of the piece
     * @return
     */
    public char getSymbol();

    /**
     * Get the pieces color
     * @return
     */
    public String getColor();

    /**
     * returns the current location of a piece
     * @return
     */
    public Location getLocation();

    /**
     * This is when I realized that planning is a good idea.
     * This method creates a copy of the piece, so that when copying a ChessBoard, the original instances of IPiece are left untouched
     * @param board
     * @return The copy
     */
    public IPiece copyForBoard(ChessBoard board);
}
