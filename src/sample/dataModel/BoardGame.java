package sample.dataModel;

public class BoardGame {
    protected static final int BOARD_WIDTH = 10;
    protected static final int BOARD_HEIGHT = 15;

    public void print(PrintableFigure current, PrintableFigure composite) {
        for (int y = 0; y < BOARD_HEIGHT; y++) {
            for (int x = 0; x < BOARD_WIDTH; x++) {
                if (current.isSingleFieldOn(x, y) || composite.isSingleFieldOn(x, y)) {
                    System.out.print("X");
                } else {
                    System.out.print("*");
                }
            }
            System.out.println();
        }
    }
}

