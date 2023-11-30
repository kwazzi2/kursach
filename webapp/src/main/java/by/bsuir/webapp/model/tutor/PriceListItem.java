package by.bsuir.webapp.model.tutor;

import by.bsuir.webapp.common.domain.SimpleEntity;

import javax.persistence.*;

@Entity
@Table
public class PriceListItem extends SimpleEntity {

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, insertable = true, updatable = true)
    private Subject subject;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(nullable = false, insertable = true, updatable = true)
    private Tutor tutor;

    @org.hibernate.annotations.Type(type = "by.bsuir.webapp.common.domain.type.OrderedEnumUserType",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "enumClass", value = "by.bsuir.webapp.model.tutor.Type")
            })
    @Column(nullable = true, insertable = true, updatable = true)
    private Type type;

    @Column(nullable = false, insertable = true, updatable = true)
    private Integer rate;

    @Column(nullable = false, insertable = true, updatable = true)
    private Integer duration;

    public Subject getSubject() {
        return subject;
    }

    public void setSubject(Subject subject) {
        this.subject = subject;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
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
