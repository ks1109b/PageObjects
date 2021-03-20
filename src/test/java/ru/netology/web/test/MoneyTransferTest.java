package ru.netology.web.test;

import org.junit.jupiter.api.*;
import ru.netology.web.page.*;

import static com.codeborne.selenide.Selenide.open;
import static java.lang.Integer.parseInt;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.netology.web.data.DataHelper.*;
import static ru.netology.web.page.DashboardPage.*;
import static ru.netology.web.page.MoneyTransferPage.topUpCard;

class MoneyTransferTest {

    @BeforeEach
    void validLogin() {
        open("http://localhost:9999");
        AuthInfo authInfo = getAuthInfo();
        LoginPage.validLogin(authInfo);
        VerificationPage.validVerify(getVerificationCodeFor(authInfo));
    }

    @AfterEach
    void returnInitialData(){
        getInitialData();
    }

    @Test
    void shouldTopUpFirstFromSecondInLimit() {
        int amount = 1000;

        clickTopUpButton(getFirstCardInfo());
        topUpCard(amount, getSecondCardInfo(), getFirstCardInfo());

        assertEquals(parseInt("11000"), getCardBalance(getFirstCardInfo()));
        assertEquals(parseInt("9000"), getCardBalance(getSecondCardInfo()));
    }

    @Test
    void shouldTopUpFirstFromSecondUpperLimit() {
        int amount = 11000;

        clickTopUpButton(getFirstCardInfo());
        topUpCard(amount, getSecondCardInfo(), getFirstCardInfo());

        assertEquals(parseInt("21000"), getCardBalance(getFirstCardInfo()));
        assertEquals(parseInt("-1000"), getCardBalance(getSecondCardInfo()));
    }

    @Test
    void shouldTopUpSecondFromFirst() {
        int amount = 5000;

        clickTopUpButton(getSecondCardInfo());
        topUpCard(amount, getFirstCardInfo(), getSecondCardInfo());

        assertEquals(parseInt("5000"), getCardBalance(getFirstCardInfo()));
        assertEquals(parseInt("15000"), getCardBalance(getSecondCardInfo()));
    }
}