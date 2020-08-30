package view.util.sharedCodes;

import java.text.NumberFormat;
import java.util.Locale;
import javafx.application.Platform;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;
import javafx.geometry.NodeOrientation;
import javafx.scene.control.TextField;

/**
 * Simple Currency Field for JavaFX - modified
 * @author Gustavo
 * @version 1.0
 */
public class CurrencyField extends NumberField{

    private NumberFormat format;
    private SimpleDoubleProperty amount;

    public CurrencyField(){this(new Locale("pt","BR"), 0.0);};

    public CurrencyField(Locale locale) {
        this(locale, 0.00);
    }

    public CurrencyField(Locale locale, Double initialAmount) {
        super(NumberFormat.getCurrencyInstance(locale), initialAmount);
        setNodeOrientation(NodeOrientation.RIGHT_TO_LEFT);
    }
}