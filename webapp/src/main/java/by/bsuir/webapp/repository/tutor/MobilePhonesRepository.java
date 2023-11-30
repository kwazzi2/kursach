package by.bsuir.webapp.repository.tutor;

import by.bsuir.webapp.model.tutor.MobilePhone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface MobilePhonesRepository extends JpaRepository<MobilePhone, Long> {
    List<MobilePhone> findByTutorId(Long tutorId);

    Optional<MobilePhone> findByIdAndTutorId(Long id, Long tutorId);
    long countByTutorId(Long tutorId);
}
