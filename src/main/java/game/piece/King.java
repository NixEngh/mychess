package game.piece;

import game.ChessBoard;
import grid.Location;
import javafx.scene.image.Image;

import java.util.Set;

public class King extends Piece{

    public King(ChessBoard board, Location startLocation, PieceColor color) {
        super(board, startLocation, color);
    }
    @Override
    public Set<Location> getPossibleMoves() {
        return null;
    }

    @Override
    public char getSymbol() {
        return 0;
    }

    @Override
    public Image getImage() {
        return null;
    }

    @Override
    public Piece copyForBoard(ChessBoard board) {
        return null;
    }
}
