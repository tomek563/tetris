package sample.dataModel;

import java.util.Random;

public class FigureFactory {

    public FigureFactory() {
    }

    public Figure getRandomFigura() {
        Random random = new Random();
        int num = random.nextInt(7);
        Figure figure;
        switch (num) {
            case 0:
                figure = new SquareFigure();
                break;
            case 1:
                figure = new LongFigure();
                break;
            case 2:
                figure = new LFigure1();
                break;
            case 3:
                figure = new LFigure2();
                break;
            case 4:
                figure = new TFigure();
                break;
            case 5:
                figure = new ZFigure1();
                break;
            case 6:
                figure = new ZFigure2();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + num);
        }
        return figure;
    }

}
