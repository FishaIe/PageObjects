package ru.netology.web.page;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.SelenideElement;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$x;

public class ReplenishmentPage {

    private SelenideElement heading3 = $x("//*[contains(text(), 'Пополнение карты')]");
    private SelenideElement sum = $("[data-test-id=amount] input");
    private SelenideElement cardFrom = $("[data-test-id=from] input");
    private SelenideElement transferButton = $("[data-test-id=action-transfer]");

    public void headingCardsPayment() {
        heading3.shouldBe(Condition.visible);
    }

    public void setCardNumber(String card, int payment) {
        sum.setValue(String.valueOf(payment));
        cardFrom.setValue(card);
        transferButton.click();
    }

    public DashboardPage validCard() {
        return new DashboardPage();
    }

    public void invalidPayCard() {
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Произошла ошибка"));
    }

    public void validPayExtendAmount() {
        $("[data-test-id=error-notification] .notification__content").shouldHave(text("Недостаточно средств на карте"));
    }

}
