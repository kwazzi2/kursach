package by.bsuir.webapp.model.tutor;

import by.bsuir.webapp.common.domain.type.OrderedEnum;

public enum Degree implements OrderedEnum {
    BACHELOR(1, "Бакалавр"),
    MASTER(2, "Магистр"),
    CANDIDATE(3, "Кандидат"),
    DOCTOR(4, "Доктор");

    private final int order;
    private final String name;

    Degree(int order, String name) {
        this.order = order;
        this.name = name;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public static Degree fromOrder(int order) {
        for (Degree degree : Degree.values()) {
            if (degree.order == order) {
                return degree;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
