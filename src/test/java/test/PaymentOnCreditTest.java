package test;

import com.codeborne.selenide.logevents.SelenideLogger;
import data.DataHelper;
import data.DataHelperSQL;
import io.qameta.allure.Description;
import io.qameta.allure.selenide.AllureSelenide;
import lombok.val;
import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import page.CreditPage;
import page.StartPage;

import static com.codeborne.selenide.Selenide.open;
import static data.DataHelperSQL.cleanDataBase;
import static org.junit.jupiter.api.Assertions.assertEquals;

public class PaymentOnCreditTest {
    CreditPage creditPage = new CreditPage();
    StartPage startPage = new StartPage();

    @BeforeEach
    void CleanDataBaseAndOpenWeb() {
        cleanDataBase();
        startPage = open("http://localhost:8080", StartPage.class);
        startPage.buyPaymentByCredit();
    }

    @BeforeAll
    static void setUpAll() {
        SelenideLogger.addListener("allure", new AllureSelenide());
    }

    @AfterAll
    static void tearDownAll() {
        SelenideLogger.removeListener("allure");
    }

    //Позитивные сценарии

    @Description( value = "Проверка первой карты")
    @Test
    void shouldApproveFirstCard() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutSuccessfulPayment();
        val expected = DataHelper.getStatusFirstCard();
        val actual = DataHelperSQL.getPurchaseOnCreditCard();
        assertEquals(expected, actual);
    }

    @Description( value = "Владельца с буквой Ё в имени/фамилии")
    @Test
    void shouldApproveOwnerNameWithTheLetter() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getLetterЁ();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutSuccessfulPayment();
        val expected = DataHelper.getStatusFirstCard();
        val actual = DataHelperSQL.getPurchaseOnCreditCard();
        assertEquals(expected, actual);
    }

    @Description( value = "Владелец с двойным именем")
    @Test
    void shouldApproveDoubleNameOfTheOwner() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getDoubleNameOfTheOwner();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutSuccessfulPayment();
        val expected = DataHelper.getStatusFirstCard();
        val actual = DataHelperSQL.getPurchaseOnCreditCard();
        assertEquals(expected, actual);
    }

    @Description( value = "Имя владельца латиницей")
    @Test
    void shouldApproveNameOwnerInLatin() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getNameOwnerInLatin();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutSuccessfulPayment();
        val expected = DataHelper.getStatusFirstCard();
        val actual = DataHelperSQL.getPurchaseOnCreditCard();
        assertEquals(expected, actual);
    }

    @Description( value = "Проверка второй карты")
    @Test
    void shouldRejectSecondCard() {
        val cardNumber = DataHelper.getSecondCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutUnsuccessfulPaymentRefused();
        val expected = DataHelper.getStatusSecondCard();
        val actual = DataHelperSQL.getPurchaseOnCreditCard();
        assertEquals(expected, actual);
    }


    //Негативные сценарии

    @Description( value = "Номер карты содержащей меньше 16 цифр")
    @Test
    void shouldLessThan16DigitsInTheCard() {
        val cardNumber = DataHelper.getLessThan16DigitsInTheCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutIncorrectDataFormat();
    }

    @Description( value = "Карта с номером из 16 нулей")
    @Test
    void should16ZerosInTheCard() {
        val cardNumber = DataHelper.get16ZerosInTheCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutUnsuccessfulPaymentRefused();
    }

    @Description( value = "Ввести в поле карты спецсимволов, иероглифов и т.д.")
    @Test
    void shouldLettersSymbolsTextInTheCard() {
        val cardNumber = DataHelper.getLettersSymbolsText();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutIncorrectDataFormat();
    }

    @Description( value = "Оставить поле карты пустым")
    @Test
    void shouldEmptyFieldInTheCard() {
        val cardNumber = DataHelper.getEmptyFieldInTheCard();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutIncorrectDataFormat();
    }

    @Description( value = "Ввести в поле месяца спецсимволов, иероглифов и т.д.")
    @Test
    void shouldLettersSymbolsTextInTheMonth() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getLettersSymbolsTextInTheMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutIncorrectDataFormat();
    }

    @Description( value = "Ввести в поле месяца значения больше 12")
    @Test
    void shouldMonthNumberMore12() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getMonthNumberMore12();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutIncorrectCardExpirationDate();
    }

    @Description( value = "Оставить поле месяца пустым")
    @Test
    void shouldMonthFieldEmpty() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getMonthFieldEmpty();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutIncorrectDataFormat();
    }

    @Description( value = "Ввести в поле года прошедший год")
    @Test
    void shouldFieldPastYear() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getYearFieldPrevious();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutCardExpiration();
    }

    @Description( value = "Ввести в поле года значение на 6 больше текущего года")
    @Test
    void shouldYearMoreThan6YearsOfTheCurrentYear() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getMoreThan6YearsOfTheCurrentYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutIncorrectCardExpirationDate();
    }

    @Description( value = "Ввести в поле года нулевое значение")
    @Test
    void shouldYearZero() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getYearZero();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutCardExpiration();
    }

    @Description( value = "Ввести в поле года спецсимволов, иероглифов и т.д.")
    @Test
    void shouldLettersSymbolsTextInTheYear() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getLettersSymbolsTextInTheYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutIncorrectDataFormat();
    }

    @Description( value = "Оставить поле года пустым")
    @Test
    void shouldYearFieldEmpty() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getYearFieldEmpty();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutIncorrectDataFormat();
    }

    @Description( value = "Ввести в поле владельца ФИ с маленькой буквы")
    @Test
    void shouldNameNndPatronymicWithSmallLetterInTheOwner() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getNameAndPatronymicWithSmallLetterInTheOwner();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutIncorrectDataFormat();
    }

    @Description( value = "Ввести в поле владельца более 30 символов")
    @Test
    void shouldMoreThan30CharactersInTheOwner() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getMoreThan30CharactersInTheOwner();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutIncorrectDataFormat();
    }

    @Description( value = "Ввести в поле владельца спецсимволов, иероглифов и т.д.")
    @Test
    void shouldLettersSymbolsTextInTheOwner() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getLettersSymbolsTextInTheOwner();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutIncorrectDataFormat();
    }

    @Description( value = "Оставить поле владельца пустым")
    @Test
    void shouldOwnerFieldEmpty() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getOwnerFieldEmpty();
        val cvc = DataHelper.getValidCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutTheRequiredField();
    }

    @Description( value = "Ввести в поле CVC нули")
    @Test
    void shouldCvcZero() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getCvcZero();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutIncorrectDataFormat();
    }

    @Description( value = "Ввести в поле CVC спецсимволов, иероглифов и т.д.")
    @Test
    void shouldLettersSymbolsTextInTheCvc() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getLettersSymbolsTextInTheCvc();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutIncorrectDataFormat();
    }

    @Description( value = "Оставить поле CVC пустым")
    @Test
    void shouldEmptyFieldInTheCvc() {
        val cardNumber = DataHelper.getFirstCardNumber();
        val month = DataHelper.getValidMonth();
        val year = DataHelper.getValidYear();
        val owner = DataHelper.getValidOwner();
        val cvc = DataHelper.getCvcFieldEmpty();
        creditPage.fillOutLine(cardNumber, month, year, owner, cvc);
        creditPage.messageAboutIncorrectDataFormat();
    }
}
