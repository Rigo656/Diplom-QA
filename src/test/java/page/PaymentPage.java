package page;

import com.codeborne.selenide.SelenideElement;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class PaymentPage {
    private SelenideElement cardNumberLine  = $("[placeholder='0000 0000 0000 0000']");
    private SelenideElement monthLine = $("[placeholder='08']");
    private SelenideElement yearLine = $("[placeholder='22']");
    private SelenideElement ownerLine = $$("[class='input__control']").get(3);
    private SelenideElement cvcLine = $("[placeholder='999']");
    private SelenideElement buttonContinue = $(byText("Продолжить"));

    private SelenideElement successfulSending = $(withText("Операция одобрена Банком."));
    private SelenideElement failedSending = $(withText("Ошибка! Банк отказал в проведении операции."));
    private SelenideElement incorrectFormat = $(withText("Неверный формат"));
    private SelenideElement incorrectCardExpirationDate = $(withText("Неверно указан срок действия карты"));
    private SelenideElement cardExpired = $(withText("Истёк срок действия карты"));
    private SelenideElement requiredLine = $(withText("Поле обязательно для заполнения"));

    public void fillOutLine(String cardNumber, String month, String year, String owner, String cvc) {
        cardNumberLine.setValue(cardNumber);
        monthLine.setValue(month);
        yearLine.setValue(year);
        ownerLine.setValue(owner);
        cvcLine.setValue(cvc);
        buttonContinue.click();
    }

    public void messageAboutSuccessfulPayment() { //сообщение об успешной оплате
        successfulSending.shouldBe(visible, Duration.ofSeconds(13));
    }

    public void messageAboutUnsuccessfulPaymentRefused() {
        failedSending.shouldBe(visible, Duration.ofSeconds(13));
    }

    public void messageAboutIncorrectDataFormat() {
        incorrectFormat.shouldBe(visible, Duration.ofSeconds(13));
    }

    public void messageAboutIncorrectCardExpirationDate() {
        incorrectCardExpirationDate.shouldBe(visible, Duration.ofSeconds(15));
    }

    public void messageAboutCardExpiration () { //сообщение об истечении срока действия карты
        cardExpired.shouldBe(visible, Duration.ofSeconds(13));
    }

    public void messageAboutTheRequiredField () {
        requiredLine.shouldBe(visible, Duration.ofSeconds(13));
    }
}

