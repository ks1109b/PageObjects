package ru.netology.web.test;

import lombok.val;
import org.junit.jupiter.api.*;
import ru.netology.web.page.*;

import static com.codeborne.selenide.Selenide.open;
import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;

class MoneyTransferToSecondTest {

    DashboardPage dashboardPage;
    MoneyTransferPage moneyTransferPage;
    CardInfo firstCardInfo = getFirstCardInfo();
    CardInfo secondCardInfo = getSecondCardInfo();

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        LoginPage loginPage = new LoginPage();

        loginPage.validLogin(getAuthInfo());
        VerificationPage verificationPage = new VerificationPage();

        verificationPage.validVerify(getVerificationCode());
        dashboardPage = new DashboardPage();
        moneyTransferPage = new MoneyTransferPage();
        dashboardPage.clickTopUpButton(secondCardInfo);
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
        int amount = 1000;
        moneyTransferPage.topUpCard(amount, firstCardInfo, secondCardInfo);

        assertEquals(parseInt("11000"), dashboardPage.getCardBalance(secondCardInfo));
        assertEquals(parseInt("9000"), dashboardPage.getCardBalance(firstCardInfo));
    }

    @Test
    void shouldGetErrorIfAboveLimit() {
        int amount = 11000;
        moneyTransferPage.topUpCard(amount, firstCardInfo, secondCardInfo);

        moneyTransferPage.getErrorInsufficientFunds();
    }

    @Test
    void shouldGetErrorIfAmountNull() {
        int amount = 0;
        moneyTransferPage.topUpCard(amount, firstCardInfo, secondCardInfo);

        moneyTransferPage.getErrorNoneAmount();
    }

    @Test
    void shouldGetErrorIfSameCard() {
        int amount = 500;
        moneyTransferPage.topUpCard(amount, secondCardInfo, secondCardInfo);

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