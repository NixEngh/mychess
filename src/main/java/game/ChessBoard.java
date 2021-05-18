package game;

import game.piece.*;
import grid.Grid;
import grid.Location;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;

import java.util.*;

public class ChessBoard extends Grid<Piece> {

    private PieceColor currentColor = PieceColor.LIGHT;
    private Map<Location, Square> locationSquareMap = new HashMap<>();
    private Set<Location> underAttack = new HashSet<>();
    private Piece selectedPiece;

    public ChessBoard(int rows, int columns) {
        super(rows, columns);

        for (Location loc : locations()) {
            Square square = new Square(this, loc);
            add(square, loc.col, loc.row);
            locationSquareMap.put(loc, square);
        };
    }
    public ChessBoard(int rows, int columns, Piece selectedPiece) {
        this(rows, columns);
        this.selectedPiece = selectedPiece;
    }

    /**
     *
     */
    public void makeMove(Location to) {

        if(underAttack.contains(to)) {
            set(selectedPiece.getLocation(), null);
            set(to, selectedPiece);
            selectedPiece.setLocation(to);
            nextColor();
            reDrawBoard();
            underAttack = Collections.emptySet();
        }

    }

    public void nextColor() {
        currentColor = (currentColor == PieceColor.LIGHT) ? PieceColor.DARK : PieceColor.LIGHT;
    }

    public void processHover (Location location){
        if(get(location) != null) {
            if(get(location).getColor() == currentColor){
                selectedPiece = get(location);
                underAttack = get(location).getPossibleMoves();
            }
        }
        rePaintBoard(location);


    }
    /**
     * Paints squares based on moves under attack from the selected piece
     */
    public void rePaintBoard(Location location) {
        for (Location loc : locations()) {
            Square square = locationSquareMap.get(loc);
            if(underAttack.contains(loc)) {
                if(get(loc) != null) {
                    square.setBackGroundColor(SquareColor.underAttackColor);
                } else {
                    square.setBackGroundColor(SquareColor.possibleMoveColor);
                }
            } else {
                square.setBackGroundColor(square.getOriginalColor());
            }
        }
    }
    /**
     * Draws pieces to the board
     */
    public void reDrawBoard() {
        locationSquareMap.values().forEach(square -> {square.setGraphic(null);});
        for(Location loc : locations()) {
            if(get(loc) != null) { drawToBoard(loc, get(loc).getImage()); }
        }
    }
    public void drawToBoard(Location loc, Image img) {
        ImageView view = new ImageView();
        view.setImage(img);
        locationSquareMap.get(loc).setGraphic(view);
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
        reDrawBoard();
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
        Piece pieceAtLocation = get(to);

        if(pieceAtLocation != null) {
            if (pieceAtLocation.getColor() == currentColor || pieceAtLocation instanceof King) {
                return false;
            }
        }

        //See if current king is under check after move, as this is illegal
        ChessBoard copy = copy();
        copy.makeMove(to);
        if(copy.isCheck(currentColor)) {
            return false;
        }

        return true;
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




}
