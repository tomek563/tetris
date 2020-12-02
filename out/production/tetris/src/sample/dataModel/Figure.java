package sample.dataModel;

import javafx.scene.input.KeyCode;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Paint;

import java.util.ArrayList;
import java.util.List;

public abstract class Figure extends Pane implements PrintableFigure {
    protected List<SingleField> singleFields;
    protected Position position = Position.UP;
    private int modX;
    private int modY;

    public Figure() {
        this.singleFields = new ArrayList<>();
        List<SingleField> tempSingleFields = getSingleFieldsOfUpdated(Position.UP);
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

    private void updateSingleFields() {
        for (SingleField singleField : singleFields) {
            singleField.updatePosition(modX, modY);
        }
    }

    public void move(KeyCode keyCode, CompositeFigure compositeFigure) {
        if (keyCode == KeyCode.LEFT && isFigureFuturePositionPossible(KeyCode.LEFT, position, compositeFigure)) {
            moveFigureLeft();
        } else if (keyCode.equals(KeyCode.RIGHT) && isFigureFuturePositionPossible(KeyCode.RIGHT, position, compositeFigure)) {
            moveFigureRight();
        } else if (keyCode.equals(KeyCode.SPACE)) {
            rotate(compositeFigure);
        } else if (keyCode.equals(KeyCode.DOWN)) {
            moveFigureDown(compositeFigure);
        }
        updateSingleFields();
    }

    private void moveFigureLeft() {
        if (!isSingleFieldOnVerticalBorder(0)) {
            modX--;
        }
    }

    private void moveFigureRight() {
        if (!isSingleFieldOnVerticalBorder(BoardGame.BOARD_WIDTH - 1)) {
            modX++;
        }
    }

    private void rotate(CompositeFigure compositeFigure) {
        if (!isFigureOnBorderOnLeftoOrRightSideOfBoard()) {
            changeActualPosition(compositeFigure);
            updateSingleFieldsByPosition();
        }
    }

    private void moveFigureDown(CompositeFigure compositeFigure) {
        if (!isSingleFieldOnHorizontalBorder() && !isCurrentFigureOnBorderWith(compositeFigure)) {
            modY++;
            updateSingleFields();
        }
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
            if (hasCompositePartOfFigure(singleFieldComposite, currentValuesOfFigure)) return true;
        }
        return false;
    }

    private List<SingleField> transformCurrentFigureIntoSingleFieldsListAccordToMods() {
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


    private boolean isFigureOnBorderOnLeftoOrRightSideOfBoard() {
        return isSingleFieldOnVerticalBorder(-1) || isSingleFieldOnVerticalBorder(BoardGame.BOARD_WIDTH);
    }

    private void changeActualPosition(CompositeFigure compositeFigure) {
        if (position.equals(Position.UP) && isFigureFuturePositionPossible(Position.RIGHT, compositeFigure)) {
            setActualPosition(Position.RIGHT);
        } else if (position.equals(Position.RIGHT) && isFigureFuturePositionPossible(Position.DOWN, compositeFigure)) {
            setActualPosition(Position.DOWN);
        } else if (position.equals(Position.DOWN) && isFigureFuturePositionPossible(Position.LEFT, compositeFigure)) {
            setActualPosition(Position.LEFT);
        } else if (position.equals(Position.LEFT) && isFigureFuturePositionPossible(Position.UP, compositeFigure)) {
            setActualPosition(Position.UP);
        }
    }

    private boolean isFigureFuturePositionPossible(Position position, CompositeFigure compositeFigure) {
        List<SingleField> tempSingleField = getSingleFieldsOfUpdated(position);
        for (SingleField singleField : tempSingleField) {
            singleField.setGridX(singleField.getGridX() + modX);
            singleField.setGridY(singleField.getGridY() + modY);
            if (isFigureWithinBorderOfBoardGame(singleField)
                    || hasCompositePartOfFigure(singleField, compositeFigure.getCompositeSingleFields())) {
                return false;
            }
        }
        return true;
    }

    private boolean isFigureFuturePositionPossible(KeyCode keyCode, Position position, CompositeFigure compositeFigure) { 
        int mod = getExtraMod(keyCode);
        List<SingleField> tempSingleField = getSingleFieldsOfUpdated(position);
        for (SingleField singleField : tempSingleField) {
            singleField.setGridX(singleField.getGridX() + modX + mod);
            singleField.setGridY(singleField.getGridY() + modY);
            if (hasCompositePartOfFigure(singleField, compositeFigure.getCompositeSingleFields())) {
                return false;
            }
        }
        return true;
    }

    private boolean isFigureWithinBorderOfBoardGame(SingleField singleField) {
        int x = singleField.getGridX();
        return x < 0 || x > BoardGame.BOARD_WIDTH - 1;
    }

    private boolean hasCompositePartOfFigure(SingleField singleField, List<SingleField> compositeSingleFields) {
        return compositeSingleFields.contains(singleField);
    }


    private int getExtraMod(KeyCode keyCode) {
        int mod;
        switch (keyCode) {
            case LEFT:
                mod = -1;
                break;
            case RIGHT:
                mod = 1;
                break;
            default:
                mod = 0;
                break;
        }
        return mod;
    }

    private void updateSingleFieldsByPosition() {
        singleFields.clear();
        List<SingleField> singleFields = getSingleFieldsOfUpdated(position);
        this.singleFields.addAll(singleFields);
        setSpecificColor();
        getChildren().clear();
        getChildren().addAll(this.singleFields);
    }


    private List<SingleField> getSingleFieldsOfUpdated(Position position) {
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

    public boolean isSingleFieldOn(int x, int y) {
        for (SingleField singleField : singleFields) {
            if (singleField.getGridX() + modX == x && singleField.getGridY() + modY == y) {
                return true;
            }
        }
        return false;
    }

    private boolean isSingleFieldOnVerticalBorder(int x) {
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

    public Paint getFigureColor() {
        return singleFields.get(0).getFill();
    }

    private void setActualPosition(Position position) {
        this.position = position;
    }

    public abstract void setSpecificColor();

    public abstract List<SingleField> getFieldsForPositionUp();

    public abstract List<SingleField> getFieldsForPositionDown();

    public abstract List<SingleField> getFieldsForPositionLeft();

    public abstract List<SingleField> getFieldsForPositionRight();


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
