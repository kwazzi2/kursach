package by.bsuir.webapp.model.tutor;

import by.bsuir.webapp.common.domain.NamedEntity;
import org.hibernate.annotations.Type;

import javax.persistence.*;

@Entity
@Table
public class ScienceDegree extends NamedEntity {
    @Type(type = "by.bsuir.webapp.common.domain.type.OrderedEnumUserType",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "enumClass", value = "by.bsuir.webapp.model.tutor.Degree")
            })
    @Column(nullable = false, insertable = true, updatable = true)
    private Degree degree;

    public Degree getDegree() {
        return degree;
    }

    public void setDegree(Degree degree) {
        this.degree = degree;
    }
}
