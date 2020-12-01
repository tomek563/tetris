package sample.dataModel;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class SquareFigure extends Figure {
    public SquareFigure() {
        getFieldsForPositionUp();
        setSpecificColor();
    }

    public void setSpecificColor() {
        for (SingleField singleField : singleFields) {
            singleField.setFill(Color.BROWN);
        }
    }

    @Override
    public List<SingleField> getFieldsForPositionUp() {
        List<SingleField> singleFields = new ArrayList<>();
        singleFields.add(new SingleField(4, 0));
        singleFields.add(new SingleField(5, 1));
        singleFields.add(new SingleField(4, 1));
        singleFields.add(new SingleField(5, 0));
        return singleFields;
    }

    @Override
    public List<SingleField> getFieldsForPositionDown() {
        return getFieldsForPositionUp();
    }

    @Override
    public List<SingleField> getFieldsForPositionLeft() {
        return getFieldsForPositionUp();
    }

    @Override
    public List<SingleField> getFieldsForPositionRight() {
        return getFieldsForPositionUp();
    }
}
