package by.bsuir.webapp.controller;

import by.bsuir.webapp.dto.TutorRegistrationDto;
import by.bsuir.webapp.dto.UserDto;
import by.bsuir.webapp.model.tutor.Type;
import by.bsuir.webapp.repository.tutor.SubjectRepository;
import by.bsuir.webapp.service.TutorService;
import by.bsuir.webapp.service.security.PersitedUser;
import by.bsuir.webapp.service.security.UserService;
import by.bsuir.webapp.service.security.exception.NonUniqueUsernameException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.validation.Valid;
import java.util.Optional;

@Controller
public class AuthController {
    @Autowired
    UserService userService;
    @Autowired
    TutorService tutorService;
    @Autowired
    SubjectRepository subjectRepository;

    @GetMapping("/login")
    public String login(Model model, String error) {
        if (error != null)
            model.addAttribute("errorMsg", "Некорректный email или пароль");
        return "auth/login";
    }

    @GetMapping("/registration")
    public String getRegistrationForm(Model model) {
        model.addAttribute("user", new UserDto());
        return "auth/registration";
    }

    @GetMapping("/registration/enable-account")
    public String enableAccount(@RequestParam Optional<String> hash, Model model) {
        try {
            userService.enableUserAccount(hash.get());
            model.addAttribute("message", "Регистрация завершена");
        } catch (Exception e) {
            model.addAttribute("message", "Не удалось завершить регистрацию");
        }
        return "auth/enable-account";
    }

    @PostMapping("/registration")
    public String registerAccount(@ModelAttribute("user") @Valid UserDto userDto, BindingResult bindingResult, Model model) {
        if (bindingResult.hasErrors())
            return "auth/registration";
        try {
            userService.registerUser(userDto);
        } catch (NonUniqueUsernameException e) {
            model.addAttribute("exception", "Адрес электронной почты уже успользуется");
            return "auth/registration";
        } catch (Exception e) {
            model.addAttribute("exception", "Ошибка регистрации");
            return "auth/registration";
        }
        return "auth/success-registration";
    }


    @PreAuthorize("isAuthenticated() && hasAuthority('USER')")
    @GetMapping("/tutor-registration")
    public String getTutorRegistrationForm(Model model) {
        model.addAttribute("form", new TutorRegistrationDto());
        model.addAttribute("typeList", Type.values());
        model.addAttribute("subjectList", subjectRepository.findAll());
        return "auth/tutor-registration";
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('USER')")
    @PostMapping("/tutor-registration")
    public String registerTutor(@ModelAttribute("form") @Valid TutorRegistrationDto dto,
                                BindingResult bindingResult, Model model,
                                @AuthenticationPrincipal PersitedUser user) {
        if (bindingResult.hasErrors()) {
            model.addAttribute("typeList", Type.values());
            model.addAttribute("subjectList", subjectRepository.findAll());
            return "auth/tutor-registration";
        }

        tutorService.registerTutor(dto, user.getAccountId());

        return "redirect:/logout";
    }
}
