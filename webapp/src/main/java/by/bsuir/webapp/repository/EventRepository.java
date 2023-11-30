package by.bsuir.webapp.repository;

import by.bsuir.webapp.model.Event;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EventRepository extends JpaRepository<Event, Long> {
    List<Event> findByTargetIdAndType(Long targetId, Event.Type type);
}
