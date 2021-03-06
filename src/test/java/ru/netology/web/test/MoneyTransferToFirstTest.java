package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.web.page.*;

import static com.codeborne.selenide.Selenide.open;
import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

class MoneyTransferToFirstTest {

    DashboardPage dashboardPage;
    MoneyTransferPage moneyTransferPage;
    CardInfo firstCardInfo = getFirstCardInfo();
    CardInfo secondCardInfo = getSecondCardInfo();

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        val loginPage = new LoginPage();
        val verificationPage = loginPage.validLogin(getAuthInfo());

        verificationPage.validVerify(getVerificationCode());
        dashboardPage = new DashboardPage();
        moneyTransferPage = new MoneyTransferPage();
        dashboardPage.clickTopUpButton(firstCardInfo);
    }

    @AfterEach
    void returnInitialData(TestInfo testInfo) {
        if (testInfo.getTags().contains("SkipCleanup")) {
            return;
        }
        getInitialData(dashboardPage, firstCardInfo, secondCardInfo);
    }

    @Test
    void shouldSuccessIfBelowLimit() {
        val amount = 1000;
        moneyTransferPage.topUpCard(amount, secondCardInfo, firstCardInfo);

        assertEquals(parseInt("11000"), dashboardPage.getCardBalance(firstCardInfo));
        assertEquals(parseInt("9000"), dashboardPage.getCardBalance(secondCardInfo));
    }

    @Test
    void shouldGetErrorIfAboveLimit() {
        val amount = 11000;
        moneyTransferPage.topUpCard(amount, secondCardInfo, firstCardInfo);
        moneyTransferPage.getErrorInsufficientFunds();
    }

    @Test
    void shouldGetErrorIfAmountNull() {
        val amount = 0;
        moneyTransferPage.topUpCard(amount, secondCardInfo, firstCardInfo);
        moneyTransferPage.getErrorNoneAmount();
    }

    @Test
    void shouldGetErrorIfSameCard() {
        val amount = 500;
        moneyTransferPage.topUpCard(amount, firstCardInfo, firstCardInfo);
        moneyTransferPage.getErrorInvalidCard();
    }

    @Test
    void shouldCancelTransaction() {
        moneyTransferPage.cancelTransaction();
    }

    @Test
    @Tag("SkipCleanup")
    void shouldGetErrorIfEmptyForm() {
        moneyTransferPage.getErrorEmptyForm();
    }
}