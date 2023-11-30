package by.bsuir.webapp.model.tutor;

import by.bsuir.webapp.common.domain.type.OrderedEnum;

public enum Type implements OrderedEnum {
    IN_GROUP(1, "В группе"),
    REMOTE(2, "Дистанционно"),
    AT_HOME(3, "На дому");

    private final int order;
    private final String name;

    Type(int order, String name) {
        this.order = order;
        this.name = name;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public static Type fromOrder(int order) {
        for (Type type : Type.values()) {
            if (type.order == order) {
                return type;
            }
        }
        return null;
    }

    public String getName() {
        return name;
    }
}
