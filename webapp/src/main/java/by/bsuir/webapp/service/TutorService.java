package by.bsuir.webapp.service;

import by.bsuir.webapp.dto.TutorDto;
import by.bsuir.webapp.dto.TutorRegistrationDto;
import by.bsuir.webapp.exception.NotFoundEntityException;
import by.bsuir.webapp.model.Account;
import by.bsuir.webapp.model.comment.Comment;
import by.bsuir.webapp.model.tutor.*;
import by.bsuir.webapp.repository.AccountRepository;
import by.bsuir.webapp.repository.comment.CommentRepository;
import by.bsuir.webapp.repository.tutor.ScienceDegreeRepository;
import by.bsuir.webapp.repository.tutor.SubjectRepository;
import by.bsuir.webapp.repository.tutor.TutorRepository;
import by.bsuir.webapp.service.external.GoogleDrivePhotoStorageService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.OptionalDouble;

@Service
public class TutorService {
    @Autowired
    TutorRepository tutorRepository;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    GoogleDrivePhotoStorageService photoStorageService;
    @Autowired
    ScienceDegreeRepository scienceDegreeRepository;

    public void update(Long tutorId, TutorDto tutorDto) throws NotFoundEntityException {
        Tutor tutor = tutorRepository.findById(tutorId).orElseThrow(NotFoundEntityException::new);
        tutor.getAccount().setFirstName(tutorDto.getFirstName());
        tutor.getAccount().setMiddleName(tutorDto.getMiddleName());
        tutor.getAccount().setLastName(tutorDto.getLastName());
        tutor.setExperience(tutorDto.getExperience());
        tutor.setBio(tutorDto.getBio());

        tutor.setScienceDegree(null);
        if(tutorDto.getScienceDegree() != null)
            tutor.setScienceDegree(scienceDegreeRepository.getReferenceById(tutorDto.getScienceDegree().longValue()));

        tutor.setSex(null);
        if(tutorDto.getSex() != null)
            tutor.setSex(Sex.fromOrder(tutorDto.getSex()));
        tutorRepository.save(tutor);
    }

    public void  updateAverageRating(Long tutorId) throws NotFoundEntityException {
        List<Comment> commentList = commentRepository.findAll(CommentRepository.Specs.byTargetId(tutorId));
        OptionalDouble averageRating = commentList.stream().mapToInt(c -> c.getRating().getOrder()).average();
        Tutor tutor = tutorRepository.findById(tutorId).orElseThrow(NotFoundEntityException::new);
        tutor.setRating(averageRating.orElse(0.0));
        tutorRepository.save(tutor);
    }

    public void updatePhoto(Long tutorId, byte[] photo) {
        Tutor tutor = tutorRepository.findById(tutorId).get();
        if(tutor.getPhotoUrl() != null) photoStorageService.deletePhotoByDirectLink(tutor.getPhotoUrl());
        String url = photoStorageService.savePhotoAndGetDirectLink(tutor.getAccount().getId().toString() + ".jpg", photo);
        tutor.setPhotoUrl(url);
        tutorRepository.save(tutor);
    }

    @Transactional
    public void registerTutor(TutorRegistrationDto dto, Long accountId) {
        Account account = accountRepository.findById(accountId).get();
        account.setRole(Account.Role.TUTOR);
        Tutor tutor = new Tutor();
        tutor.setHidden(false);
        tutor.setAccount(account);

        PriceListItem item = new PriceListItem();
        item.setTutor(tutor);
        item.setDuration(dto.getDuration());
        item.setRate(dto.getRate());
        item.setSubject(subjectRepository.getReferenceById(dto.getSubject().longValue()));
        item.setType(Type.fromOrder(dto.getType()));

        MobilePhone mobilePhone = new MobilePhone();
        mobilePhone.setValue(dto.getValue());
        mobilePhone.setTutor(tutor);

        tutor.setMobilePhones(List.of(mobilePhone));
        tutor.setPriceListItems(List.of(item));
        tutorRepository.save(tutor);
    }
}
