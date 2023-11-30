package by.bsuir.webapp.service;

import by.bsuir.webapp.dto.PriceListItemDto;
import by.bsuir.webapp.exception.NotFoundEntityException;
import by.bsuir.webapp.model.tutor.PriceListItem;
import by.bsuir.webapp.model.tutor.Type;
import by.bsuir.webapp.repository.tutor.PriceListItemRepository;
import by.bsuir.webapp.repository.tutor.SubjectRepository;
import by.bsuir.webapp.repository.tutor.TutorRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PriceListItemService {
    @Autowired
    PriceListItemRepository priceListItemRepository;
    @Autowired
    TutorRepository tutorRepository;
    @Autowired
    SubjectRepository subjectRepository;

    public void update(Long tutorId, Long itemId, PriceListItemDto dto) throws NotFoundEntityException {
        PriceListItem item = priceListItemRepository.findByIdAndTutorId(itemId, tutorId)
                .orElseThrow(NotFoundEntityException::new);
        dtoToItem(dto, item);
        priceListItemRepository.save(item);
    }

    public void create(Long tutorId, PriceListItemDto dto) {
        PriceListItem item = new PriceListItem();
        item.setTutor(tutorRepository.getReferenceById(tutorId));
        dtoToItem(dto, item);
        priceListItemRepository.save(item);
    }

    private void dtoToItem(PriceListItemDto dto, PriceListItem item) {
        item.setDuration(dto.getDuration());
        item.setRate(dto.getRate());
        item.setSubject(subjectRepository.getReferenceById(dto.getSubject().longValue()));
        item.setType(Type.fromOrder(dto.getType()));
    }
}
