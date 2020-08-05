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
    public static boolean isHora(TextField string){
        String str = string.getText();
        if (str.matches("[0-23]\\d")){
            int i = Integer.parseInt(str);
            if (i >= 0 && i <=23){
                return true;
            }
        }
        return false;
    }
    public static boolean isMinuto(TextField string){
        String str = string.getText();
        if (str.matches("[0-59]\\d")){
            int i = Integer.parseInt(str);
            if (i >= 0 && i <=59){
                return true;
            }
        }
        return false;
    }

}
