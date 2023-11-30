package by.bsuir.webapp.repository.tutor;

import by.bsuir.webapp.model.tutor.PriceListItem;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface PriceListItemRepository extends JpaRepository<PriceListItem, Long> {
    List<PriceListItem> findByTutorId(Long tutorId);
    Optional<PriceListItem> findByIdAndTutorId(Long id, Long tutorId);
    long countByTutorId(Long tutorId);
}
