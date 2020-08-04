package view.util;

import javafx.scene.control.TextField;

public class StringValidator {
    public static boolean isCurrency(String str){

        return str.matches("-?\\d+|\\d+.\\d+");
    }

    public static boolean isDate(String str){
        return str.matches("");
    }
}
