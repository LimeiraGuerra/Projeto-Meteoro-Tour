package view.util.sharedCodes;

import javafx.beans.property.SimpleStringProperty;

import java.text.DecimalFormat;

public class DoubleField extends NumberField {

    public DoubleField(){this("", 0.0);};

    public DoubleField(String labelR, Double initialAmount) {
        super(new DecimalFormat("####0.00"), initialAmount);
        this.setLabelR(new SimpleStringProperty(this, "labelR", labelR).get());
    }
}