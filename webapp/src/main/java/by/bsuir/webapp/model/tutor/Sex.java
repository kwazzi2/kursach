package by.bsuir.webapp.model.tutor;

import by.bsuir.webapp.common.domain.type.OrderedEnum;

public enum Sex implements OrderedEnum {
    MALE(1, "Мужской"),
    FEMALE(2, "Женский");

    private final int order;
    private final String name;

    Sex(int order, String name) {
        this.order = order;
        this.name = name;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public static Sex fromOrder(int order) {
        for (Sex sex : Sex.values()) {
            if (sex.order == order) {
                return sex;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }

    @Override
    public String toString() {
        return name;
    }
}
