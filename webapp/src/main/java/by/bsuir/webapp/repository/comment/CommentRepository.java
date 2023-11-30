package by.bsuir.webapp.repository.comment;

import by.bsuir.webapp.model.Account_;
import by.bsuir.webapp.model.comment.Comment;
import by.bsuir.webapp.model.comment.Comment_;
import by.bsuir.webapp.model.comment.Rating;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.List;

public interface CommentRepository extends JpaRepository<Comment, Long>, JpaSpecificationExecutor<Comment> {

    interface Specs {

        static Specification<Comment> byTargetId(Long targetId) {
            return (root, query, builder) ->
                    builder.equal(root.get(Comment_.target).get(Account_.ID), targetId);
        }

        static Specification<Comment> byRatingIn(List<Rating> ratingList) {
            return (root, query, builder) -> root.get(Comment_.rating).in(ratingList);
        }
    }
}
