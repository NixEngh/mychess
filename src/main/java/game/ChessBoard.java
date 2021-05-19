package game;

import game.piece.*;
import grid.Grid;
import grid.Location;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.StackPane;

import java.util.*;

public class ChessBoard extends Grid<Piece> {

    private PieceColor currentColor = PieceColor.LIGHT;
    private Map<Location, Square> locationSquareMap = new HashMap<>();
    private Map<Location, StackPane> locationStackPaneMap = new HashMap<>();


    private Set<Location> underAttack = new HashSet<>();
    private Set<Location> possibleLocs = new HashSet<>();
    private Piece selectedPiece;

    public ChessBoard(int rows, int columns) {
        super(rows, columns);

        for (Location loc : locations()) {
            Square square = new Square(this, loc);
            StackPane stackPane = new StackPane(square);
            add(stackPane, loc.col, loc.row);


            locationSquareMap.put(loc, square);
            locationStackPaneMap.put(loc, stackPane);
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
            removeFrame(selectedPiece.getLocation());

            set(to, selectedPiece);
            selectedPiece.setLocation(to);

            nextColor();

            reDrawBoard();
            clearBoardPaint();

            underAttack = Collections.emptySet();
            possibleLocs = Collections.emptySet();

            clearSelectedPiece();
        }

    }

    public void nextColor() {
        currentColor = (currentColor == PieceColor.LIGHT) ? PieceColor.DARK : PieceColor.LIGHT;
    }

    public void handleHover(Location location){
        if(get(location) != null) {
            if(get(location).getColor() == currentColor){
//                selectedPiece = get(location);
                possibleLocs = get(location).getPossibleMoves();
            }
        } else {
            possibleLocs = Collections.emptySet();
        }
        rePaintBoard(location);
    }
    public void handleMouseClicked(Location location) {
        Piece piece = get(location);
        if(hasSelectedPiece()) {
            if(piece == selectedPiece) {
                removeFrame(location);
                clearSelectedPiece();
            } else if(piece == null) {
                makeMove(location);
            } else if(piece.getColor() == currentColor) {
                selectPiece(piece);
            } else if(piece.getColor() != currentColor) {
                makeMove(location);
            }

        } else {
            if (piece != null) {
                if(piece.getColor() == currentColor){
                    selectPiece(piece);
                }
            }
        }
    }
    /**
     * Paints squares based on moves under attack from the selected piece
     */
    public void rePaintBoard(Location location) {
        for (Location loc : locations()) {
            Square square = locationSquareMap.get(loc);

            Set<Location> setToPaint = (hasSelectedPiece()) ? underAttack : possibleLocs;
            if(setToPaint.contains(loc)) {
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
    public void clearBoardPaint() {
        for(Location loc : locations()) {
            Square square = locationSquareMap.get(loc);
            square.setBackGroundColor(square.getOriginalColor());
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

    public boolean hasSelectedPiece() {
        return selectedPiece != null;
    }
    public void clearSelectedPiece() {
        selectedPiece = null;
        underAttack = Collections.emptySet();
    }

    public void selectPiece(Piece piece) {
        if(hasSelectedPiece()) {
            removeFrame(selectedPiece.getLocation());
        }
        selectedPiece = piece;
        underAttack = possibleLocs;
        rePaintBoard(piece.getLocation());

        drawFrame(selectedPiece.getLocation());

    }

    public void drawFrame(Location loc) {
        locationStackPaneMap.get(loc).getChildren().add(new ImageView(new Image("square-png-25133.png", Square.getSIZE(), Square.getSIZE(), true, false)));
    }
    public void removeFrame(Location loc) {
        StackPane stackPane = locationStackPaneMap.get(loc);
        Square square = locationSquareMap.get(loc);
        stackPane.getChildren().clear();
        stackPane.getChildren().add(square);
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


        reDrawBoard();
    }

}
