package by.bsuir.webapp.model.tutor;

import by.bsuir.webapp.common.domain.SimpleEntity;

import javax.persistence.*;

@Entity
@Table(name = "mobile_phone")
public class MobilePhone extends SimpleEntity {
    @Column(nullable = false, insertable = true, updatable = true)
    private String value;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false, insertable = true, updatable = true)
    private Tutor tutor;

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public Tutor getTutor() {
        return tutor;
    }

    public void setTutor(Tutor tutor) {
        this.tutor = tutor;
    }
}
