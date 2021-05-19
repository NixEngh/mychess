package game;

import game.piece.Piece;
import grid.Location;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.StackPane;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

public class GUIGrid extends GridPane {
    private ChessBoard board;

    private Map<Location, Square> locationSquareMap = new HashMap<>();
    private Map<Location, StackPane> locationStackPaneMap = new HashMap<>();

    public GUIGrid(Chess game, ChessBoard board) {
        this.board = board;
        for (Location loc : board.locations()) {
            Square square = new Square(game, board, loc);
            StackPane stackPane = new StackPane(square);
            add(stackPane, loc.col, loc.row);


            locationSquareMap.put(loc, square);
            locationStackPaneMap.put(loc, stackPane);
        };
    }
    public void drawToBoard (Location location, Image image) {
        ImageView view = new ImageView(image);
        Square square = locationSquareMap.get(location);
        square.setGraphic(view);
        getChildren();
    }
    /**
     * draws every piece in this.board to the board.
     */
    public void reDrawBoard() {
        locationSquareMap.values().forEach(square -> {square.setGraphic(null);});
        for(Location loc : board.locations()) {
            if(board.get(loc) != null) { drawToBoard(loc, board.get(loc).getImage()); }
        }
    }

    /**
     * Paints a square green or red based on whether the location contains a piece.
     * @param loc the location to be painted
     */
    public void paintSquare(Location loc) {
        locationSquareMap.get(loc).setBackGroundColor((board.get(loc) == null) ? SquareColor.possibleMoveColor : SquareColor.underAttackColor);
    }

    /**
     * Goes through a list of locations and paints them per the rules of GUIGrid::paintSquare
     * @param locs the locations to be painted
     */
    public void paintLocsBackground (Set<Location> locs) {
        clearBoardPaint();
        for(Location loc : locs) {
            paintSquare(loc);
        }
    }

    /**
     * Paints every square its original color
     */
    public void clearBoardPaint() {
        for(Square square : locationSquareMap.values()) {
            square.setBackGroundColor(square.getOriginalColor());
        }
    }

    /**
     * Adds the frame picture to a locations StackPane
     * @param loc the location to be drawn on
     */
    public void drawFrame(Location loc) {
        locationStackPaneMap.get(loc).getChildren().add(new ImageView(new Image("square-png-25133.png", Square.getSIZE(), Square.getSIZE(), true, false)));
    }

    /**
     * Removes a frame from a location
     * @param loc
     */
    public void removeFrame(Location loc) {
        StackPane stackPane = locationStackPaneMap.get(loc);
        Square square = locationSquareMap.get(loc);
        stackPane.getChildren().clear();
        stackPane.getChildren().add(square);
    }

}
