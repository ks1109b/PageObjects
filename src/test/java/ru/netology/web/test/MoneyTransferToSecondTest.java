package ru.netology.web.test;

import org.junit.jupiter.api.*;
import ru.netology.web.page.*;

import static com.codeborne.selenide.Selenide.open;
import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;
import static ru.netology.web.page.DashboardPage.*;
import static ru.netology.web.page.MoneyTransferPage.*;

class MoneyTransferToSecondTest {

    CardInfo firstCardInfo = getFirstCardInfo();
    CardInfo secondCardInfo = getSecondCardInfo();

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        LoginPage.validLogin(getAuthInfo());
        VerificationPage.validVerify(getVerificationCode());
        clickTopUpButton(secondCardInfo);
    }

    @AfterEach
    void returnInitialData(TestInfo testInfo) {
        if (testInfo.getTags().contains("SkipCleanup")) {
            return;
        }
        getInitialData();
    }

    @Test
    void shouldSuccessIfBelowLimit() {
        int amount = 1000;
        topUpCard(amount, firstCardInfo, secondCardInfo);

        assertEquals(parseInt("11000"), getCardBalance(secondCardInfo));
        assertEquals(parseInt("9000"), getCardBalance(firstCardInfo));
    }

    @Test
    void shouldGetErrorIfAboveLimit() {
        int amount = 11000;
        topUpCard(amount, firstCardInfo, secondCardInfo);

        getErrorInsufficientFunds();
    }

    @Test
    void shouldGetErrorIfAmountNull() {
        int amount = 0;
        topUpCard(amount, firstCardInfo, secondCardInfo);

        getErrorNoneAmount();
    }

    @Test
    void shouldGetErrorIfSameCard() {
        int amount = 500;
        topUpCard(amount, secondCardInfo, secondCardInfo);

        getErrorInvalidCard();
    }

    @Test
    void shouldCancelTransaction() {
        cancelTransaction();
    }

    @Test
    @Tag("SkipCleanup")
    void shouldGetErrorIfEmptyForm() {
        getErrorEmptyForm();
    }
}