package by.bsuir.webapp.model.comment;

import by.bsuir.webapp.common.domain.type.OrderedEnum;

public enum Rating implements OrderedEnum {
    ONE(1),
    TWO(2),
    THREE(3),
    FOUR(4),
    FIVE(5);

    private final int order;

    Rating(int order) {
        this.order = order;
    }

    @Override
    public int getOrder() {
        return order;
    }

    public static Rating fromOrder(int order) {
        for (Rating rating : Rating.values()) {
            if (rating.order == order) {
                return rating;
            }
        }
        return null;
    }
}
