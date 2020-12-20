package sample.game.controller;

import javafx.scene.Node;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

public class CompositeFigure extends Pane implements PrintableFigure {
    private final List<SingleField> compositeSingleFields;

    public CompositeFigure() {
        this.compositeSingleFields = new ArrayList<>();
    }

    public List<SingleField> getCompositeSingleFields() {
        return compositeSingleFields;
    }

    public void addToComposite(Figure figure) {
        Paint color = figure.getFigureColor();
        List<SingleField> tempSingleFields = transformCompositeIntoSingleFieldsAccordToMods(figure);
        compositeSingleFields.addAll(tempSingleFields);
        getChildren().addAll(tempSingleFields);
        for (SingleField tempSingleField : tempSingleFields) {
            tempSingleField.setFill(color);
        }
    }

    private List<SingleField> transformCompositeIntoSingleFieldsAccordToMods(Figure figure) {
        List<SingleField> singleFieldsTempList = new ArrayList<>();
        for (SingleField singleField : figure.getSingleFields()) {
            int newX = singleField.getGridX() + figure.getModX();
            int newY = singleField.getGridY() + figure.getModY();
            singleFieldsTempList.add(new SingleField(newX, newY));
        }
        return singleFieldsTempList;
    }


    private boolean isSingleRowFilled(int row) {
        int sum = 0;
        for (int x = 0; x < BoardGame.BOARD_WIDTH; x++) {
            SingleField tempSingleField = new SingleField(x, row);
            if (compositeSingleFields.contains(tempSingleField)) {
                sum++;
            }
        }
        return sum == 10;
    }

    private void removeLineFields(int y) {
        List<SingleField> singleFieldsToRemove = new ArrayList<>();
        for (int x = 0; x < BoardGame.BOARD_WIDTH; x++) {
            SingleField tempSingleField = new SingleField(x, y);
            singleFieldsToRemove.add(tempSingleField);
        }
        compositeSingleFields.removeAll(singleFieldsToRemove);
        getChildren().removeAll(singleFieldsToRemove);
    }

    private List<Integer> getListOfNumberOfFullRows() {
        List<Integer> fullRows = new ArrayList<>();
        for (int y = 0; y < BoardGame.BOARD_HEIGHT; y++) {
            if (isSingleRowFilled(y)) {
                fullRows.add(y);
            }
        }
        return fullRows;
    }

    public int handleCompletingRow() {
        List<Integer> listOfNumberOfFullRow = getListOfNumberOfFullRows();
        removeFullRows(listOfNumberOfFullRow);
        lowerRows(listOfNumberOfFullRow);
        return listOfNumberOfFullRow.size();
    }

    private void removeFullRows(List<Integer> listOfNumberOfFullRow) {
        if (!listOfNumberOfFullRow.isEmpty()) {
            for (Integer integer : listOfNumberOfFullRow) {
                removeLineFields(integer);
            }
        }
    }

    private void lowerRows(List<Integer> listOfNumberOfFullRow) {
        for (Integer integer : listOfNumberOfFullRow) {
            lowerRowsAboveErasedLine(integer);
        }
    }

    private void lowerRowsAboveErasedLine(int y) {
        for (SingleField singleField : compositeSingleFields) {
            if (singleField.getGridY() < y) {
                Node node = getChildren().filtered(x -> x.equals(singleField)).get(0);
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
