package sample.dataModel;

import javafx.scene.paint.Color;

import java.util.ArrayList;
import java.util.List;

public class LFigure1 extends Figure {
    public LFigure1() {
        getFieldsForPositionUp();
        setSpecificColor();
    }
    public LFigure1(Figure figure) {
        super(figure);
    }

    public void setSpecificColor() {
        for (SingleField singleField : singleFields) {
            singleField.setFill(Color.YELLOW);
        }
    }

    @Override
    public List<SingleField> getFieldsForPositionUp() {
        List<SingleField> singleFields = new ArrayList<>();
        singleFields.add(new SingleField(3,0));
        singleFields.add(new SingleField(4,0));
        singleFields.add(new SingleField(4,1));
        singleFields.add(new SingleField(4,2));
        return singleFields;
    }

    @Override
    public List<SingleField> getFieldsForPositionDown() {
        List<SingleField> singleFields = new ArrayList<>();
        singleFields.add(new SingleField(5,2));
        singleFields.add(new SingleField(4,0));
        singleFields.add(new SingleField(4,1));
        singleFields.add(new SingleField(4,2));
        return singleFields;
    }

    @Override
    public List<SingleField> getFieldsForPositionLeft() {
        List<SingleField> singleFields = new ArrayList<>();
        singleFields.add(new SingleField(3,1));
        singleFields.add(new SingleField(3,0));
        singleFields.add(new SingleField(4,0));
        singleFields.add(new SingleField(5,0));
        return singleFields;
    }

    @Override
    public List<SingleField> getFieldsForPositionRight() {
        List<SingleField> singleFields = new ArrayList<>();
        singleFields.add(new SingleField(4,0));
        singleFields.add(new SingleField(3,1));
        singleFields.add(new SingleField(2,1));
        singleFields.add(new SingleField(4,1));
        return singleFields;
    }
}
