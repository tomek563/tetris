package sample.dataModel;

import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Objects;
import java.util.Random;

public class SingleField extends Rectangle {
    private int gridX;
    private int gridY;
    private  int actualGridX;
    private  int actualGridY;

    public SingleField(int gridX, int gridY) {
        this.gridX = gridX;
        this.gridY = gridY;
        updatePosition(0,0);
        setWidth(Game.FIELD_SIZE);
        setHeight(Game.FIELD_SIZE);
        actualGridX = gridX;
        actualGridY = gridY;
    }
    public void updatePosition(int modX, int modY) {
        actualGridX = gridX + modX;
        actualGridY = gridY + modY;
        setX((actualGridX)*Game.FIELD_SIZE);
        setY((actualGridY)*Game.FIELD_SIZE);

    }

    public int getGridX() {
        return gridX;
    }

    public int getGridY() {
        return gridY;
    }
    public void setColor(Color color) {
        setFill(color);
    }

    public void setGridX(int gridX) {
        this.gridX = gridX;
    }

    public void setGridY(int gridY) {
        this.gridY = gridY;

    }

    public boolean isOnField(SingleField other){
        return actualGridX == other.actualGridX && actualGridY == other.actualGridY;
    }

    @Override
    public String toString() {
        return "SingleField{" +
                "X=" + gridX +
                ", Y=" + gridY +
                '}';
    }


    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        SingleField that = (SingleField) o;
        return gridX == that.gridX &&
                gridY == that.gridY;
    }

    @Override
    public int hashCode() {
        return Objects.hash(gridX, gridY);
    }
}
