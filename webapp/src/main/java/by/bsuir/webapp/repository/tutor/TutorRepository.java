package by.bsuir.webapp.repository.tutor;

import by.bsuir.webapp.model.Account_;
import by.bsuir.webapp.model.tutor.*;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.lang.Nullable;

import javax.persistence.criteria.ListJoin;
import java.util.List;
import java.util.Optional;

public interface TutorRepository extends JpaRepository<Tutor, Long>, JpaSpecificationExecutor<Tutor> {

    Optional<Tutor> findByIdAndHidden(Long id, boolean isHidden);


    interface Specs {
        static Specification<Tutor> byLastNameLike(String lastNamePattern) {
            return (root, query, builder) ->
                    builder.like(root.get(Tutor_.account).get(Account_.lastName), lastNamePattern);
        }
        static Specification<Tutor> byFirstNameLike(String firstNamePattern) {
            return (root, query, builder) ->
                    builder.like(root.get(Tutor_.account).get(Account_.firstName), firstNamePattern);
        }
        static Specification<Tutor> byMiddleNameLike(String middleNamePattern) {
            return (root, query, builder) ->
                    builder.like(root.get(Tutor_.account).get(Account_.middleName), middleNamePattern);
        }

        static Specification<Tutor> noHidden() {
            return (root, query, builder) ->
                    builder.equal(root.get(Tutor_.hidden), false);
        }

        static Specification<Tutor> byDegreeIn(List<Degree> degreeList) {
            return (root, query, builder) -> root.get(Tutor_.scienceDegree).get(ScienceDegree_.degree).in(degreeList);
        }

        static Specification<Tutor> bySubjectIdIn(List<Long> subjectIdList) {
            return (root, query, builder) -> {
                ListJoin<Tutor, PriceListItem> priceLists = root.join(Tutor_.priceListItems);
                return priceLists.get(PriceListItem_.subject).get(Subject_.id).in(subjectIdList);
            };
        }

        static Specification<Tutor> bySexIn(List<Sex> sexList) {
            return (root, query, builder) -> root.get(Tutor_.sex).in(sexList);
        }

        static Specification<Tutor> orderByRatingDesc(@Nullable Specification<Tutor> spec) {
            return spec == null ? (root, query, builder) -> {
                query.orderBy(builder.asc(root.get(Tutor_.rating)));
                return null;
            } : (root, query, builder) -> {
                query.orderBy(builder.asc(root.get(Tutor_.rating)));
                return spec.toPredicate(root, query, builder);
            };
        }

        static Specification<Tutor> orderByRatingAsc(@Nullable Specification<Tutor> spec) {
            return spec == null ? (root, query, builder) -> {
                query.orderBy(builder.desc(root.get(Tutor_.rating)));
                return null;
            } : (root, query, builder) -> {
                query.orderBy(builder.desc(root.get(Tutor_.rating)));
                return spec.toPredicate(root, query, builder);
            };
        }

        static Specification<Tutor> orderByExperience(@Nullable Specification<Tutor> spec) {
            return spec == null ? (root, query, builder) -> {
                query.orderBy(builder.desc(root.get(Tutor_.experience)));
                return null;
            } : (root, query, builder) -> {
                query.orderBy(builder.desc(root.get(Tutor_.experience)));
                return spec.toPredicate(root, query, builder);
            };
        }
    }
}