package teamcity.ui.elements;

import com.codeborne.selenide.SelenideElement;
import lombok.Getter;

import static com.codeborne.selenide.Condition.text;
import static com.codeborne.selenide.Selenide.$;

@Getter
public class ErrorElement{
    private SelenideElement error = $("#error_buildTypeName");

    public void checkErrorText(String errorText) {
        error.shouldHave(text(errorText));
    }
}
