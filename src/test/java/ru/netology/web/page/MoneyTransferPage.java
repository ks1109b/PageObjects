package ru.netology.web.page;

import com.codeborne.selenide.*;
import lombok.Value;
import org.openqa.selenium.Alert;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;
import ru.netology.web.data.DataHelper;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class MoneyTransferPage {
    private static final SelenideElement amountField = $("[data-test-id='amount'] .input__control");
    private static final SelenideElement fromField = $("[data-test-id='from'] .input__control");
    private static final SelenideElement toField = $("[data-test-id='to'] .input__control");
    private static final SelenideElement transferButton = $("[data-test-id='action-transfer']");
    private static final SelenideElement cancelButton = $("[data-test-id='action-cancel']");

    public MoneyTransferPage() {
        SelenideElement heading = $("[data-test-id=dashboard]");
        heading.shouldBe(visible);
    }

    public static DashboardPage topUpCard(int amount, DataHelper.CardInfo card) {
        amountField.sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
        amountField.setValue(Integer.toString(amount));
        fromField.sendKeys(Keys.chord(Keys.CONTROL, "A"), Keys.DELETE);
        fromField.setValue(card.getNumber());
        transferButton.click();
        return new DashboardPage();
    }
}
