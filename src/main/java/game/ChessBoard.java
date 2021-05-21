package game;

import game.piece.*;
import grid.Grid;
import grid.GridDirection;
import grid.Location;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

public class ChessBoard extends Grid<Piece> {
    private PieceColor currentColor = PieceColor.LIGHT;
    private Set<Location> enPassantThreat = new HashSet<>();
    private HashMap<PieceColor, Piece> colorToKing = new HashMap<>();


    public ChessBoard() {
        super(8, 8);
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
     * Creates a copy of the chessboard and sees if the king of currentColor is put in check.
     * @param from a location from which a piece is to be moved
     * @param to the location to which the piece shall be moved
     *
     * @return  whether a move is possible with respect to isKingInCheck
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
     * Iterates through every piece and sees if piece.getPossibleMovesIgnoreCheck contains the location of the king of the given color
     * @param color the color of king to check
     * @return if the king is in check
     */
    public boolean isKingInCheck(PieceColor color){
        for (Location loc : locations()) {
            if (get(loc) == null) {
                continue;
            }
            else if (get(loc).getColor() != color) {
                if(!(get(loc) instanceof King) && get(loc).getPossibleMovesIgnoreCheck().contains(colorToKing.get(color).getLocation())) {
                    return true;
                }
            }
        }
        return false;
    }

    /**
     * Creates a copy chessboard where every piece is also a new instance
     * @return the copy
     */
    @Override
    public ChessBoard copy() {
        ChessBoard copy = new ChessBoard();
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
        //BISHOP
        Location loc = new Location(0, 2);
        set(loc, new Bishop(this, loc, PieceColor.DARK));

        loc = new Location(0, 5);
        set(loc, new Bishop(this,loc, PieceColor.DARK));

        loc = new Location(7, 2);
        set(loc, new Bishop(this,loc, PieceColor.LIGHT));

        loc = new Location(7, 5);
        set(loc, new Bishop(this,loc, PieceColor.LIGHT));

        //KNIGHT

        loc = new Location(0, 1);
        set(loc, new Knight(this, loc, PieceColor.DARK));

        loc = new Location(0, 6);
        set(loc, new Knight(this,loc, PieceColor.DARK));

        loc = new Location(7, 1);
        set(loc, new Knight(this,loc, PieceColor.LIGHT));

        loc = new Location(7, 6);
        set(loc, new Knight(this,loc, PieceColor.LIGHT));


        //QUEEN

        loc = new Location(0, 3);
        set(loc, new Queen(this, loc, PieceColor.DARK));

        loc = new Location(7, 3);
        set(loc, new Queen(this, loc, PieceColor.LIGHT));


        //KING

        loc = new Location(0, 4);

        King darkKing = new King(this, loc, PieceColor.DARK);
        colorToKing.put(PieceColor.DARK, darkKing);
        set(loc, colorToKing.get(PieceColor.DARK));

        loc = new Location(7, 4);
        King lightKing = new King(this, loc, PieceColor.LIGHT);
        colorToKing.put(PieceColor.LIGHT, lightKing);
        set(loc, colorToKing.get(PieceColor.LIGHT));


        //ROOK


        loc = new Location(0, 0);
        Rook rook = new Rook(this, loc, PieceColor.DARK);
        set(loc, rook);
        darkKing.addToRooks(rook);

        loc = new Location(0, 7);
        rook = new Rook(this, loc, PieceColor.DARK);
        set(loc, rook);
        darkKing.addToRooks(rook);


        loc = new Location(7, 0);
        rook = new Rook(this, loc, PieceColor.LIGHT);
        set(loc, rook);
        lightKing.addToRooks(rook);

        loc = new Location(7, 7);
        rook = new Rook(this,loc, PieceColor.LIGHT);
        set(loc,rook);
        lightKing.addToRooks(rook);


    }
    public void restart() {
        clear();
        setupBoard();
        currentColor = PieceColor.LIGHT;
    }

    /**
     * Checks: if the given king and rook have moved (by using hasMoved, meaning rook and king must be at their startlocations),
     * if there are any pieces between the rook and the king, and if the king is put in check or is blocked by check.
     * @param king
     * @param rook
     * @return
     */
    public boolean canCastle(Piece king, Piece rook) {
        if(king.hasMoved || rook.hasMoved) {
            return false;
        }
        if(isKingInCheck(currentColor)) return false;

        GridDirection directionToRook = king.getLocation().directionTo(rook.getLocation());
        Location testLocation = king.getLocation().getNeighbor(directionToRook);
        if(get(testLocation) != null || !canMoveCheck(king.getLocation(), king.getLocation().getNeighbor(directionToRook))) {
            return false;
        }
        testLocation = testLocation.getNeighbor(directionToRook);
        if(get(testLocation) != null || !canMoveCheck(king.getLocation(), testLocation)) return false;

        return true;

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
