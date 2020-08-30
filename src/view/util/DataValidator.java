package view.util;

import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;

public class DataValidator {

    public static boolean isDouble(String str){
        return str.matches("-?\\d+|\\d+.\\d+");
    }

    public static boolean isInteger(String num){
        return num.matches("^\\d+$");
    }

    public static boolean isCpf(String cpf){
        return cpf.matches("^([0-9]{3}?){3}?[0-9]{2}$");
    }

    public static boolean isPhone(String num){
        return num.matches("^\\d{10,11}$");
    }

    public static boolean isTime (String time) {
        return time.matches("^([2][0-3]|[0-1][0-9]):[0-5][0-9]$");
    }

    public static boolean isHora(String str){
        if (str.matches("[0-23]\\d")){
            int i = Integer.parseInt(str);
            if (i >= 0 && i <=23){
                return true;
            }
        }
        return false;
    }

    public static boolean isMinuto(String str){
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

    public static String doubleWithDecimalsFormater(double value){
        DecimalFormat df = new DecimalFormat("####0.00");
        return df.format(value);
    }

    public static String formatCurrencyView(double value){
        return "R$ ".concat(DataValidator.doubleWithDecimalsFormater(value));
    }

    public static Date LocalDateConverter(LocalDate localDate){
        return localDate != null ? java.sql.Date.valueOf(localDate) : null;
    }

    public static String txtInputVerifier(String str){
        if (str == null) return null;
        str = str.trim();
        return str.isEmpty() ? null : str;
    }

    public static String rgVerifier(String rg){
        /*RG tem padrão diferente para cada estado, e não é obrigatório*/
        rg = txtInputVerifier(rg);
        if (rg == null) return "";
        if (rg.length() < 14) return rg;
        return null;
    }

    public static String cpfVerifier(String cpf){
        if (isCpf(cpf))
            return cpf;
        return null;
    }

    public static String phoneVerifier(String phone){
        if (isPhone(phone))
            return phone;
        return null;
    }

    public static String verifyTime(String time){
        if (isTime(time))
            return time;
        return "";
    }
}
