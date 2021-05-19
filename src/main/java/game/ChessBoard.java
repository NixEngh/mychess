package game;

import game.piece.*;
import grid.Grid;
import grid.Location;

import java.util.HashSet;
import java.util.Set;

public class ChessBoard extends Grid<Piece> {
    private PieceColor currentColor = PieceColor.LIGHT;
    private Set<Location> enPassantThreat = new HashSet<>();


    public ChessBoard(int rows, int columns) {
        super(rows, columns);
    }


    /**
     * Updates piece location
     */
    public void makeMove(Piece selectedPiece, Location to) {
        set(selectedPiece.getLocation(), null);
        set(to, selectedPiece);
        nextColor();
    }



    /**
     * If a location is empty and king is not checked in moving.
     * @param to
     *
     * @return
     */
    public boolean canMove(Piece piece, Location to){
        if(!isOnGrid(to)) {
            return false;
        }
        Piece pieceAtLocation = get(to);

        if(pieceAtLocation != null) {
            if (pieceAtLocation.getColor() == currentColor || pieceAtLocation instanceof King) {
                return false;
            }
        }

        //See if current king is under check after move, as this is illegal
        ChessBoard copy = copy();
        copy.makeMove(piece, to);

        return !copy.isCheck(currentColor);
    }
    /**
     * Returns whether the king is in check.
     * @return
     */
    public boolean isCheck(PieceColor color){
        return false;
    }

    /**
     * Creates a copy chessboard where every piece is also a new instance
     * @return
     */
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
    public PieceColor getCurrentColor() {
        return currentColor;
    }
    public void nextColor() {
        currentColor = (currentColor == PieceColor.LIGHT) ? PieceColor.DARK : PieceColor.LIGHT;
    }

    public void setupBoard() {
        for (int i = 0; i < 8; i++) {
            Pawn pawn = new Pawn(this,new Location(1,i), PieceColor.DARK);
            set(pawn.getLocation(), pawn);
        }
        for (int i = 0; i<8; i++) {
            Pawn pawn = new Pawn(this, new Location(6, i), PieceColor.LIGHT);
            set(pawn.getLocation(), pawn);
        }
        Location loc = new Location(0, 2);
        set(loc, new Bishop(this, loc, PieceColor.DARK));

        loc = new Location(0, 5);
        set(loc, new Bishop(this,loc, PieceColor.DARK));

        loc = new Location(7, 2);
        set(loc, new Bishop(this,loc, PieceColor.LIGHT));

        loc = new Location(7, 5);
        set(loc, new Bishop(this,loc, PieceColor.LIGHT));

        loc = new Location(0, 1);
        set(loc, new Knight(this, loc, PieceColor.DARK));

        loc = new Location(0, 6);
        set(loc, new Knight(this,loc, PieceColor.DARK));

        loc = new Location(7, 1);
        set(loc, new Knight(this,loc, PieceColor.LIGHT));

        loc = new Location(7, 6);
        set(loc, new Knight(this,loc, PieceColor.LIGHT));

        loc = new Location(0, 0);
        set(loc, new Rook(this, loc, PieceColor.DARK));

        loc = new Location(0, 7);
        set(loc, new Rook(this,loc, PieceColor.DARK));

        loc = new Location(7, 0);
        set(loc, new Rook(this,loc, PieceColor.LIGHT));

        loc = new Location(7, 7);
        set(loc, new Rook(this,loc, PieceColor.LIGHT));

        loc = new Location(0, 3);
        set(loc, new Queen(this, loc, PieceColor.DARK));

        loc = new Location(7, 3);
        set(loc, new Queen(this, loc, PieceColor.LIGHT));

    }

    public void watchForEnPassant(Location loc) {
        enPassantThreat.add(loc);
    }

    public Set<Location> getEnPassantThreat() {
        return enPassantThreat;
    }
    public void clearEnPassantThreat() {
        enPassantThreat.clear();
    }
}
