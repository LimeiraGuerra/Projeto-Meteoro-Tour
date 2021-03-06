package view.util;
import java.text.DecimalFormat;
import java.time.LocalDate;
import java.util.Date;

public class DataValidator {

    public static boolean isInteger(String num){
        return num.matches("^\\d+$");
    }

    public static boolean isCpf(String cpf){
        if (cpf.equals("00000000000") ||
                cpf.equals("11111111111") ||
                cpf.equals("22222222222") || cpf.equals("33333333333") ||
                cpf.equals("44444444444") || cpf.equals("55555555555") ||
                cpf.equals("66666666666") || cpf.equals("77777777777") ||
                cpf.equals("88888888888") || cpf.equals("99999999999") ||
                (cpf.length() != 11))
            return(false);

        char dig10, dig11;
        int sm, i, r, num, peso;

        // Calculo do 1o. Digito Verificador
        sm = 0;
        peso = 10;
        for (i=0; i<9; i++) {
            num = (int)(cpf.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
        }

        r = 11 - (sm % 11);
        if ((r == 10) || (r == 11))
            dig10 = '0';
        else dig10 = (char)(r + 48);

        // Calculo do 2o. Digito Verificador
        sm = 0;
        peso = 11;
        for(i=0; i<10; i++) {
            num = (int)(cpf.charAt(i) - 48);
            sm = sm + (num * peso);
            peso = peso - 1;
        }

        r = 11 - (sm % 11);
        if ((r == 10) || (r == 11))
            dig11 = '0';
        else dig11 = (char)(r + 48);

        // Verifica se os digitos calculados conferem com os digitos informados.
        if ((dig10 == cpf.charAt(9)) && (dig11 == cpf.charAt(10))) return true;
        else return false;
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

    public static String placaVerifier(String placa){
        placa = txtInputVerifier(placa);
        if (placa == null) return placa;
        if (placa.length() == 7) return placa;
        return null;
    }

    public static String renavamVerifier(String renavam){
        renavam = txtInputVerifier(renavam);
        if (renavam == null) return renavam;
        if (renavam.length() == 11) return renavam;
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

    public static boolean verifyNumeric(Double d){
        return d != 0;
    }
}
