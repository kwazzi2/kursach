package by.bsuir.webapp.service;

import by.bsuir.webapp.model.Event;
import by.bsuir.webapp.repository.AccountRepository;
import by.bsuir.webapp.repository.EventRepository;
import by.bsuir.webapp.service.security.PersitedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;

@Service
public class EventService {
    @Autowired
    EventRepository eventRepository;
    @Autowired
    AccountRepository accountRepository;

    public void checkPhones(PersitedUser user, Long accountId) {
        Event event = new Event();
        event.setAuthor(accountRepository.getReferenceById(user.getAccountId()));
        event.setDatetime(new Date());
        event.setTargetId(accountId);
        event.setType(Event.Type.CHECK_TUTOR_PHONES);
        eventRepository.save(event);
    }

}
