package game;

import game.piece.*;
import grid.Grid;
import grid.Location;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ChessBoard extends Grid<Piece> {
    private PieceColor currentColor = PieceColor.LIGHT;
    private Set<Location> enPassantThreat = new HashSet<>();
    private HashMap<PieceColor, Piece> colorToKing = new HashMap<>();


    public ChessBoard(int rows, int columns) {
        super(rows, columns);
    }


    /**
     * Updates piece location
     */
    public void makeMove(Location from, Location to) {
        set(to, get(from));
        set(from, null);
        nextColor();
    }



    /**
     * If a location is empty and king is not checked in moving.
     * @param to
     *
     * @return
     */
    public boolean canMoveCheck(Location from, Location to){
        //See if current king is under check after move, as this is illegal
        ChessBoard copy = copy();
        HashMap<PieceColor, Piece> colorToKingCopy = new HashMap<>();

        copy.setColorToKing();
        copy.get(from).makeMove(to);
        copy.makeMove(from, to);

        return !copy.isKingInCheck(currentColor);
    }

    /**
     *
     * @param color the color of king to check
     * @return if the king is in check
     */
    public boolean isKingInCheck(PieceColor color){
        for (Location loc : locations()) {
            if (get(loc) == null) {
                continue;
            }
            else if (get(loc).getColor() != color) {
                if(get(loc).getPossibleMovesIgnoreCheck().contains(colorToKing.get(color).getLocation())) {
                    return true;
                }
            }
        }
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

    public void setColorToKing() {
        for(Location loc : locations()) {
            if(get(loc) instanceof King) {
                colorToKing.put(get(loc).getColor(), get(loc));
            }
        }

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

        loc = new Location(0, 4);
        colorToKing.put(PieceColor.DARK, new King(this, loc, PieceColor.DARK));
        set(loc, colorToKing.get(PieceColor.DARK));

        loc = new Location(7, 4);
        colorToKing.put(PieceColor.LIGHT, new King(this, loc, PieceColor.LIGHT));
        set(loc, colorToKing.get(PieceColor.LIGHT));

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
