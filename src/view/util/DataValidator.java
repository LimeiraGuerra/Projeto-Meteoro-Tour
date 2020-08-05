package view.util;

import javafx.scene.control.TextField;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;

public class DataValidator {

    public static boolean isDouble(TextField string){
        String str = string.getText();
        return str.matches("-?\\d+|\\d+.\\d+");
    }

    public static boolean isInteger(String num){
        return num.matches("^\\d+$");
    }

    public static boolean isCpf(String cpf){
        return cpf.matches("^([0-9]{3}\\.?){3}-?[0-9]{2}$");
    }

    public static boolean isRg(String rg){
        //todo Não sei padrão de rg ¯\_(ツ)_/¯
        return rg.matches(".+");
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

    public static String checkNumId(String id){
        id = txtInputVerifier(id);
        if (id != null && isInteger(id))
            return id;
        return null;
    }

    public static String doubleToCurrencyFormater(double value){
        DecimalFormat df = new DecimalFormat("####0.00");
        return df.format(value);
    }

    public static String formatCurrencyView(double value){
        return "R$ ".concat(DataValidator.doubleToCurrencyFormater(value));
    }

    public static Date LocalDateConverter(LocalDate localDate){
        return localDate != null ? java.sql.Date.valueOf(localDate) : null;
    }

    public static String txtInputVerifier(String str){
        str = str.trim();
        return str.isEmpty() ? null : str;
    }

    public static String rgVerifier(String rg){
        rg = txtInputVerifier(rg);
        if (rg != null && isRg(rg))
            return rg;
        return null;
    }

    public static String cpfVerifier(String cpf){
        cpf = txtInputVerifier(cpf);
        if (cpf != null && isCpf(cpf))
            return cpf;
        return null;
    }
}
