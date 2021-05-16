package game;

import javafx.geometry.Pos;
import javafx.scene.control.Label;

public class Square extends Label {
    private int x, y;
    private ChessBoard board;
    private static final String blackColor = "-fx-background-color: #a79a8a;";
    private static final String whiteColor = "-fx-background-color: white;";
    private static final String redColor = "-fx-background-color: red;";
    private String originalColor;
    Square(ChessBoard board, int x, int y) {
        this.board = board;
        this.x = x;
        this.y = y;
        setAlignment(Pos.CENTER);
        originalColor = (x+y)%2==0 ? whiteColor : blackColor;
        setStyle(originalColor);

        setMinSize(50, 50);
        setMaxSize(50, 50);

        setOnMouseEntered(e -> onMouseEntered());
        setOnMouseExited(e -> onMouseExited());
    }
    private void onMouseEntered() {
        setStyle(redColor);
    }
    private void onMouseExited() {
        setStyle(originalColor);
    }
}
