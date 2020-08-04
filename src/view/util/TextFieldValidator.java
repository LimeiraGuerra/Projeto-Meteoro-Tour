package view.util;

import javafx.scene.control.TextField;

import java.time.LocalDate;
import java.util.Date;

public class TextFieldValidator {

    public static boolean isDouble(TextField string){
        String str = string.getText();
        return str.matches("-?\\d+|\\d+.\\d+");
    }

    public static boolean isDate(TextField string){
        //todo serve pra nada desse jeito
        String str = string.getText();
        return str.equals("");
    }

    public static Date LocalDateConverter(LocalDate localDate){
        return localDate != null ? java.sql.Date.valueOf(localDate) : null;
    }

    public static String txtInputVerifier(String str){
        str = str.trim();
        return str.isEmpty() ? null : str;
    }
}
