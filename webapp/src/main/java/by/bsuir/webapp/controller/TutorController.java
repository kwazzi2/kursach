package by.bsuir.webapp.controller;

import by.bsuir.webapp.common.domain.type.OrderedEnum;
import by.bsuir.webapp.dto.ChangePasswordDto;
import by.bsuir.webapp.dto.FilterDto;
import by.bsuir.webapp.dto.TutorDto;
import by.bsuir.webapp.exception.NotFoundEntityException;
import by.bsuir.webapp.model.Event;
import by.bsuir.webapp.model.tutor.Degree;
import by.bsuir.webapp.model.tutor.Sex;
import by.bsuir.webapp.model.tutor.Tutor;
import by.bsuir.webapp.repository.EventRepository;
import by.bsuir.webapp.repository.tutor.ScienceDegreeRepository;
import by.bsuir.webapp.repository.tutor.SubjectRepository;
import by.bsuir.webapp.repository.tutor.TutorRepository;
import by.bsuir.webapp.service.TutorService;
import by.bsuir.webapp.service.security.PersitedUser;
import by.bsuir.webapp.service.security.UserService;
import by.bsuir.webapp.util.SpecificationUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.MultiValueMap;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.Part;
import javax.validation.Valid;
import java.util.*;
import java.util.stream.Collectors;

@Controller
public class TutorController {
    @Autowired
    TutorService tutorService;
    @Autowired
    TutorRepository tutorRepository;
    @Autowired
    SubjectRepository subjectRepository;
    @Autowired
    ScienceDegreeRepository scienceDegreeRepository;
    @Autowired
    UserService userService;
    @Autowired
    EventRepository eventRepository;

    @GetMapping("/")
    public String index() {
        return "redirect:/tutors/list";
    }

    @GetMapping("/tutors/details/{tutorId}")
    public String getTutor(@PathVariable Long tutorId, Model model) throws NotFoundEntityException {
        Tutor tutor = tutorRepository.findByIdAndHidden(tutorId, false).orElseThrow(NotFoundEntityException::new);
        model.addAttribute("tutor", tutor);
        return "tutor/tutor-page";
    }

    @GetMapping("/tutors/search")
    public String findTutors(@RequestParam Optional<Integer> page,
                            @RequestParam String query,
                            Model model) {
        Integer pageNum = page.orElse(1);
        PageRequest request = PageRequest.of(pageNum-1, 10, org.springframework.data.domain.Sort.unsorted());

        Specification<Tutor> specification = getTutorSpecification(query);

        Page<Tutor> tutorPage = tutorRepository.findAll(specification, request);
        model.addAttribute("tutors", tutorPage.getContent());
        model.addAttribute("totalPages", tutorPage.getTotalPages());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("showFilters", false);
        return "tutor/tutor-list";
    }

    private Specification<Tutor> getTutorSpecification(String query) {
        List<Specification<Tutor>> specifications = new ArrayList<>();
        query = "%"+ query +"%";
        specifications.add(TutorRepository.Specs.byLastNameLike(query));
        specifications.add(TutorRepository.Specs.byMiddleNameLike(query));
        specifications.add(TutorRepository.Specs.byFirstNameLike(query));
        return TutorRepository.Specs.noHidden().and(SpecificationUtil.combineWithOr(specifications));
    }

    @GetMapping("/tutors/list")
    public String getTutors(@RequestParam Optional<Integer> page,
                            @RequestParam MultiValueMap<String, String> requestParams,
                            Model model) {
        Integer pageNum = page.orElse(1);

        Map<String, List<FilterDto>> checkboxFilters = initCheckboxFilters();
        checkFilters(checkboxFilters, requestParams);

        Map<String, List<FilterDto>> radioFilters = initRadioFilters();
        checkFilters(radioFilters, requestParams);

        model.addAttribute("checkboxFilters", checkboxFilters);
        model.addAttribute("radioFilters", radioFilters);

        PageRequest request = PageRequest.of(pageNum-1, 10, org.springframework.data.domain.Sort.unsorted());

        Specification<Tutor> specification = getTutorSpecification(requestParams, checkboxFilters);

        Page<Tutor> tutorPage = tutorRepository.findAll(specification, request);
        model.addAttribute("tutors", tutorPage.getContent());
        model.addAttribute("totalPages", tutorPage.getTotalPages());
        model.addAttribute("currentPage", pageNum);
        model.addAttribute("showFilters", true);
        model.addAttribute("filterNames", Map.of("subject","Предмет","sex","Пол","degree","Степень","sort", "Сортировать"));
        return "tutor/tutor-list";
    }

