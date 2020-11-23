package sample.dataModel;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

public class CompositeFigure extends Pane implements PrintableFigure {
    private final List<SingleField> compositeSingleFields;
    private final List<Color> colors;
    private final Random random = new Random();

    public CompositeFigure() {
        this.compositeSingleFields = new ArrayList<>();
        this.colors = new ArrayList<>();
        colors.add(Color.ORANGE);
        colors.add(Color.YELLOW);
        colors.add(Color.GREEN);
        colors.add(Color.BROWN);
        colors.add(Color.RED);
        colors.add(Color.BLUE);
        colors.add(Color.PURPLE);
    }

    public List<SingleField> getCompositeSingleFields() {
        return compositeSingleFields;
    }

    public void addToComposite(Figure figure) {
        Paint fill1 = figure.singleFields.get(0).getFill();
//        Paint fill = tempSingleField.getFill();
//        System.out.println("kolor "+fill);
        List<SingleField> tempSingleFields = transformCompositeIntoSingleFieldsAccordToMods(figure);
        compositeSingleFields.addAll(tempSingleFields);
        getChildren().addAll(tempSingleFields);

        for (SingleField tempSingleField : tempSingleFields) {
            tempSingleField.setFill(fill1);
        }
    }


    public List<SingleField> transformCompositeIntoSingleFieldsAccordToMods(Figure figure) {
        List<SingleField> singleFieldsTempList = new ArrayList<>();
        for (SingleField singleField : figure.getSingleFields()) {
            int newX = singleField.getGridX() + figure.getModX();
            int newY = singleField.getGridY() + figure.getModY();
            singleFieldsTempList.add(new SingleField(newX, newY));
        }
        return singleFieldsTempList;
    }


    public boolean isAnyRowFilled() {
        for (int y = 0; y < BoardGame.BOARD_HEIGHT; y++) {
            if (isSingleRowFilled(y)) {
                return true;
            }
        }
        return false;
    }

    public boolean isSingleRowFilled(int row) {
        int sum = 0;
        for (int x = 0; x < BoardGame.BOARD_WIDTH; x++) {
            SingleField tempSingleField = new SingleField(x, row);
            if (compositeSingleFields.contains(tempSingleField)) {
                sum++;
            }
        }
        return sum == 10;
    }

    public void removeLineFields(int y) {
        List<SingleField> singleFieldsToRemove = new ArrayList<>();
        for (int x = 0; x < BoardGame.BOARD_WIDTH; x++) {
            SingleField tempSingleField = new SingleField(x, y);
            singleFieldsToRemove.add(tempSingleField);
        }

        compositeSingleFields.removeAll(singleFieldsToRemove);
        getChildren().removeAll(singleFieldsToRemove);
    }

    public List<Integer> getListNumberOfFullRow() {
        List<Integer> nowa = new ArrayList<>();
        for (int y = 0; y < BoardGame.BOARD_HEIGHT; y++) {
            if (isSingleRowFilled(y)) {
                nowa.add(y);
            }
        }
        return nowa;
    }

    public void removeFullRows() {
        List<Integer> listOfNumberOfFullRow = getListNumberOfFullRow();
        if (!listOfNumberOfFullRow.isEmpty()) {
            for (Integer integer : listOfNumberOfFullRow) {
                removeLineFields(integer);
            }
        }
        for (Integer integer : listOfNumberOfFullRow) {
            lowerRowsAboveErasedLine(integer);
        }

    }

    public void lowerRowsAboveErasedLine(int y) {
        for (SingleField singleField : compositeSingleFields) {
            if (singleField.getGridY() < y) {
                Node node = getChildren().filtered(x -> x.equals(singleField)).get(0); // TODO: 23.11.2020 jakis bład z get indexoutofbound
                getChildren().remove(node);
                int newSingleFieldY = singleField.getGridY() + 1;
                singleField.setGridY(newSingleFieldY);
                singleField.updatePosition(0, 0);
            }
        }
        getChildren().clear();
        getChildren().addAll(compositeSingleFields);
    }

    @Override
    public boolean isSingleFieldOn(int x, int y) {
        for (SingleField singleField : compositeSingleFields) {
            if (singleField.getGridX() == x && singleField.getGridY() == y) {
                return true;
            }
        }
        return false;
    }

    @Override
    public String toString() {
        return "CompositeFigure{" +
                "figury=" + compositeSingleFields +
                '}';
    }

}
