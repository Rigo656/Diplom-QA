package data;

import com.github.javafaker.Faker;

import java.util.Locale;

public class DataHelper {
    private static Faker faker = new Faker(new Locale("ru"));

    private DataHelper() {
    }

    public static String getFirstCardNumber() {
        return "4444 4444 4444 4441";
    }

    public static String getSecondCardNumber() {
        return "4444 4444 4444 4442";
    }

    public static String getStatusFirstCard() {
        return "APPROVED";
    }

    public static String getStatusSecondCard() {
        return "DECLINED";
    }

    public static String getValidMonth() {
        return "05";
    }

    public static String getValidYear() {
        return "23";
    }

    public static String getValidOwner() {
        return faker.name().fullName();
    }

    public static String getLetterЁ() {
        return "Парочкин Семён";
    }

    public static String getDoubleNameOfTheOwner() {
        return "Ремарк Эрих-Мария";
    }

    public static String getLettersSymbolsTextInTheOwner() {
        return "il%!";
    }

    public static String getOwnerFieldEmpty() {
        return "";
    }

    public static String getValidCvc() {
        return "133";
    }

    public static String getCvcZero() {
        return "000";
    }

    public static String getLettersSymbolsTextInTheCvc() {
        return "%!";
    }

    public static String getCvcFieldEmpty() {
        return "";
    }

    public static String getLessThan16DigitsInTheCard() {
        return "2233 5566 7788 99";
    }

    public static String get16ZerosInTheCard() {
        return "0000 0000 0000 0000";
    }

    public static String getLettersSymbolsText() {
        return "!№;%:?()_+";
    }

    public static String getEmptyFieldInTheCard() {
        return "";
    }

    public static String getLettersSymbolsTextInTheMonth() {
        return "%!";
    }

    public static String getMonthNumberMore12() {
        return "18";
    }

    public static String getMonthFieldEmpty() {
        return "";
    }

    public static String getMonthZero() {
        return "00";
    }

    public static String getYearFieldPrevious() {
        return "21";
    }

    public static String getMoreThan6YearsOfTheCurrentYear() {
        return "29";
    }

    public static String getYearZero() {
        return "00";
    }

    public static String getLettersSymbolsTextInTheYear() {
        return "%!";
    }

    public static String getYearFieldEmpty() {
        return "";
    }

    public static String getNameOwnerInLatin() {
        return "Petrov Alexander";
    }

    public static String getNameAndPatronymicWithSmallLetterInTheOwner() {
        return "петров александр";
    }

    public static String getMoreThan30CharactersInTheOwner() {
        return "абабабаббабабабабаабаббабабабабабабабабабабабаббабабабабабабабаббабабабабабабабабабабабабабаббабабабабабаббааб";
    }

}
