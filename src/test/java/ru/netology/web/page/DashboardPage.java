package ru.netology.web.page;

import com.codeborne.selenide.*;
import lombok.val;

import static com.codeborne.selenide.Condition.*;
import static com.codeborne.selenide.Selenide.*;
import static ru.netology.web.data.DataHelper.*;
import static ru.netology.web.page.MoneyTransferPage.*;

public class DashboardPage {

    static CardInfo firstCardInfo = getFirstCardInfo();
    static CardInfo secondCardInfo = getSecondCardInfo();

    public DashboardPage() {
        SelenideElement heading = $("[data-test-id=dashboard]");
        heading.shouldBe(visible);
    }

    public static int getCardBalance(CardInfo card) {
        String id = card.getId();
        val text = $("[data-test-id='" + id + "']").getText();
        return extractBalance(text);
    }

    private static int extractBalance(String text) {
        String balanceStart = "баланс: ";
        String balanceFinish = " р.";
        val start = text.indexOf(balanceStart);
        val finish = text.indexOf(balanceFinish);
        val value = text.substring(start + balanceStart.length(), finish);
        return Integer.parseInt(value);
    }

    public static void clickTopUpButton(CardInfo card) {
        String id = card.getId();
        $("[data-test-id='" + id + "']")
                .find("[data-test-id='action-deposit']")
                .click();
        new MoneyTransferPage();
    }

    public static void getInitialData() {

        int firstCardBalance = getCardBalance(firstCardInfo);
        int secondCardBalance = getCardBalance(secondCardInfo);

        if (firstCardBalance > secondCardBalance) {
            int amount = (firstCardBalance + secondCardBalance) / 2 - secondCardBalance;
            clickTopUpButton(secondCardInfo);
            topUpCard(amount, firstCardInfo, secondCardInfo);
        } else {
            if (firstCardBalance < secondCardBalance) {
                int amount = (secondCardBalance + firstCardBalance) / 2 - firstCardBalance;
                clickTopUpButton(firstCardInfo);
                topUpCard(amount, secondCardInfo, firstCardInfo);
            }
        }
        new DashboardPage();
    }
}
