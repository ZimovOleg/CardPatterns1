package ru.netology;

import lombok.val;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.withText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static ru.netology.DataGenerator.generateDate;
import static ru.netology.DataGenerator.generateRequest;

public class CardDeliveryTest {
    @BeforeEach
    void setUp() {
        open("http://localhost:9999");
    }

    @Test
    void shouldRegister() {
        val requestInfo = generateRequest("ru");
        $("[data-test-id=city] input").setValue(requestInfo.getCity());
        val firstMeetingDate = generateDate(0, 4);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(firstMeetingDate);
        $("[name='name']").setValue(requestInfo.getName());
        $("[name='phone']").setValue(requestInfo.getPhone());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content")
                .waitUntil(visible, 15000)
                .shouldHave(exactText("Встреча успешно запланирована на  " + firstMeetingDate));
        val secondMeetingDate = generateDate(5, 4);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(secondMeetingDate);
        $(withText("Запланировать")).click();
        $(withText("Перепланировать")).click();
        $("[data-test-id=success-notification] .notification__content")
                .waitUntil(visible, 15000)
                .shouldHave(exactText("Встреча успешно запланирована на  " + secondMeetingDate));
    }
    @Test
    void shouldRegisterWithoutChangingDate() {
        val requestInfo = generateRequest("ru");
        $("[data-test-id=city] input").setValue(requestInfo.getCity());
        val MeetingDate = generateDate(0, 4);
        $("[placeholder='Дата встречи']").doubleClick().sendKeys(Keys.BACK_SPACE);
        $("[placeholder='Дата встречи']").setValue(MeetingDate);
        $("[name='name']").setValue(requestInfo.getName());
        $("[name='phone']").setValue(requestInfo.getPhone());
        $("[data-test-id=agreement]").click();
        $(withText("Запланировать")).click();
        $("[data-test-id=success-notification] .notification__content")
                .waitUntil(visible, 15000)
                .shouldHave(exactText("Встреча успешно запланирована на  " + MeetingDate));

    }



