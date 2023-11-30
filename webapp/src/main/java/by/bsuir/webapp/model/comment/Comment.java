package by.bsuir.webapp.model.comment;


import by.bsuir.webapp.common.domain.SimpleEntity;
import by.bsuir.webapp.model.Account;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Comment extends SimpleEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Account author;

    @ManyToOne(fetch = FetchType.LAZY)
    private Account target;

    @Column(nullable = true, insertable = true, updatable = true)
    private String body;

    @Type(type = "by.bsuir.webapp.common.domain.type.OrderedEnumUserType",
            parameters = {
            @org.hibernate.annotations.Parameter(name = "enumClass", value = "by.bsuir.webapp.model.comment.Rating")
    })
    @Column(nullable = false, insertable = true, updatable = true)
    private Rating rating;

    @Column(nullable = false, insertable = true, updatable = true)
    private Date createdAt;

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public Account getTarget() {
        return target;
    }

    public void setTarget(Account target) {
        this.target = target;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public Rating getRating() {
        return rating;
    }

    public void setRating(Rating rating) {
        this.rating = rating;
    }

    public Date getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(Date createdAt) {
        this.createdAt = createdAt;
    }
}
