package view.util.sharedCodes;

import javafx.beans.property.SimpleStringProperty;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class IntegerField extends NumberField {

    public IntegerField(){this("", 0.0);};

    public IntegerField(String labelR, Double initialAmount) {
        super(NumberFormat.getIntegerInstance(), initialAmount);
        this.setLabelR(new SimpleStringProperty(this, "labelR", labelR).get());
    }

    @Override
    protected void formatText(String text) {
        if(text != null && !text.isEmpty()) {
            String plainText = text.replaceAll("[^0-9]", "");
            if (plainText.isEmpty()) plainText = "0";
            Integer newValue = Integer.parseInt(plainText);
            amountProperty().set(newValue);
            setText(getFormat().format(newValue) + this.getLabelR());
        }
    }
}
