package game;

import game.piece.King;
import game.piece.Pawn;
import game.piece.Piece;
import game.piece.Rook;
import grid.GridDirection;
import grid.Location;
import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.BorderPane;

import javafx.stage.Stage;

import java.util.*;

public class Chess extends Application {
    private Piece selectedPiece;
    private ChessBoard board;
    private GUIGrid guiGrid;

    private Set<Location> possibleLocs = new HashSet<>();
    private Set<Location> underAttack = new HashSet<>();



    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chess");
        BorderPane pane = new BorderPane();

        board = new ChessBoard();
        board.setupBoard();

        guiGrid = new GUIGrid(this, board);
        guiGrid.setAlignment(Pos.CENTER);

        pane.setCenter(guiGrid);
        pane.autosize();
        guiGrid.reDrawBoard();

        Button restartButton = new Button("Restart");
        restartButton.setStyle("-fx-font-size: 20");
        restartButton.setOnAction(e -> {
            board.restart();
            guiGrid.reDrawBoard();
        });

        guiGrid.add(restartButton, 4, 9, 1, 1);

        Scene scene = new Scene(pane);
        primaryStage.setMinHeight(Square.getSIZE()*9);
        primaryStage.setMinWidth(Square.getSIZE()*8);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public void makeMove(Location location) {
        if(underAttack.contains(location)) {
            if(selectedPiece instanceof King) {
                ((King) selectedPiece).getPossibleMovesIgnoreCheck();
                if(((King) selectedPiece).getCastlingLocations().contains(location)) {
                    Piece rook = ((King) selectedPiece).getCastlingRook(location);
                    GridDirection dirToKing = rook.getLocation().directionTo(selectedPiece.getLocation());
                    board.makeMove(rook.getLocation(), location.getNeighbor(dirToKing));
                    board.nextColor();
                    rook.makeMove(location.getNeighbor(dirToKing));
                }
            }

            Location from = selectedPiece.getLocation();
            board.makeMove(selectedPiece.getLocation(), location);
            selectedPiece.makeMove(location);

            clearPreviousSelectedPiece(from);

            guiGrid.reDrawBoard();
            rePaintBoard();
        }
    }

    public void handleMouseEntered(Location location){
        if(board.get(location) != null) {
            if(board.get(location).getColor() == board.getCurrentColor()){
                possibleLocs = board.get(location).getPossibleMoves();
            }
        } else {
            possibleLocs = Collections.emptySet();
        }
        rePaintBoard();
    }

    public void handleMouseClicked(Location location) {
        Piece piece = board.get(location);
        if(hasSelectedPiece()) {
            if(piece == selectedPiece) {
                clearPreviousSelectedPiece(location);
            }
            else if(piece == null) {
                makeMove(location);
            }
            else if(piece.getColor() == board.getCurrentColor()) {
                selectPiece(piece);
            }
            else if(piece.getColor() != board.getCurrentColor()) {
                makeMove(location);
            }

        } else {
            if (piece != null) {
                if(piece.getColor() == board.getCurrentColor()){
                    selectPiece(piece);
                }
            }
        }
    }

    public void selectPiece(Piece piece) {
        if(hasSelectedPiece()) {
            guiGrid.removeFrame(selectedPiece.getLocation());
        }
        selectedPiece = piece;
        underAttack = piece.getPossibleMoves();
        rePaintBoard();
        guiGrid.drawFrame(selectedPiece.getLocation());

    }

    public boolean hasSelectedPiece() {
        return selectedPiece != null;
    }


    public void clearPreviousSelectedPiece(Location frameLocation) {

        guiGrid.removeFrame(frameLocation);
        selectedPiece = null;
        underAttack = Collections.emptySet();
    }
    /**
     * Paints all the squares in underattack or possiblelocs based on whether a piece is selected
     */
    public void rePaintBoard() {
        guiGrid.clearBoardPaint();
        Set<Location> setToPaint = (hasSelectedPiece()) ? underAttack : possibleLocs;

        guiGrid.paintLocsBackground(setToPaint);

    }



}
