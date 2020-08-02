package view.util;

public class  VerificaInputs {

    public static boolean isDouble( String input){
        return input.matches("-?\\d+|\\d+.\\d+");
    }

    public static boolean isVazio(String input){
        return !input.equals("");
    }
}
