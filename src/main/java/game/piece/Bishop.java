package game.piece;

import game.ChessBoard;
import game.Square;
import grid.GridDirection;
import grid.Location;
import javafx.scene.image.Image;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Bishop extends Piece{
    private static final char SYMBOL = 'B';

    public Bishop(ChessBoard board, Location startLocation, PieceColor color) {
        super(board, startLocation, color);
        this.IMAGE_PATH = "pieces/" + color.getColorString() + "/bishop.png";

    }

    @Override
    public Set<Location> getPossibleMoves() {
        Set<Location> ret = new HashSet<>();

        for(GridDirection dir : GridDirection.FOUR_DIRECTIONS){
            ret.addAll(getStraightLine(getLocation(), dir.turnRight45()));
        }
        return ret;
    }


    @Override
    public char getSymbol() {
        return SYMBOL;
    }


    @Override
    public Piece copyForBoard(ChessBoard board) {
        return new Pawn(board, getLocation(), getColor());
    }
}
