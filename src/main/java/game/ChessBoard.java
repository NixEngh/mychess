package game;

import game.piece.IPiece;
import grid.Grid;
import grid.Location;

public class ChessBoard extends Grid<IPiece> {

    private String currentColor = "white";

    public ChessBoard(int rows, int columns) {
        super(rows, columns);
        locations().forEach(loc -> add(new Square(this, loc.col, loc.row), loc.col, loc.row));
    }

    /**
     *
     */
    public void makeMove() {

    }

    /**
     * If a location is empty and king is not checked in moving.
     * @param to
     * @return
     */
    public boolean canMove(IPiece piece, Location to){
        if(!isOnGrid(to)) {
            return false;
        }
        ChessBoard copy = copy();
        copy.makeMove();

        return true;
    }
    /**
     * Returns whether the king is in check.
     * @return
     */
    public boolean isCheck(){
        return false;
    }

    @Override
    public ChessBoard copy() {
        ChessBoard copy = new ChessBoard(numRows(), numColumns());
        for (Location loc : locations()) {
            if(get(loc) != null) {
                copy.set(loc, get(loc).copyForBoard(copy));
            }
        }
        return copy;
    }



}
