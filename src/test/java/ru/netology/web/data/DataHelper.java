package ru.netology.web.data;

import lombok.Value;
import lombok.val;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.MoneyTransferPage;

public class DataHelper {
    private DataHelper() {
    }

    @Value
    public static class AuthInfo {
        String login;
        String password;
    }

    public static AuthInfo getAuthInfo() {
        return new AuthInfo("vasya", "qwerty123");
    }

    @Value
    public static class VerificationCode {
        String code;
    }

    public static VerificationCode getVerificationCode() {
        return new VerificationCode("12345");
    }

    @Value
    public static class CardInfo {
        String id;
        String number;
    }

    public static CardInfo getFirstCardInfo() {
        return new CardInfo("92df3f1c-a033-48e6-8390-206f6b1f56c0", "5559 0000 0000 0001");
    }

    public static CardInfo getSecondCardInfo() {
        return new CardInfo("0f3f5c2a-249e-4c3d-8287-09f7a039391d", "5559 0000 0000 0002");
    }

    public static void getInitialData(DashboardPage dashboardPage, CardInfo firstCardInfo, CardInfo secondCardInfo) {
        val moneyTransferPage = new MoneyTransferPage();

        int firstCardBalance = dashboardPage.getCardBalance(firstCardInfo);
        int secondCardBalance = dashboardPage.getCardBalance(secondCardInfo);

        if (firstCardBalance > secondCardBalance) {
            int amount = (firstCardBalance + secondCardBalance) / 2 - secondCardBalance;
            dashboardPage.clickTopUpButton(secondCardInfo);
            moneyTransferPage.topUpCard(amount, firstCardInfo, secondCardInfo);
        } else {
            if (firstCardBalance < secondCardBalance) {
                int amount = (secondCardBalance + firstCardBalance) / 2 - firstCardBalance;
                dashboardPage.clickTopUpButton(firstCardInfo);
                moneyTransferPage.topUpCard(amount, secondCardInfo, firstCardInfo);
            }
        }
        new DashboardPage();
    }
}