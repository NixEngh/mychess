package game.piece;

public enum PieceColor {
    LIGHT("light"),
    DARK("dark");

    private final String colorString;
    PieceColor(String color) {
        this.colorString = color;
    }

    public String getColorString() {
        return colorString;
    }
}
