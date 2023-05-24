package ru.netology.web.test;

import org.junit.jupiter.api.Test;
import ru.netology.web.data.DataHelper;
import ru.netology.web.page.LoginPageV1;
import lombok.val;

import static com.codeborne.selenide.Selenide.open;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTransferTest {

    @Test
    void shouldReplenishmentFromSecondToFirstCard() {
        val sum = 500;
        open("http://localhost:9999");
        val loginPage = new LoginPageV1();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.profilePage();
        val initialBalanceToCard = dashboardPage.getFirstCardBalance();
        val initialBalanceFromCard = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.replenishmentV1();
        transferPage.headingCardsPayment();
        transferPage.setCardNumber(DataHelper.getSecondCard(), sum);
        val dashboardPage1 = transferPage.validCard();
        val actual = dashboardPage1.getFirstCardBalance();
        val expected = initialBalanceToCard + sum;
        val actual2 = dashboardPage1.getSecondCardBalance();
        val expected2 = initialBalanceFromCard + sum;
        assertEquals(expected, actual);
        assertEquals(expected2, actual2);
    }

    @Test
    void shouldReplenishmentFromFirstToSecondCard() {
        val sum = 500;
        open("http://localhost:9999");
        val loginPage = new LoginPageV1();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.profilePage();
        val initialBalanceToCard = dashboardPage.getSecondCardBalance();
        val initialBalanceFromCard = dashboardPage.getFirstCardBalance();
        val transferPage = dashboardPage.replenishmentV2();
        transferPage.headingCardsPayment();
        transferPage.setCardNumber(DataHelper.getFirstCard(), sum);
        val dashboardPage1 = transferPage.validCard();
        val actual = dashboardPage1.getSecondCardBalance();
        val expected = initialBalanceToCard - sum;
        val actual2 = dashboardPage1.getFirstCardBalance();
        val expected2 = initialBalanceFromCard - sum;
        assertEquals(expected, actual);
        assertEquals(expected2, actual2);
    }

    @Test
    void shouldReplenishmentFromInvalidCard() {
        val amount = 1000;
        open("http://localhost:9999");
        val loginPage = new LoginPageV1();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.profilePage();
        val transferPage = dashboardPage.replenishmentV1();
        transferPage.headingCardsPayment();
        transferPage.setCardNumber(DataHelper.getAnotherCard(), amount);
        transferPage.invalidPayCard();
    }

    @Test
    void shouldReplenishmentOverLimitFromFirstCard() {
        val amount = 50000;
        open("http://localhost:9999");
        val loginPage = new LoginPageV1();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.profilePage();
        val initialBalanceFromCard = dashboardPage.getSecondCardBalance();
        val transferPage = dashboardPage.replenishmentV1();
        transferPage.headingCardsPayment();
        transferPage.setCardNumber(DataHelper.getSecondCard(), amount);
        transferPage.validPayExtendAmount();
    }

    @Test
    void shouldReplenishmentOverLimitFromSecondCard() {
        val amount = 50000;
        open("http://localhost:9999");
        val loginPage = new LoginPageV1();
        val authInfo = DataHelper.getAuthInfo();
        val verificationPage = loginPage.validLogin(authInfo);
        val verificationCode = DataHelper.getVerificationCodeFor(authInfo);
        val dashboardPage = verificationPage.validVerify(verificationCode);
        dashboardPage.profilePage();
        val initialBalanceFromCard = dashboardPage.getFirstCardBalance();
        val transferPage = dashboardPage.replenishmentV1();
        transferPage.headingCardsPayment();
        transferPage.setCardNumber(DataHelper.getFirstCard(), amount);
        transferPage.validPayExtendAmount();
    }

}

