package ru.netology.web.page;

import com.codeborne.selenide.*;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper.CardInfo;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.*;

public class MoneyTransferPage {

    private final SelenideElement amountField = $("[data-test-id='amount'] .input__control");
    private final SelenideElement fromField = $("[data-test-id='from'] .input__control");
    private final SelenideElement toField = $("[data-test-id='to'] .input__control");
    private final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private final SelenideElement cancelButton = $("[data-test-id='action-cancel']");
    private final SelenideElement errorInsufficientFunds = $(withText("Недостаточно средств"));
    private final SelenideElement errorNoneAmount = $(withText("Укажите сумму"));
    private final SelenideElement errorInvalidCard = $(withText("Укажите корректный номер карты"));
    private final SelenideElement errorEmptyForm = $("[data-test-id=error-notification]");

    public MoneyTransferPage() {
        SelenideElement heading = $("[data-test-id=dashboard]");
        heading.shouldBe(visible);
    }

    public void getErrorInsufficientFunds() {
        errorInsufficientFunds.shouldBe(visible);
    }

    public void getErrorNoneAmount() {
        errorNoneAmount.shouldBe(visible);
    }

    public void getErrorInvalidCard() {
        errorInvalidCard.shouldBe(visible);
    }

    public void getErrorEmptyForm(){
        transferButton.click();
        errorEmptyForm.shouldBe(visible).shouldHave(text("Произошла ошибка"));
    }

    public DashboardPage topUpCard(Integer amount, CardInfo from, CardInfo to) {
        amountField.sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
        amountField.setValue(Integer.toString(amount));
        fromField.sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
        fromField.setValue(from.getNumber());
        validToField(to);
        transferButton.click();
        return new DashboardPage();
    }

    public void validToField(CardInfo to) {
        String value = "**** **** **** " + to.getNumber().substring(15);
        toField.shouldHave(value(value));
    }

    public DashboardPage cancelTransaction() {
        cancelButton.click();
        return new DashboardPage();
    }
}
