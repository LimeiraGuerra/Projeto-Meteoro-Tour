package view.util.sharedCodes;

import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.scene.control.TextField;

import java.text.NumberFormat;
import java.util.Locale;


/**
 * Simple Currency Field for JavaFX - modified for other numbers formats
 * @author Gustavo
 * @version 1.0
 */
public class NumberField extends TextField {

    private NumberFormat format;
    private SimpleDoubleProperty amount;
    private String labelR = "";
    private Double initialAmount;

    public NumberField(){}

    public NumberField(NumberFormat format, Double initialAmount) {
        this.initialAmount = initialAmount;
        amount = new SimpleDoubleProperty(this, "amount", initialAmount);
        this.format = format;
        setText(format.format(initialAmount));

        // Remove selection when textfield gets focus
        focusedProperty().addListener((ObservableValue<? extends Boolean> observable, Boolean oldValue, Boolean newValue) -> {
            Platform.runLater(() -> {
                setCaretToEnd();
            });
        });

        // Listen the text's changes
        textProperty().addListener(new ChangeListener<String>() {

            @Override
            public void changed(ObservableValue<? extends String> observable, String oldValue, String newValue) {
                formatText(newValue);
            }
        });
    }

    /**
     * Get the current amount value
     * @return Total amount
     */
    public Double getAmount() {
        return amount.get();
    }

    /**
     * Property getter
     * @return SimpleDoubleProperty
     */
    public SimpleDoubleProperty amountProperty() {
        return this.amount;
    }

    /**
     * Change the current amount value
     * @param newAmount
     */
    public void setAmount(Double newAmount) {
        if(newAmount >= 0.0) {
            amount.set(newAmount);
            formatText(format.format(newAmount));
        }
    }

    public void setAmount(int newAmount) {
        if(newAmount >= 0.0) {
            amount.set(newAmount);
            formatText(format.format(newAmount));
        }
    }

    /**
     * Set Currency format
     * @param locale
     */
    public void setCurrencyFormat(Locale locale) {
        format = NumberFormat.getCurrencyInstance(locale);
        formatText(format.format(getAmount()));
    }

    protected void formatText(String text) {
        if(text != null && !text.isEmpty()) {
            String plainText = text.replaceAll("[^0-9]", "");

            while(plainText.length() < 3) {
                plainText = "0" + plainText;
            }
            StringBuilder builder = new StringBuilder(plainText);
            builder.insert(plainText.length() - 2, ".");

            Double newValue = Double.parseDouble(builder.toString());
            amount.set(newValue);
            setText(format.format(newValue) + labelR);
        }
    }

    @Override
    public void deleteText(int start, int end) {
        StringBuilder builder = new StringBuilder(getText());
        builder.delete(start, end);
        formatText(builder.toString());
        selectRange(start, start);
        setCaretToEnd();
    }

    private void setCaretToEnd(){
        int lenght = getText().length() - labelR.length();
        selectRange(lenght, lenght);
        positionCaret(lenght);
    }

    public String getLabelR() {
        return labelR;
    }

    public void setLabelR(String labelR) {
        this.labelR = labelR;
    }

    @Override
    public void clear() {
        super.clear();
        this.setAmount(initialAmount);
    }

    public NumberFormat getFormat() {
        return format;
    }

    public void setFormat(NumberFormat format) {
        this.format = format;
    }
}

