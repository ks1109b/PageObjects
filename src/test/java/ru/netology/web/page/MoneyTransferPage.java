package ru.netology.web.page;

import com.codeborne.selenide.*;
import org.openqa.selenium.Keys;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class MoneyTransferPage {

    private SelenideElement heading = $("[data-test-id=dashboard]");
    private SelenideElement amountField = $("[data-test-id='amount'] .input__control");
    private SelenideElement fromField = $("[data-test-id='from'] .input__control");
    private SelenideElement toField = $("[data-test-id='to'] .input__control");
    private SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private SelenideElement cancelButton = $("[data-test-id='action-cancel']");

    public MoneyTransferPage() {
        heading.shouldBe(visible);
    }

    public DashboardPage topUpCard(int amount, DataHelper.CardInfo card) {
        amountField.sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
        amountField.setValue(Integer.toString(amount));
        fromField.sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
        fromField.setValue(card.getNumber());
        transferButton.click();
        return new DashboardPage();
    }
}
