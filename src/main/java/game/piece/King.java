package game.piece;

import game.ChessBoard;
import grid.Location;

import java.util.*;

public class King extends Piece{
    private static final char SYMBOL = 'K';
    private boolean hasMoved;
    private HashMap<Location, Piece> castlingLocationToRook = new HashMap<>();
    private List<Rook> rooks = new ArrayList<>();


    public King(ChessBoard board, Location startLocation, PieceColor color) {
        this(board, startLocation, color, false);
    }
    public King(ChessBoard board, Location startLocation, PieceColor color, boolean hasMoved) {
        super(board, startLocation, color);
        this.IMAGE_PATH = "pieces/" + color.getColorString() + "/king.png";
        this.hasMoved = hasMoved;
    }

    @Override
    public Set<Location> getPossibleMovesIgnoreCheck() {
        castlingLocationToRook.clear();

        Set<Location> ret = new HashSet<>();
        for(Location loc : getLocation().allNeighbors()) {
            if(getBoard().isOnGrid(loc)) {
                if(getBoard().get(loc) == null) {
                    ret.add(loc);
                }
                else if (isEnemyPiece(getBoard().get(loc))){
                    ret.add(loc);
                }
            }
        }
        if(!hasMoved) {
            for(Piece rook : rooks) {
                if(getBoard().canCastle(this, rook)){

                    Location castlingLocation = getLocation().getNeighbor(getLocation().directionTo(rook.getLocation()), 2);
                    castlingLocationToRook.put(castlingLocation, rook);
                    ret.add(castlingLocation);
                }
            }
        }
        return ret;

    }
    public void addToRooks(Rook rook) {
        rooks.add(rook);
    }
    public Piece getCastlingRook(Location location) {
        return castlingLocationToRook.get(location);
    }
    public Set<Location> getCastlingLocations() {
        return castlingLocationToRook.keySet();

    }

    @Override
    public char getSymbol() {
        return SYMBOL;
    }

    @Override
    public Piece copyForBoard(ChessBoard board) {
        return new King(board, getLocation(), getColor(), hasMoved);
    }
}
