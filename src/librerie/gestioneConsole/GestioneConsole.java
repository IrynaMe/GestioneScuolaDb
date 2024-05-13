package librerie.gestioneConsole;

import gestioneScuola.Entita;

import gestioneScuola.menu.*;

import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.regex.Pattern;

public class GestioneConsole {

    private Scanner sc = new Scanner(System.in);
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_ORANGE = "\u001B[38;5;208m";
    public static final String ANSI_RESET = "\u001B[0m";



    //i metodi return null se input errato, valore se corretto
    public Integer dammiIntero(String msgShow, String msgRetry, String msgError,
                               String msgSuccess, int tentativi, Integer rangeMin, Integer rangeMax) {
        String input;
        Integer result = null;
        Integer numMin = rangeMin;
        Integer numMax = rangeMax;
        if (numMin < 0 || numMin > numMax) {
            System.out.println("Range non valido");
        } else {
            StringBuilder regexBuilder = new StringBuilder("\\b(").append(rangeMin).append("|").append(rangeMax).append("|");
            for (int i = numMin + 1; i < numMax; i++) {
                regexBuilder.append(i).append("|");
            }
            regexBuilder.append(")\\b");
            String regexInt = regexBuilder.toString();
            do {
                System.out.println(msgShow);
                input = sc.nextLine().trim();
                if (!input.isEmpty() && !Pattern.matches(regexInt, input)) {
                    System.out.print(ANSI_ORANGE);
                    System.out.println(msgRetry);
                    System.out.print(ANSI_RESET);
                    tentativi--;
                }
            } while ((input.isEmpty() || !Pattern.matches(regexInt, input)) && tentativi != 0);
            if (tentativi == 0) {
                System.out.print(ANSI_RED);
                System.out.println(msgError);
                System.out.print(ANSI_RESET);
            } else {
                result = Integer.parseInt(input);
                System.out.println(ANSI_GREEN);
                System.out.println(msgSuccess);
                System.out.println(ANSI_RESET);
            }
        }
        return result;
    }


    public String dammiStringa(String msgShow, String msgRetry, String msgError,
                               String msgSuccess, int tentativi, int rangeMinLength, int rangeMaxLength) {
        String input = null;
        if (rangeMinLength < 0 || rangeMaxLength < 0 || rangeMinLength > rangeMaxLength) {
            System.out.println("Range di lunghezza non valido");
        } else {
            String regexString = "^[a-zA-Z0-9]{" + rangeMinLength + "," + rangeMaxLength + "}$";
            do {
                System.out.println(msgShow);
                input = sc.nextLine().trim();
                if (!input.isEmpty() && !Pattern.matches(regexString, input)) {
                    System.out.println(ANSI_ORANGE);
                    System.out.println(msgRetry);
                    System.out.println(ANSI_RESET);
                    tentativi--;
                }
            } while (input.isEmpty() || (!input.isEmpty() && !Pattern.matches(regexString, input)) && tentativi != 0);
            if (tentativi == 0) {
                System.out.println(ANSI_RED);
                System.out.println(msgError);
                System.out.println(ANSI_RESET);
                input = null;
            } else {
                System.out.println(ANSI_GREEN);
                System.out.println(msgSuccess);
                System.out.println(ANSI_RESET);
            }
        }
        return input;
    }


    public String dammiSesso(String msgShow, String msgRetry, String msgError,
                             String msgSuccess, int tentativi) {
        String input = null;
        String regexSesso = "^[mfMF]$";
        do {
            System.out.println(msgShow);
            input = sc.nextLine().trim();
            if (!Pattern.matches(regexSesso, input)) {
                System.out.println(ANSI_ORANGE);
                System.out.println(msgRetry);
                System.out.println(ANSI_RESET);
                tentativi--;
            }
        } while (input == null || !Pattern.matches(regexSesso, input) && tentativi != 0);
        if (tentativi == 0) {
            System.out.println(ANSI_RED);
            System.out.println(msgError);
            System.out.println(ANSI_RESET);
            input = null;
        } else {
            System.out.println(ANSI_GREEN);
            System.out.println(msgSuccess);
            System.out.println(ANSI_RESET);
        }

        return input;
    }

    public String dammiLettera(String msgShow, String msgRetry, String msgError,
                               String msgSuccess, int tentativi, String letteraInizio, String letteraFine) {
        int asciiInizio = (int) letteraInizio.charAt(0);
        int asciiFine = (int) letteraFine.charAt(0);
        String input = null;
        if (asciiInizio > asciiFine || letteraInizio.length() > 1 || letteraFine.length() > 1) {
            System.out.println(ANSI_RED);
            System.out.println("Range di lettere non valido");
            System.out.println(ANSI_RESET);
        } else {
            String regexString = "^([" + letteraInizio.toLowerCase() + "-" + letteraFine.toLowerCase() + letteraInizio.toUpperCase() + "-" + letteraFine.toUpperCase() + "])$";
            do {
                System.out.println(msgShow);
                input = sc.nextLine().trim();
                if (input != null && !Pattern.matches(regexString, input)) {
                    System.out.println(ANSI_ORANGE);
                    System.out.println(msgRetry);
                    System.out.println(ANSI_RESET);
                    input = null;
                    tentativi--;
                }
            } while (input == null && tentativi != 0);
            if (input == null) {
                System.out.println(ANSI_RED);
                System.out.println(msgError);
                System.out.println(ANSI_RESET);
            } else {
                System.out.println(ANSI_GREEN);
                System.out.println(msgSuccess);
                System.out.println(ANSI_RESET);
            }
        }
        return input;
    }


