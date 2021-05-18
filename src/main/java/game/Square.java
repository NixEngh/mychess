package game;

import grid.Location;
import javafx.geometry.Pos;
import javafx.scene.control.Label;
import javafx.scene.layout.StackPane;

public class Square extends Label {
    private Location location;
    private ChessBoard board;
    private static final int SIZE = 100;
    private SquareColor originalColor;


    Square(ChessBoard board, Location location) {
        this.board = board;
        this.location = location;
        setAlignment(Pos.CENTER);
        originalColor = (location.col+location.row)%2==0 ? SquareColor.whiteColor : SquareColor.blackColor;
        setStyle(originalColor.getColorCode());

        setMinSize(getSIZE(), getSIZE());
        setMaxSize(getSIZE(), getSIZE());

        setOnMouseClicked(e -> onMouseClicked());
        setOnMouseEntered(e -> onMouseEntered());
        setOnMouseExited(e -> onMouseExited());
    }

    public void setBackGroundColor (SquareColor color) {
        setStyle(color.getColorCode());
    }
    public SquareColor getOriginalColor() {
        return originalColor;
    }

    private void onMouseEntered() {
        board.handleHover(location);
    }


    private void onMouseClicked() {
        board.handleMouseClicked(location);
    }
    private void onMouseExited() {
        board.clearBoardPaint();
    }

    public static int getSIZE() {
        return SIZE;
    }

    public Location getLocation() {
        return location;
    }
}
