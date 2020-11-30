package sample.dataModel;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public abstract class Figure extends Pane implements PrintableFigure {
    protected List<SingleField> singleFields;
    protected Position position = Position.UP;
    private int modX;
    private int modY;

    public Figure() {
        this.singleFields = new ArrayList<>();
        List<SingleField> tempSingleFields = setPosition(Position.UP);
        singleFields.addAll(tempSingleFields);
        getChildren().addAll(singleFields);
        this.modX = 0;
        this.modY = 0;
    }

    public Figure(Figure figure) {
        this.singleFields = new ArrayList<>(figure.singleFields);
        getChildren().addAll(singleFields);
        this.modX = figure.modX;
        this.modY = figure.modY;
    }

    public void updateSingleFields() {
        for (SingleField singleField : singleFields) {
            singleField.updatePosition(modX, modY);
        }
    }


    public void move(KeyCode keyCode, CompositeFigure compositeFigure) {
        // TODO: 23.11.2020
            if (keyCode == KeyCode.LEFT && isFuturePositionOfCompositePossible(KeyCode.LEFT ,position, compositeFigure)) {
                moveFigureLeft();
            } else if (keyCode.equals(KeyCode.RIGHT) && isFuturePositionOfCompositePossible(KeyCode.RIGHT,position,compositeFigure)) {
                moveFigureRight();
            } else if (keyCode.equals(KeyCode.SPACE) /*&& isFuturePositionOfCompositePossible(KeyCode.SPACE, position, compositeFigure)*/) { // TODO: 26.11.2020 obrot inaczej
                rotate(compositeFigure);
            } else if (keyCode.equals(KeyCode.DOWN)) {
                moveFigureDown(compositeFigure);
            }
        updateSingleFields();
    }



    public void moveFigureDown(CompositeFigure compositeFigure) {
        if (!isSingleFieldOnHorizontalBorder() && !isCurrentFigureOnBorderWith(compositeFigure)) {
            modY++;
            updateSingleFields();
        }
    }


    public void removeSingleField(SingleField singleField) {
        singleFields.remove(singleField);
    }

    public int getModX() {
        return modX;
    }

    public int getModY() {
        return modY;
    }


    public boolean isCurrentFigureOnBorderWith(CompositeFigure compositeFigure) {
        List<SingleField> currentValuesOfFigure = transformCurrentFigureIntoSingleFieldsListAccordToMods();
        for (SingleField singleFieldComposite : compositeFigure.getCompositeSingleFields()) {
            if (currentValuesOfFigure.contains(singleFieldComposite)) {
                return true;
            }
        }
        return false;
    }

    public List<SingleField> transformCurrentFigureIntoSingleFieldsListAccordToMods() {
        List<SingleField> singleFieldsTempList = new ArrayList<>();
        for (SingleField singleField : singleFields) {
            int newX = singleField.getGridX() + getModX();
            int newY = singleField.getGridY() + getModY() + 1;
            singleFieldsTempList.add(new SingleField(newX, newY));
        }
        return singleFieldsTempList;
    }

    public List<SingleField> getSingleFields() {
        return singleFields;
    }


    public void moveFigureLeft() {
        if (!isSingleFieldOnVerticalBorder(0)) {
            modX--;
        }
    }

    public void moveFigureRight() {
        if (!isSingleFieldOnVerticalBorder(BoardGame.BOARD_WIDTH - 1)) {
            modX++;
        }
    }

    public boolean isFigureOnBorderOnLeftoOrRightSideOfBoard() {
        return isSingleFieldOnVerticalBorder(-1) || isSingleFieldOnVerticalBorder(BoardGame.BOARD_WIDTH);
    }

    public void changeActualPosition(CompositeFigure compositeFigure) {
        if (position.equals(Position.UP) && isFuturePositionPossible(Position.RIGHT, compositeFigure)) {
            setActualPosition(Position.RIGHT);
        } else if (position.equals(Position.RIGHT) && isFuturePositionPossible(Position.DOWN, compositeFigure)) {
            setActualPosition(Position.DOWN);
        } else if (position.equals(Position.DOWN) && isFuturePositionPossible(Position.LEFT, compositeFigure)) {
            setActualPosition(Position.LEFT);
        } else if (position.equals(Position.LEFT) && isFuturePositionPossible(Position.UP, compositeFigure)) {
            setActualPosition(Position.UP);
        }
    }

    public void rotate(CompositeFigure compositeFigure) {
        if (!isFigureOnBorderOnLeftoOrRightSideOfBoard()) {
            changeActualPosition(compositeFigure);
            updateSingleFieldsByPosition();
        }
    }

    private void updateSingleFieldsByPosition() {
        singleFields.clear();
        List<SingleField> singleFields = setPosition(position);
        this.singleFields.addAll(singleFields);
        setSpecificColor();
        getChildren().clear();
        getChildren().addAll(this.singleFields);
    }


    public List<SingleField> setPosition(Position position) {
        List<SingleField> singleFields;
        switch (position) {
            case UP:
                singleFields = getFieldsForPositionUp();
                break;
            case RIGHT:
                singleFields = getFieldsForPositionRight();
                break;
            case DOWN:
                singleFields = getFieldsForPositionDown();
                break;
            case LEFT:
                singleFields = getFieldsForPositionLeft();
                break;
            default:
                throw new IllegalStateException("Unexpected value: " + position);
        }
        return singleFields;
    }

    public abstract void setSpecificColor();

    public abstract List<SingleField> getFieldsForPositionUp();

    public abstract List<SingleField> getFieldsForPositionDown();

    public abstract List<SingleField> getFieldsForPositionLeft();

    public abstract List<SingleField> getFieldsForPositionRight();

    public boolean isSingleFieldOn(int x, int y) {
        for (SingleField singleField : singleFields) {
            if (singleField.getGridX() + modX == x && singleField.getGridY() + modY == y) {
                return true;
            }
        }
        return false;
    }

    public boolean isSingleFieldOnVerticalBorder(int x) {
        for (SingleField singleField : singleFields) {
            if (singleField.getGridX() + modX == x) {
                return true;
            }
        }
        return false;
    }

    public boolean isSingleFieldOnHorizontalBorder() {
        for (SingleField singleField : singleFields) {
            if (singleField.getGridY() + modY == BoardGame.BOARD_HEIGHT - 1) {
                return true;
            }
        }
        return false;
    }


    public void setActualPosition(Position position) {
        this.position = position;
    }
    public boolean isFuturePositionPossible(Position position, CompositeFigure compositeFigure) { // TODO: 26.11.2020 dodac warunek do obrotu z funkcji ponizej
        System.out.println(position);
        System.out.println("singleFields przed "+singleFields);
        List<SingleField> tempSingleField = setPosition(position);
        for (SingleField singleField : tempSingleField) {
            singleField.setGridX(singleField.getGridX() + modX);
            singleField.setGridY(singleField.getGridY() + modY);
            System.out.println("singleField po "+singleField);
            int i = singleField.getGridX();
            if (i<0 || i>BoardGame.BOARD_WIDTH-1) {
                return false;
            }
            if (compositeFigure.getCompositeSingleFields().contains(singleField)) {
                return false;
            }
        }
        return true;
    }

    public boolean isFuturePositionOfCompositePossible(KeyCode keyCode, Position position, CompositeFigure compositeFigure) { // TODO: 23.11.2020
        int mod = getExtraMod(keyCode);
        List<SingleField> tempSingleField = setPosition(position);
        for (SingleField singleField : tempSingleField) {
            singleField.setGridX(singleField.getGridX() + modX + mod);
            singleField.setGridY(singleField.getGridY() + modY);
            if (compositeFigure.getCompositeSingleFields().contains(singleField)) {
                return false;
            }
        }
        return true;
    }

    private int getExtraMod(KeyCode keyCode) {
        int mod;
        switch(keyCode) {
            case LEFT: mod = -1;
                break;
            case RIGHT: mod =1;
                break;
            default:
                mod = 0;
                break;
        }
        return mod;
    }

    @Override
    public String toString() {
        return "Figure{" +
                "singleFields=" + singleFields +
                ", position=" + position +
                ", modX=" + modX +
                ", modY=" + modY +
                '}';
    }
}
