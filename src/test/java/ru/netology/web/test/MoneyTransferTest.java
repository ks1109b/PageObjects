package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.*;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    DataHelper.CardInfo firstCardInfo = DataHelper.getFirstCardInfo();
    DataHelper.CardInfo secondCardInfo = DataHelper.getSecondCardInfo();
    private int firstCardBalance;
    private int secondCardBalance;

    @BeforeEach
    void validLogin() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        verificationPage.validVerify(verificationCode);
    }

    @BeforeEach
    void setUp() {
        firstCardBalance = DashboardPage.getCardBalance(firstCardInfo);
        secondCardBalance = DashboardPage.getCardBalance(secondCardInfo);
    }

    @AfterEach
    void returnInitialData() {
        firstCardBalance = DashboardPage.getCardBalance(firstCardInfo);
        secondCardBalance = DashboardPage.getCardBalance(secondCardInfo);
        if (firstCardBalance > secondCardBalance) {
            int amount = (firstCardBalance + secondCardBalance) / 2 - secondCardBalance;
            DashboardPage.clickTopUpButton(secondCardInfo);
            MoneyTransferPage.topUpCard(amount, firstCardInfo);
        } else if (firstCardBalance < secondCardBalance) {
            int amount = (secondCardBalance + firstCardBalance) / 2 - firstCardBalance;
            DashboardPage.clickTopUpButton(firstCardInfo);
            MoneyTransferPage.topUpCard(amount, secondCardInfo);
        }
    }

    @Test
    void shouldTopUpFirstFromSecond() {
        int amount = 1000;
        DashboardPage.clickTopUpButton(firstCardInfo);
        MoneyTransferPage.topUpCard(amount, secondCardInfo);
        int exp = 11000;
        int exp2 = 9000;
        assertEquals(/*firstCardBalance + amount*/ exp, firstCardBalance);
        assertEquals(/*secondCardBalance - amount*/ exp2, secondCardBalance);
    }

    @Test
    void shouldTopUpSecondFromFirst() {
        int amount = 5000;

        DashboardPage.clickTopUpButton(secondCardInfo);
        MoneyTransferPage.topUpCard(amount, firstCardInfo);
        int exp = 5000;
        int exp2 = 15000;
        assertEquals(/*firstCardBalance - amount*/ exp, firstCardBalance);
        assertEquals(/*secondCardBalance + amount*/ exp2, secondCardBalance);
    }
}