    public LocalDate dammiData(String msgShow, String msgRetry, String msgError,
                               String msgSuccess, int tentativi, int minYear, int maxYear) {
        String input = null;
        LocalDate data = null;
        String minYearString = String.valueOf(minYear);
        String maxYearString = String.valueOf(maxYear);

        if (minYear > maxYear || minYearString.length() != 4 || maxYearString.length() != 4) {
            System.out.println("Range di anni non valido");
        } else {
            String regexData = "^(0[1-9]|1\\d|2[0-8])-(0[1-9]|1[0-2])-(19\\d{2}|20[0-2]\\d|202[0-4])$";
            do {
                System.out.println(msgShow);
                input = sc.nextLine().trim();
                if (input != null && !Pattern.matches(regexData, input)) {
                    System.out.println(ANSI_ORANGE);
                    System.out.println(msgRetry);
                    System.out.println(ANSI_RESET);
                    input = null;
                    tentativi--;
                }
            } while (input == null && tentativi != 0);
            if (input == null) {
                System.out.println(ANSI_RED);
                System.out.println(msgError);
                System.out.println(ANSI_RESET);
            } else {
                DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy");
                data = LocalDate.parse(input, formatter);
                System.out.println(ANSI_GREEN);
                System.out.println(msgSuccess);
                System.out.println(ANSI_RESET);
            }
        }
        return data;
    }

    public LocalTime dammiOra(String msgShow, String msgRetry, String msgError,
                              String msgSuccess, int tentativi) {

       /* if (minHour.length() != 2 || maxHour.length() != 2) {
            System.out.println("Range di ore non valido, deve essere formsto hh (2 caratteri)");
        }*/
        String input = null;
        LocalTime localTime = null;
        String regexTime = "^([01]\\d|2[0-3]):([0-5]\\d):([0-5]\\d)$";
        do {
            System.out.println(msgShow);
            input = sc.nextLine().trim();
            if (input != null && !Pattern.matches(regexTime, input)) {
                System.out.println(ANSI_ORANGE);
                System.out.println(msgRetry);
                System.out.println(ANSI_RESET);
                input = null;
                tentativi--;
            }
        } while (input == null && tentativi != 0);
        if (input == null) {
            System.out.println(ANSI_RED);
            System.out.println(msgError);
            System.out.println(ANSI_RESET);
        } else {
            localTime = LocalTime.parse(input);
            System.out.println(ANSI_GREEN);
            System.out.println(msgSuccess);
            System.out.println(ANSI_RESET);
        }
        return localTime;
    }

    public String dammiMail(String msgShow, String msgRetry, String msgError,
                            String msgSuccess, int tentativi) {
        String input = null;
        String regexMail = "^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-zA-Z]{2,}$";
        do {
            System.out.println(msgShow);
            input = sc.nextLine().trim();
            if (!Pattern.matches(regexMail, input)) {
                System.out.println(ANSI_ORANGE);
                System.out.println(msgRetry);
                System.out.println(ANSI_RESET);
                tentativi--;
                input=null;
            }
        } while (!Pattern.matches(regexMail, input) && tentativi != 0);

        if (input == null) {
            System.out.println(ANSI_RED);
            System.out.println(msgError);
            System.out.println(ANSI_RESET);
        } else {
            System.out.println(ANSI_GREEN);
            System.out.println(msgSuccess);
            System.out.println(ANSI_RESET);
        }

        return input;
    }


    public String dammiCodiceFiscale(String msgShow, String msgRetry, String msgError,
                                     String msgSuccess, int tentativi) {
        String input = null;
        String regexCodiceFiscale = "^[A-Z]{6}\\d{2}[A-Z]\\d{2}[A-Z]\\d{3}[A-Z]$";

        do {
            System.out.println(msgShow);
            input = sc.nextLine().trim();
            if (!Pattern.matches(regexCodiceFiscale, input)) {
                System.out.println(ANSI_ORANGE);
                System.out.println(msgRetry);
                System.out.println(ANSI_RESET);
                tentativi--;
            }
        } while (input == null || !Pattern.matches(regexCodiceFiscale, input) && tentativi != 0);


        if (input == null) {
            System.out.println(ANSI_RED);
            System.out.println(msgError);
            System.out.println(ANSI_RESET);
        } else {
            System.out.println(ANSI_GREEN);
            System.out.println(msgSuccess);
            System.out.println(ANSI_RESET);
        }

        return input;
    }

    public void chiudiScanner() {
        sc.close();
    }


}//
