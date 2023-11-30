package by.bsuir.webapp.controller;

import by.bsuir.webapp.dto.PhoneDto;
import by.bsuir.webapp.exception.NotFoundEntityException;
import by.bsuir.webapp.model.tutor.MobilePhone;
import by.bsuir.webapp.repository.tutor.MobilePhonesRepository;
import by.bsuir.webapp.service.EventService;
import by.bsuir.webapp.service.MobilePhoneService;
import by.bsuir.webapp.service.security.PersitedUser;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class MobilePhoneController {
    @Autowired
    MobilePhonesRepository mobilePhonesRepository;
    @Autowired
    MobilePhoneService mobilePhoneService;
    @Autowired
    EventService eventService;

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/phones/{tutorId}")
    public String getPhones(@PathVariable Long tutorId, Model model, @AuthenticationPrincipal PersitedUser user) {
        eventService.checkPhones(user, tutorId);
        model.addAttribute("phones", mobilePhonesRepository.findByTutorId(tutorId));
        return "tutor/phones";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/tutor/phones")
    public String getPhones(@AuthenticationPrincipal PersitedUser user, Model model,
                            @RequestParam Optional<String> exception) {
        exception.ifPresent(c ->model.addAttribute("exception", c));
        model.addAttribute("phones", mobilePhonesRepository.findByTutorId(user.getAccountId()));
        return "/tutor/profile/phones";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/phone/{phoneId}/delete")
    public String deletePhone(@AuthenticationPrincipal PersitedUser user,  @PathVariable Long phoneId, Model model) throws NotFoundEntityException {
        MobilePhone phone = mobilePhonesRepository.findByIdAndTutorId(phoneId, user.getAccountId())
                .orElseThrow(NotFoundEntityException::new);
        if(mobilePhonesRepository.countByTutorId(user.getAccountId()) == 1L) {
            model.addAttribute("exception", "Не может быть меньше 1");
            return "redirect:/tutor/phones";
        }
        mobilePhonesRepository.delete(phone);
        return "redirect:/tutor/phones";
    }

    @PreAuthorize("isAuthenticated()")
    @GetMapping("/phone/editor")
    public String getPhoneForm(@RequestParam Optional<Long> phoneId, Model model) throws NotFoundEntityException {
        if(phoneId.isEmpty()) {
            model.addAttribute("form", new PhoneDto());
            return "/tutor/profile/phone-form";
        }

        MobilePhone phone = mobilePhonesRepository.findById(phoneId.get())
                .orElseThrow(NotFoundEntityException::new);
        model.addAttribute("phoneValue", phone.getValue());
        model.addAttribute("phoneId", phoneId.get());
        model.addAttribute("form", new PhoneDto());
        return "/tutor/profile/phone-form";
    }

    @PreAuthorize("isAuthenticated()")
    @PostMapping("/save-phone")
    public String savePhone(@ModelAttribute("form") @Valid PhoneDto phoneDto,
                            BindingResult bindingResult,
                            @AuthenticationPrincipal PersitedUser user,
                            @RequestParam Optional<Long> phoneId) throws NotFoundEntityException {
        if(bindingResult.hasErrors())
            return "/tutor/profile/phone-form";

        if(phoneId.isPresent()) {
            mobilePhoneService.update(user.getAccountId(), phoneId.get(), phoneDto.getValue());
        } else {
            mobilePhoneService.create(user.getAccountId(), phoneDto.getValue());
        }
        return "redirect:/tutor/phones";
    }


}
