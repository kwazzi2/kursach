package by.bsuir.webapp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.constraints.Positive;

public class TutorRegistrationDto {
    @NotNull(message = "не может быть пустым")
    private Integer subject;
    @NotNull(message = "не может быть пустым")
    private Integer type;
    @NotNull(message = "не может быть пустым")
    @Positive(message = "положительное число")
    private Integer rate;
    @NotNull(message = "не может быть пустым")
    @Positive(message = "положительное число")
    private Integer duration;
    @Pattern(regexp = "[0-9]{12}", message = "12 цифр (375111111111)")
    private String value;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Integer getSubject() {
        return subject;
    }

    public void setSubject(Integer subject) {
        this.subject = subject;
    }

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getRate() {
        return rate;
    }

    public void setRate(Integer rate) {
        this.rate = rate;
    }

    public Integer getDuration() {
        return duration;
    }

    public void setDuration(Integer duration) {
        this.duration = duration;
    }
}
