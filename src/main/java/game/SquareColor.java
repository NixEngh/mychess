package game;

public enum SquareColor {
    blackColor("-fx-background-color: #a79a8a;"),
    whiteColor("-fx-background-color: white;"),
    underAttackColor("-fx-background-color: red;"),
    possibleMoveColor("-fx-background-color: green;");

    private final String colorCode;
    SquareColor(String colorCode){
        this.colorCode = colorCode;
    }

    public String getColorCode() {
        return colorCode;
    }

    public static SquareColor getOriginalColor(Square square) {
        return square.getOriginalColor();
    }
}
