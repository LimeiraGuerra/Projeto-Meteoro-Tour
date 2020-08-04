package view.util;

import javafx.scene.control.TextField;

public class TextFieldValidator {

    public static boolean isDouble(TextField string){
        String str = string.getText();
        return str.matches("-?\\d+|\\d+.\\d+");
    }

    public static boolean isDate(TextField string){
        String str = string.getText();
        return str.equals("");
    }
}
