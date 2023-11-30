package by.bsuir.webapp.util;

import org.springframework.data.jpa.domain.Specification;

import java.util.List;

public class SpecificationUtil {
    public static <T> Specification<T> combineWithAnd(final List<Specification<T>> specList) {
        if (specList.isEmpty()) {
            return null;
        }
        Specification<T> specs = Specification.where(specList.get(0));
        for (final Specification<T> specification : specList.subList(1, specList.size())) {
            specs = specs.and(specification);
        }
        return specs;
    }

    public static <T> Specification<T> combineWithOr(final List<Specification<T>> specList) {
        if (specList.isEmpty()) {
            return null;
        }
        Specification<T> specs = Specification.where(specList.get(0));
        for (final Specification<T> specification : specList.subList(1, specList.size())) {
            specs = specs.or(specification);
        }
        return specs;
    }
}