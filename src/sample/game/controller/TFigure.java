package sample.game.controller;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class TFigure extends Figure {
    public TFigure() {
        getFieldsForPositionUp();
        setSpecificColor();
    }

    public void setSpecificColor() {
        for (SingleField singleField : singleFields) {
            singleField.setFill(Color.RED);
        }
    }

    @Override
    public List<SingleField> getFieldsForPositionUp() {
        List<SingleField> singleFields = new ArrayList<>();
        singleFields.add(new SingleField(3, 1));
        singleFields.add(new SingleField(4, 0));
        singleFields.add(new SingleField(4, 1));
        singleFields.add(new SingleField(4, 2));
        return singleFields;
    }

    @Override
    public List<SingleField> getFieldsForPositionDown() {
        List<SingleField> singleFields = new ArrayList<>();
        singleFields.add(new SingleField(3, 0));
        singleFields.add(new SingleField(3, 1));
        singleFields.add(new SingleField(3, 2));
        singleFields.add(new SingleField(4, 1));
        return singleFields;
    }

    @Override
    public List<SingleField> getFieldsForPositionLeft() {
        List<SingleField> singleFields = new ArrayList<>();
        singleFields.add(new SingleField(3, 0));
        singleFields.add(new SingleField(4, 0));
        singleFields.add(new SingleField(4, 1));
        singleFields.add(new SingleField(5, 0));
        return singleFields;
    }

    @Override
    public List<SingleField> getFieldsForPositionRight() {
        List<SingleField> singleFields = new ArrayList<>();
        singleFields.add(new SingleField(3, 1));
        singleFields.add(new SingleField(4, 0));
        singleFields.add(new SingleField(4, 1));
        singleFields.add(new SingleField(5, 1));
        return singleFields;
    }
}
