package by.bsuir.webapp.controller;

import by.bsuir.webapp.exception.NotFoundEntityException;
import by.bsuir.webapp.repository.AccountRepository;
import by.bsuir.webapp.service.security.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class AdminController {
    @Autowired
    AccountRepository accountRepository;

    @Autowired
    UserService userService;

    @PreAuthorize("isAuthenticated() && hasAuthority('ADMIN')")
    @GetMapping("admin/accounts")
    public String getAccounts(Model model) {
        model.addAttribute("accounts", accountRepository.findAll());
        return "/admin/account-list";
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('ADMIN')")
    @PostMapping("admin/account/{id}/lock")
    public String lockAccount(Model model, @PathVariable Long id) throws NotFoundEntityException {
        userService.lockUserAccount(id);
        model.addAttribute("accounts", accountRepository.findAll());
        return "/admin/account-list";
    }

    @PreAuthorize("isAuthenticated() && hasAuthority('ADMIN')")
    @PostMapping("admin/account/{id}/unlock")
    public String unlockAccount(Model model, @PathVariable Long id) throws NotFoundEntityException {
        userService.unlockUserAccount(id);
        model.addAttribute("accounts", accountRepository.findAll());
        return "/admin/account-list";
    }

}