    private Specification<Tutor> getTutorSpecification(MultiValueMap<String, String> requestParams, Map<String, List<FilterDto>> checkboxFilters) {
        List<Specification<Tutor>> specifications = new ArrayList<>();
        specifications.add(TutorRepository.Specs.noHidden());
        Set<String> keys = requestParams.keySet().stream().filter(checkboxFilters::containsKey).collect(Collectors.toSet());
        for (String key: keys) {
            switch (key) {
                case "sex" -> specifications.add(TutorRepository.Specs.bySexIn(
                        requestParams.get(key).stream().map(s -> Sex.fromOrder(Integer.parseInt(s))).toList()));

                case "degree" -> specifications.add(TutorRepository.Specs.byDegreeIn(
                        requestParams.get(key).stream().map(s -> Degree.fromOrder(Integer.parseInt(s))).toList()));

                case "subject" -> specifications.add(TutorRepository.Specs.bySubjectIdIn(
                        requestParams.get(key).stream().map(Long::valueOf).toList()));
            }
        }

        Specification<Tutor> specification = SpecificationUtil.combineWithAnd(specifications);
        if(requestParams.get("sort") != null) {
            switch (requestParams.get("sort").get(0)) {
                case "1" -> specification = TutorRepository.Specs.orderByRatingDesc(specification);
                case "2" -> specification = TutorRepository.Specs.orderByRatingAsc(specification);
                case "3" -> specification = TutorRepository.Specs.orderByExperience(specification);
            }
        }
        return specification;
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("tutor/profile")
    public String getProfile(@AuthenticationPrincipal PersitedUser user, Model model) {
        model.addAttribute("tutor", tutorRepository.findById(user.getAccountId()).get());
        model.addAttribute("tutorDto", new TutorDto());
        setSelectorAttributes(model);
        return "tutor/profile/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("tutor/profile")
    public String updateProfile(@ModelAttribute("tutorDto") @Valid TutorDto tutorDto,
                                BindingResult bindingResult,
                                @AuthenticationPrincipal PersitedUser user,
                                Model model) throws NotFoundEntityException {
        if (bindingResult.hasErrors()) {
            model.addAttribute("tutor", tutorRepository.findById(user.getAccountId()).get());
            setSelectorAttributes(model);
            return "tutor/profile/profile";
        }
        tutorService.update(user.getAccountId(), tutorDto);
        return "redirect:/tutor/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("tutor/settings")
    public String getSettings(@AuthenticationPrincipal PersitedUser user,
                                Model model) {
        model.addAttribute("form", new ChangePasswordDto());
        model.addAttribute("tutor", tutorRepository.findById(user.getAccountId()).get());
        return "tutor/profile/settings";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("tutor/change-password")
    public String changePassword(@ModelAttribute("form") @Valid ChangePasswordDto changePasswordDto,
                                 BindingResult bindingResult,
                                 @AuthenticationPrincipal PersitedUser user,
                                 Model model) {
        if(bindingResult.hasErrors()) {
            model.addAttribute("tutor", tutorRepository.findById(user.getAccountId()).get());
            return "tutor/profile/settings";
        }

        boolean isChanged = userService.changePassword(user.getAccountId(),
                                                changePasswordDto.getOldPassword(),
                                                changePasswordDto.getPassword());
        if(!isChanged) {
            model.addAttribute("exception", "Не удалось сменить пароль");
            model.addAttribute("tutor", tutorRepository.findById(user.getAccountId()).get());
            return "tutor/profile/settings";
        }

        return "redirect:/tutor/settings";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("tutor/set-isHidden")
    public String setIsHidden(HttpServletRequest request, @AuthenticationPrincipal PersitedUser user) {
        String param =  request.getParameter("isHidden");
        boolean isHidden = (param!=null && param.equals("on"));
        Tutor tutor = tutorRepository.findById(user.getAccountId()).get();
        tutor.setHidden(isHidden);
        tutorRepository.save(tutor);
        return "redirect:/tutor/settings";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("tutor/update-rating")
    public String setUpdateRating(@AuthenticationPrincipal PersitedUser user) throws NotFoundEntityException {
        tutorService.updateAverageRating(user.getAccountId());
        return "redirect:/tutor/settings";
    }


    @PreAuthorize("isAuthenticated()")
    @PostMapping("tutor/upload-photo")
    public String uploadPhoto(HttpServletRequest request, @AuthenticationPrincipal PersitedUser user, Model model) {
        try {
            Part filePart = request.getPart("userCoverPhoto");
            if (filePart != null) {
                byte[] photo = filePart.getInputStream().readAllBytes();
                tutorService.updatePhoto(user.getAccountId(), photo);
            }
        } catch (Exception e) {
            model.addAttribute("exception", "Не уалось обновить фото, попробуйте позже");

            model.addAttribute("tutor", tutorRepository.findById(user.getAccountId()).get());
            model.addAttribute("tutorDto", new TutorDto());
            setSelectorAttributes(model);
        }
        return "redirect:/tutor/profile";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("tutor/stat")
    public String getStat(@AuthenticationPrincipal PersitedUser user, Model model) {
        List<Event> allEvents = eventRepository.findByTargetIdAndType(user.getAccountId(), Event.Type.CHECK_TUTOR_PHONES);
        int unicUsersCount = allEvents.stream().map(e-> e.getAuthor().getId()).collect(Collectors.toSet()).size();
        model.addAttribute("allEventsCount", allEvents.size());
        model.addAttribute("unicUsersCount", unicUsersCount);
        return "tutor/profile/stat";
    }

    private void setSelectorAttributes(Model model){
        model.addAttribute("sexList", Sex.values());
        model.addAttribute("degreeList", scienceDegreeRepository.findAll());
    }

    private void checkFilters(Map<String, List<FilterDto>> filters, MultiValueMap<String, String> requestParams) {
        for(Map.Entry<String, List<FilterDto>> entry : filters.entrySet()) {
            if(requestParams.get(entry.getKey()) == null) continue;

            List<String> params = requestParams.get(entry.getKey());
            for(FilterDto dto : entry.getValue()) {
                if(params.contains(dto.getId().toString()))
                    dto.setChecked(true);
            }
        }
    }


    private Map<String, List<FilterDto>> initCheckboxFilters() {
        Map<String, List<FilterDto>> filters = new HashMap<>();

        List<FilterDto> sexFilter = Arrays.stream(Sex.values()).map(s -> new FilterDto((long) s.getOrder(), s.getName())).toList();
        filters.put("sex", sexFilter);

        List<FilterDto> degreeFilter = Arrays.stream(Degree.values()).map(d -> new FilterDto((long) d.getOrder(), d.getName())).toList();
        filters.put("degree", degreeFilter);

        List<FilterDto> subjectFilter = subjectRepository.findAll().stream().map(s -> new FilterDto(s.getId(), s.getName())).toList();
        filters.put("subject", subjectFilter);
        return filters;
    }

    private Map<String, List<FilterDto>> initRadioFilters() {
        Map<String, List<FilterDto>> radioFilters = new HashMap<>();
        List<FilterDto> sortFilter = Arrays.stream(Sort.values()).map(s -> new FilterDto((long) s.getOrder(), s.getName())).toList();
        radioFilters.put("sort", sortFilter);
        return radioFilters;
    }

    public enum Sort implements OrderedEnum {
        RATING_ASC(1, "По возрастанию рейтинга"),
        RATING_DESC(2, "По убыванию рейтинга"),
        EXPERIENCE_ASC(3, "Сначала опытные");


        private final int order;
        private final String name;

        Sort(int order, String name) {
            this.order = order;
            this.name = name;
        }

        @Override
        public int getOrder() {
            return order;
        }

        public static Sort fromOrder(int order) {
            for (Sort sort : Sort.values()) {
                if (sort.order == order) {
                    return sort;
                }
            }
            return null;
        }

        public String getName() {
            return name;
        }
    }

}
