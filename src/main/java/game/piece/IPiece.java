package game.piece;

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

    public char getSymbol();
}
