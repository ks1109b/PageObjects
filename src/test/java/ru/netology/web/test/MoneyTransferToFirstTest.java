package ru.netology.web.test;

import org.junit.jupiter.api.*;
import ru.netology.web.page.*;

import static com.codeborne.selenide.Selenide.open;
import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;
import static ru.netology.web.page.DashboardPage.*;
import static ru.netology.web.page.MoneyTransferPage.*;

class MoneyTransferToFirstTest {

    CardInfo firstCardInfo = getFirstCardInfo();
    CardInfo secondCardInfo = getSecondCardInfo();

    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
        LoginPage.validLogin(getAuthInfo());
        VerificationPage.validVerify(getVerificationCode());
        clickTopUpButton(firstCardInfo);
    }

    @AfterEach
    void returnInitialData() {
        getInitialData();
    }

    @Test
    void shouldSuccessIfBelowLimit() {
        int amount = 1000;
        topUpCard(amount, secondCardInfo, firstCardInfo);

        assertEquals(parseInt("11000"), getCardBalance(firstCardInfo));
        assertEquals(parseInt("9000"), getCardBalance(secondCardInfo));
    }

    @Test
    void shouldGetErrorIfAboveLimit() {
        int amount = 11000;
        topUpCard(amount, secondCardInfo, firstCardInfo);

        getErrorInsufficientFunds();
    }

    @Test
    void shouldGetErrorIfAmountNull() {
        int amount = 0;
        topUpCard(amount, secondCardInfo, firstCardInfo);

        getErrorNoneAmount();
    }

    @Test
    void shouldGetErrorIfSameCard() {
        int amount = 500;
        topUpCard(amount, firstCardInfo, firstCardInfo);

        getErrorInvalidCard();
    }

    @Test
    void shouldCancelTransaction() {
        cancelTransaction();
    }
}