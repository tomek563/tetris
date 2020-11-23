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
        getChildren().addAll(this.singleFields);
        this.modX = 0;
        this.modY = 0;
    }
    public Figure(Figure figure) {
        this.singleFields = new ArrayList<>(figure.singleFields);
        getChildren().addAll(this.singleFields);
        this.modX = figure.modX;
        this.modY = figure.modY;
    }

    public void updateSingleFields() {
        for (SingleField singleField : singleFields) {
            singleField.updatePosition(modX, modY);
        }
    }

    public void move(KeyCode keyCode, CompositeFigure compositeFigure) {
        if (keyCode == KeyCode.LEFT) {
            moveFigureLeft();
        } else if (keyCode.equals(KeyCode.RIGHT)) {
            moveFigureRight();
        } else if (keyCode.equals(KeyCode.SPACE)) {
            rotate();
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

    public void changeActualPosition() {
        if (position.equals(Position.UP)) {
            setActualPosition(Position.RIGHT);
        } else if (position.equals(Position.RIGHT)) {
            setActualPosition(Position.DOWN);
        } else if (position.equals(Position.DOWN)) {
            setActualPosition(Position.LEFT);
        } else {
            setActualPosition(Position.UP);
        }
    }

    public void rotate() {
        if (!isFigureOnBorderOnLeftoOrRightSideOfBoard()) { // TODO: 25.06.2020 problem jak sie obraca to wychodzi poza plansze
            changeActualPosition();
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
