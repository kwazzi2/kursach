package by.bsuir.webapp.service;

import by.bsuir.webapp.exception.NotFoundEntityException;
import by.bsuir.webapp.model.tutor.MobilePhone;
import by.bsuir.webapp.repository.tutor.MobilePhonesRepository;
import by.bsuir.webapp.repository.tutor.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class MobilePhoneService {
    @Autowired
    MobilePhonesRepository mobilePhonesRepository;
    @Autowired
    TutorRepository tutorRepository;

    public void update(Long tutorId, Long phoneId, String value) throws NotFoundEntityException {
        MobilePhone phone = mobilePhonesRepository.findByIdAndTutorId(phoneId, tutorId)
                .orElseThrow(NotFoundEntityException::new);
        phone.setValue(value);
        mobilePhonesRepository.save(phone);
    }

    public void create(Long tutorId, String value) {
        MobilePhone phone = new MobilePhone();
        phone.setTutor(tutorRepository.getReferenceById(tutorId));
        phone.setValue(value);
        mobilePhonesRepository.save(phone);
    }
}
