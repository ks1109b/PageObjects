package ru.netology.web.page;

import com.codeborne.selenide.*;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper.CardInfo;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class MoneyTransferPage {

    private static final SelenideElement amountField = $("[data-test-id='amount'] .input__control");
    private static final SelenideElement fromField = $("[data-test-id='from'] .input__control");
    private static final SelenideElement toField = $("[data-test-id='to'] .input__control");
    private static final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private static final SelenideElement cancelButton = $("[data-test-id='action-cancel']");
    private static final SelenideElement errorInsufficientFunds = $(withText("Недостаточно средств"));
    private static final SelenideElement errorNoneAmount = $(withText("Укажите сумму"));
    private static final SelenideElement errorInvalidCard = $(withText("Укажите карту"));

    public static void getErrorInsufficientFunds() {
        errorInsufficientFunds.shouldBe(visible);
    }

    public static void getErrorNoneAmount() {
        errorNoneAmount.shouldBe(visible);
    }

    public static void getErrorInvalidCard() {
        errorInvalidCard.shouldBe(visible);
    }

    public MoneyTransferPage() {
        SelenideElement heading = $("[data-test-id=dashboard]");
        heading.shouldBe(visible);
    }

    public static void topUpCard(int amount, CardInfo from, CardInfo to) {
        amountField.sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
        amountField.setValue(Integer.toString(amount));
        fromField.sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
        fromField.setValue(from.getNumber());
        validToField(to);
        transferButton.click();
        new DashboardPage();
    }

    public static void validToField(CardInfo to) {
        String value = "**** **** **** " + to.getNumber().substring(15);
        toField.shouldHave(value(value));
    }

    public static void cancelTransaction() {
        cancelButton.click();
        new DashboardPage();
    }
}
