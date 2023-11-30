package by.bsuir.webapp.service;

import by.bsuir.webapp.model.Account;
import by.bsuir.webapp.model.comment.Comment;
import by.bsuir.webapp.model.comment.Rating;
import by.bsuir.webapp.model.tutor.*;
import by.bsuir.webapp.repository.AccountRepository;
import by.bsuir.webapp.repository.comment.CommentRepository;
import by.bsuir.webapp.repository.tutor.*;
import by.bsuir.webapp.service.external.GoogleDrivePhotoStorageService;
import org.apache.commons.lang3.RandomStringUtils;
import org.apache.commons.lang3.RandomUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import javax.annotation.PostConstruct;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.*;

@Service
public class InitService {
    @Autowired
    AccountRepository accountRepository;
    @Autowired
    TutorRepository tutorRepository;
    @Autowired
    PasswordEncoder passwordEncoder;
    @Autowired
    CommentRepository commentRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    MobilePhonesRepository mobilePhonesRepository;
    @Autowired
    ScienceDegreeRepository scienceDegreeRepository;
    @Autowired
    PriceListItemRepository priceListItemRepository;

    @Autowired
    GoogleDrivePhotoStorageService photoStorageService;

    private void generateAdmin() {
        Account account = new Account();
        account.setRole(Account.Role.ADMIN);
        account.setEnabled(true);
        account.setPassword(passwordEncoder.encode("1234"));
        account.setFirstName("admin");
        account.setLastName("admin");
        account.setEmail("admin");
        accountRepository.save(account);
    }
    private void generateUsers() {
        List<Account> accountList = new ArrayList<>();
        try (Scanner myReader = new Scanner(new File("users.txt"))){
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                String[] io = line.split(" ");
                Account account = new Account();
                account.setEnabled(true);
                account.setRole(Account.Role.USER);
                account.setPassword(passwordEncoder.encode("1234"));
                account.setFirstName(io[1]);
                account.setLastName(io[0]);
                account.setEmail(RandomStringUtils.randomAlphabetic(9)+"@mail.ru");
                accountList.add(account);
            }
        } catch (FileNotFoundException ignored) {}

