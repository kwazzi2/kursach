package by.bsuir.webapp.dto;

import javax.validation.constraints.NotNull;

public class CommentDto {
    private String body;

    @NotNull(message = "поставьте отметку")
    private Integer rating;

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Integer getRating() {
        return rating;
    }

    public void setRating(Integer rating) {
        this.rating = rating;
    }
}
