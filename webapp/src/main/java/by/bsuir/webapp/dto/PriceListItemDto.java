package by.bsuir.webapp.dto;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;


public class PriceListItemDto {
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
