package by.bsuir.webapp.dto;

import javax.validation.constraints.Pattern;

public class PhoneDto {
    @Pattern(regexp = "[0-9]{12}", message = "12 цифр (375111111111)")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }
}
