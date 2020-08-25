package view.util;

import javafx.print.Printer;
import javafx.util.StringConverter;

public class ConverterPrinterName extends StringConverter<Printer> {
    @Override
    public String toString(Printer object) {
        return object.getName();
    }

    @Override
    public Printer fromString(String string) {
        return null;
    }
}
