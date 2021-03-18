package ru.netology.web.page;

import com.codeborne.selenide.*;
import lombok.val;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;

public class DashboardPage {
    private SelenideElement heading = $("[data-test-id=dashboard]");
    private ElementsCollection cards = $$(".list__item");
    private final String balanceStart = "баланс: ";
    private final String balanceFinish = " р.";
    private SelenideElement cardField;
    private SelenideElement cardButton;

    public DashboardPage() {
        heading.shouldBe(visible);
    }

    public int getCardBalance(String id) {
        val text = $("[data-test-id='" + id + "']").getText();
//        val text = cards.find(attribute("data-test-id", id)).getText();
        return extractBalance(text);
    }

    private int extractBalance(String text) {
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public MoneyTransferPage clickCardButton(String id) {
        cardField = $("[data-test-id='" + id + "']");
        cardButton = cardField.find("[data-test-id='action-deposit']");
        cardButton.click();
        return new MoneyTransferPage();
    }

//    public Integer checkCardBalance(String id) {
//        cardField = $("[data-test-id='" + id + "']");
//        String text = cardField.getText();
//        String amount = text.replaceAll("(.*баланс: )|( р\\.[\\s\\S]*)", "");
//        return  Integer.valueOf(amount);
//    }

}
