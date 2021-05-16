package game;

import javafx.application.Application;
import javafx.geometry.Pos;
import javafx.scene.Scene;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.GridPane;

import javafx.stage.Stage;

public class Chess extends Application {

    @Override
    public void start(Stage primaryStage) throws Exception {
        primaryStage.setTitle("Chess");
        BorderPane pane = new BorderPane();
        GridPane board = new GameBoard(8, 8);
        board.setAlignment(Pos.CENTER);
        pane.setCenter(board);

        Scene scene = new Scene(pane);
        primaryStage.setMinHeight(scene.getHeight());
        primaryStage.setMinWidth(scene.getWidth());
        primaryStage.setScene(scene);
        primaryStage.show();
    }
}
