package by.bsuir.webapp.model;

import by.bsuir.webapp.common.domain.SimpleEntity;

import javax.persistence.*;
import java.util.Date;

@Entity
@Table
public class Event extends SimpleEntity {
    @ManyToOne(fetch = FetchType.LAZY)
    private Account author;
    @Column(nullable = false, insertable = true, updatable = true)
    Long targetId;

    @Enumerated(EnumType.STRING)
    private Type type;

    @Column(nullable = false, insertable = true, updatable = true)
    Date datetime;

    public enum Type {
       CHECK_TUTOR_PHONES
    }

    public Account getAuthor() {
        return author;
    }

    public void setAuthor(Account author) {
        this.author = author;
    }

    public Long getTargetId() {
        return targetId;
    }

    public void setTargetId(Long targetId) {
        this.targetId = targetId;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Date getDatetime() {
        return datetime;
    }

    public void setDatetime(Date datetime) {
        this.datetime = datetime;
    }
}
