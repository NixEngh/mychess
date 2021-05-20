package game.piece;

import game.ChessBoard;
import grid.Location;
import javafx.scene.image.Image;

import java.util.List;
import java.util.Set;

/**
 * Interface for chessPieces to be used in this chess version
 */
public interface IPiece {
    /**
     * Method to get all valid moves in a List, EmptyList if none.
     * @return
     */
    public Set<Location> getPossibleMovesIgnoreCheck();

    /**
     * Get symbol representation of the piece
     * @return
     */
    public char getSymbol();

    /**
     * Get the pieces color
     * @return
     */
    public PieceColor getColor();

    /**
     * returns the current location of a piece
     * @return
     */
    public Location getLocation();


    public Image getImage ();
}
