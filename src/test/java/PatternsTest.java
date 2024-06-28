import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Keys;

import java.time.Duration;

import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selectors.byText;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class PatternsTest {
    @BeforeEach
    void starting(){
        open("http://localhost:9999");
    }
    @Test
    void successPlan(){
        var user = DataClass.Registration.generateUser("ru");
        int addedDays1 = 4;
        String firstDate = DataClass.genDate(addedDays1);
        int addedDays2 = 7;
        String secondDate = DataClass.genDate(addedDays2);
        $("[data-test-id=city] input").setValue(user.getCity());
        $("[data-test-id=date] input").sendKeys(Keys.SHIFT,Keys.HOME,Keys.DELETE);
        $("[data-test-id=date] input").setValue(firstDate);
        $("[data-test-id=name] input").setValue(user.getName());
        $("[data-test-id=phone] input").setValue(user.getPhone());
        $("[data-test-id=agreement]").click();
        $(byText("Запланировать")).click();
        $(byText("Успешно!")).shouldBe(visible, Duration.ofSeconds(15));
        $("[data-test-id='success-notification'] .notification__content")
                .shouldBe(exactText("Встреча успешно запланирована на " + firstDate))
                .shouldBe(visible);
        $("[data-test-id=date] input").sendKeys(Keys.SHIFT,Keys.HOME,Keys.DELETE);
        $("[data-test-id=date] input").setValue(secondDate);
        $(byText("Запланировать")).click();
        $("[data-test-id='replan-notification'] .notification__content")
                .shouldBe(exactText("У вас уже запланирована встреча на другую дату. Перепланировать?"))
                .shouldBe(visible);
        $("[data-test-id='replan-notification'] button").click();
        $("[data-test-id='success-notification'] .notification__content")
                .shouldHave(exactText("Встреча успешно запланирована на " + secondDate))
                .shouldBe(visible);
    }
}
