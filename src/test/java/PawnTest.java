import game.ChessBoard;
import game.piece.Pawn;
import game.piece.PieceColor;
import grid.GridDirection;
import grid.Location;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class PawnTest {
//    @Test
//    public void testEnPassantPossibleProperty() {
//        ChessBoard board = new ChessBoard(8, 8);
//        Pawn whitePawn = new Pawn(board, new Location(6, 3), PieceColor.LIGHT);
//        board.set(whitePawn.getLocation(), whitePawn);
//
//        Assertions.assertFalse(whitePawn.enPassantPossible());
//        Location newLoc = whitePawn.getLocation().getNeighbor(GridDirection.NORTH, 2);
//        board.makeMove(whitePawn, newLoc);
//        whitePawn.makeMove(newLoc);
//        Assertions.assertTrue(whitePawn.enPassantPossible());
//
//
//        board.nextColor();
//        newLoc = whitePawn.getLocation().getNeighbor(GridDirection.NORTH);
//        board.makeMove(whitePawn, newLoc);
//        whitePawn.makeMove(newLoc);
//
//        Assertions.assertFalse(whitePawn.enPassantPossible());
//
//    }
//
//    @Test
//    public void testEnPassantInPossibleMoves() {
//        ChessBoard board = new ChessBoard(8, 8);
//        Pawn whitePawn = new Pawn(board, new Location(6, 3), PieceColor.LIGHT);
//        Pawn blackPawn = new Pawn(board, new Location(4, 4), PieceColor.DARK);
//        board.set(whitePawn.getLocation(), whitePawn);
//        board.set(blackPawn.getLocation(),blackPawn);
//
//        board.makeMove(whitePawn, new Location(4, 3));
//        Assertions.assertTrue(blackPawn.getPossibleMoves().contains(new Location(5, 4)));
//    }
}
