package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.DashboardPage;
import ru.netology.web.page.LoginPage;
import ru.netology.web.page.MoneyTransferPage;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    private DashboardPage dashboardPage;
    private int firstCardBalance;
    private int secondCardBalance;
    private final String firstCardId = DataHelper.getFirstCardInfo().getId();
    private final String secondCardId = DataHelper.getSecondCardInfo().getId();

    @BeforeEach
    void singIn() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        dashboardPage = verificationPage.validVerify(verificationCode);
    }

    @AfterEach
    void returnInitialData() {
        firstCardBalance = dashboardPage.getCardBalance(firstCardId);
        secondCardBalance = dashboardPage.getCardBalance(secondCardId);

        if (firstCardBalance > secondCardBalance) {
            int amount = (firstCardBalance + secondCardBalance) / 2 - secondCardBalance;
            val moneyTransferPage = dashboardPage.clickCardButton(secondCardId);
            moneyTransferPage.topUpCard(amount, DataHelper.getFirstCardInfo());
        } else if (firstCardBalance < secondCardBalance) {
            int amount = (secondCardBalance + firstCardBalance) / 2 - firstCardBalance;
            val moneyTransferPage = dashboardPage.clickCardButton(firstCardId);
            moneyTransferPage.topUpCard(amount, DataHelper.getSecondCardInfo());
        }
    }

    @Test
    void transferMoneyToFirstCardTest() {
        int amount = 1000;
        firstCardBalance = dashboardPage.getCardBalance(firstCardId);
        secondCardBalance = dashboardPage.getCardBalance(secondCardId);

        MoneyTransferPage moneyTransferPage = dashboardPage.clickCardButton(firstCardId);
        dashboardPage = moneyTransferPage.topUpCard(amount, DataHelper.getSecondCardInfo());

        assertEquals(firstCardBalance + amount, dashboardPage.getCardBalance(firstCardId));
        assertEquals(secondCardBalance - amount, dashboardPage.getCardBalance(secondCardId));
    }

    @Test
    void transferMoneyToFirstCardTest1() {
        int amount = 5000;
        firstCardBalance = dashboardPage.getCardBalance(firstCardId);
        secondCardBalance = dashboardPage.getCardBalance(secondCardId);

        MoneyTransferPage moneyTransferPage = dashboardPage.clickCardButton(secondCardId);
        dashboardPage = moneyTransferPage.topUpCard(amount, DataHelper.getFirstCardInfo());
        int exp = 15000;
        assertEquals(exp, dashboardPage.getCardBalance(secondCardId));
        assertEquals(firstCardBalance - amount, dashboardPage.getCardBalance(firstCardId));
    }
}