        accountRepository.saveAll(accountList);
    }

    private void generateTutors() {
        List<Tutor> tutorList = new ArrayList<>();
        try (Scanner myReader = new Scanner(new File("tutorsw.txt"))){
            while (myReader.hasNextLine()) {
                String line = myReader.nextLine();
                String[] io = line.split(" ");
                Account account = new Account();
                account.setEnabled(true);
                account.setRole(Account.Role.TUTOR);
                account.setPassword(passwordEncoder.encode("1234"));
                account.setFirstName(io[1]);
                account.setLastName(io[0]);
                account.setMiddleName(io[2]);
                account.setEmail(RandomStringUtils.randomAlphabetic(6)+"@yandex.by");
                Tutor tutor = new Tutor();
                tutor.setAccount(account);
                tutor.setExperience(RandomUtils.nextInt(1,10));
                tutor.setRating(RandomUtils.nextDouble(1.0, 5.0));
                tutor.setHidden(false);
                tutor.setSex(Sex.FEMALE);
                tutorList.add(tutor);
            }
        } catch (FileNotFoundException ignored) {}

        tutorRepository.saveAll(tutorList);
    }

    private void addComments(){
        List<String> bodyList = new ArrayList<>();
        List<Comment> comments = new ArrayList<>();
        try (Scanner myReader = new Scanner(new File("comments.txt"))){
            while (myReader.hasNextLine())
                bodyList.add(myReader.nextLine());
        } catch (FileNotFoundException ignored) {}

        List<Tutor> tutorList = tutorRepository.findAll();
        List<Account> accountList = accountRepository.findByRole(Account.Role.USER);

        for (Tutor  tutor: tutorList) {
            int commentCount = RandomUtils.nextInt(7,accountList.size());
            for (int i=0;i<commentCount;i++) {
                generateComment(tutor, accountList.get(i), bodyList);
            }
        }

    }

    private void generateComment(Tutor tutor, Account account, List<String> bodyList) {
        Comment comment = new Comment();
        comment.setAuthor(account);
        comment.setTarget(accountRepository.getReferenceById(tutor.getAccount().getId()));
        comment.setRating(Rating.fromOrder(RandomUtils.nextInt(1,6)));
        int index = RandomUtils.nextInt(0,bodyList.size()+4);
        if (index< bodyList.size())
            comment.setBody(bodyList.get(index));
        Calendar calendar = Calendar.getInstance();
        calendar.add(Calendar.DAY_OF_MONTH, -RandomUtils.nextInt(0, 23));
        comment.setCreatedAt(calendar.getTime());
        commentRepository.save(comment);
    }

    private void generateSubjects() {
        try (Scanner myReader = new Scanner(new File("subjects.txt"))){
            while (myReader.hasNextLine()) {
                Subject subject = new Subject();
                subject.setName(myReader.nextLine());
                subjectRepository.save(subject);
            }
        } catch (FileNotFoundException ignored) {}
    }

    private void generateScienceDegree() {
        List<String> list = List.of("экономических наук", "физических наук", "математических наук", "лингвистических наук", "юридических наук", "социальных наук", "химических наук", "биологических наук", "педагогических наук");
        for(Degree degree: Degree.values()) {
            list.forEach(l -> {
                ScienceDegree scienceDegree = new ScienceDegree();
                scienceDegree.setDegree(degree);
                scienceDegree.setName(degree.getName() + " " + l);
                scienceDegreeRepository.save(scienceDegree);
            });
        }
    }

    private void generatePriceList() {
        List<Subject> subjectList = subjectRepository.findAll();
        for(Tutor tutor : tutorRepository.findAll()){
            for(int i =0; i<RandomUtils.nextInt(1, 4); i++) {
                PriceListItem priceListItem = new PriceListItem();
                priceListItem.setDuration(RandomUtils.nextInt(2, 5) * 30);
                priceListItem.setTutor(tutor);
                priceListItem.setRate(RandomUtils.nextInt(10, 50));
                priceListItem.setType(Type.fromOrder(RandomUtils.nextInt(1, 4)));
                priceListItem.setSubject(subjectList.get(RandomUtils.nextInt(0, subjectList.size())));
                priceListItemRepository.save(priceListItem);
            }
        }

    }

    private void addImagesMale() {
        try (Scanner myReader = new Scanner(new File("urlsm.txt"))){
            List<Tutor> tutorList = tutorRepository.findAll(TutorRepository.Specs.bySexIn(List.of(Sex.MALE)));
            int i = 0;
            while (myReader.hasNextLine()) {
                String url = myReader.nextLine();
                try(InputStream in = new URL(url).openStream()) {
                    byte[] fileContent = in.readAllBytes();
                    Tutor tutor = tutorList.get(i);
                    String url1 = photoStorageService.savePhotoAndGetDirectLink(tutor.getAccount().getId().toString() + ".jpg", fileContent);
                    tutor.setPhotoUrl(url1);
                    tutorRepository.save(tutor);
                } catch (IOException e) {
                }
                i++;
            }
        } catch (FileNotFoundException ignored) {}

        try (Scanner myReader = new Scanner(new File("urlsw.txt"))){
            List<Tutor> tutorList = tutorRepository.findAll(TutorRepository.Specs.bySexIn(List.of(Sex.FEMALE)));
            int i = 0;
            while (myReader.hasNextLine()) {
                String url = myReader.nextLine();
                try(InputStream in = new URL(url).openStream()) {
                    byte[] fileContent = in.readAllBytes();
                    Tutor tutor = tutorList.get(i);
                    String url1 = photoStorageService.savePhotoAndGetDirectLink(tutor.getAccount().getId().toString() + ".jpg", fileContent);
                    tutor.setPhotoUrl(url1);
                    tutorRepository.save(tutor);
                } catch (IOException e) {
                }
                i++;
            }
        } catch (FileNotFoundException ignored) {}

    }

    private void generatePhones() {
        for(Tutor tutor: tutorRepository.findAll()) {
            MobilePhone mobilePhone = new MobilePhone();
            mobilePhone.setValue("375"+RandomStringUtils.randomAlphanumeric(9));
            mobilePhone.setTutor(tutor);
            mobilePhonesRepository.save(mobilePhone);
        }
    }

    private void generateBio() {
        List<String> lines = new ArrayList<>();
        try (Scanner myReader = new Scanner(new File("bio.txt"))){
            while (myReader.hasNextLine()) lines.add(myReader.nextLine());
        } catch (FileNotFoundException ignored) {}

        for(Tutor tutor: tutorRepository.findAll()){
            String bio = "";
            for(int i=0; i<RandomUtils.nextInt(1,5); i++) {
                bio += lines.get(RandomUtils.nextInt(0, lines.size()));
            }
            tutor.setBio(bio);
            tutorRepository.save(tutor);
        }
    }

    private void setScienceDegree(){
        List<ScienceDegree> scienceDegrees = scienceDegreeRepository.findAll();
        for(Tutor tutor:tutorRepository.findAll()) {
            tutor.setScienceDegree(scienceDegrees.get(RandomUtils.nextInt(0,scienceDegrees.size())));
            tutorRepository.save(tutor);
        }
    }

}
