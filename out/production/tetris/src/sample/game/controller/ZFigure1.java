package sample.game.controller;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class ZFigure1 extends Figure {
    public ZFigure1() {
        getFieldsForPositionUp();
        setSpecificColor();
    }

    public void setSpecificColor() {
        for (SingleField singleField : singleFields) {
            singleField.setFill(Color.BLUE);
        }
    }

    @Override
    public List<SingleField> getFieldsForPositionUp() {
        List<SingleField> singleFields = new ArrayList<>();
        singleFields.add(new SingleField(3, 0));
        singleFields.add(new SingleField(3, 1));
        singleFields.add(new SingleField(4, 1));
        singleFields.add(new SingleField(4, 2));
        return singleFields;
    }

    @Override
    public List<SingleField> getFieldsForPositionLeft() {
        List<SingleField> singleFields = new ArrayList<>();
        singleFields.add(new SingleField(3, 1));
        singleFields.add(new SingleField(4, 0));
        singleFields.add(new SingleField(4, 1));
        singleFields.add(new SingleField(5, 0));
        return singleFields;
    }

    @Override
    public List<SingleField> getFieldsForPositionRight() {
        return getFieldsForPositionLeft();
    }

    @Override
    public List<SingleField> getFieldsForPositionDown() {
        return getFieldsForPositionUp();
    }

}

