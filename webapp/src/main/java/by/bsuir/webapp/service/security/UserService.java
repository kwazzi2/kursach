package by.bsuir.webapp.service.security;

import by.bsuir.webapp.dto.UserDto;
import by.bsuir.webapp.exception.NotFoundEntityException;
import by.bsuir.webapp.model.Account;
import by.bsuir.webapp.repository.AccountRepository;
import by.bsuir.webapp.service.mail.MailService;
import by.bsuir.webapp.service.security.exception.NonUniqueUsernameException;
import org.apache.commons.lang3.RandomStringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.transaction.annotation.Transactional;

import javax.mail.MessagingException;
import java.util.List;
import java.util.Optional;

public class UserService implements UserDetailsService {

    @Autowired
    AccountRepository accountRepository;
    @Autowired
    MailService mailService;

    public void setPasswordEncoder(PasswordEncoder passwordEncoder) {
        this.passwordEncoder = passwordEncoder;
    }

    private PasswordEncoder passwordEncoder;

    @Transactional
    public void lockUserAccount(Long accountId) throws NotFoundEntityException {
        Account account = accountRepository.findById(accountId).orElseThrow(NotFoundEntityException::new);
        account.setLocked(true);
    }
    @Transactional
    public void unlockUserAccount(Long accountId) throws NotFoundEntityException {
        Account account = accountRepository.findById(accountId).orElseThrow(NotFoundEntityException::new);
        account.setLocked(false);
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Account account = accountRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("User" + username +  "not found"));
        GrantedAuthority grantedAuthority = new SimpleGrantedAuthority(account.getRole().name());
        return new PersitedUser(account.getId(), account.getEmail(), account.getPassword(),
                account.isEnabled(),
                true,
                true,
                !account.isLocked(),
                List.of(grantedAuthority));
    }

    @Transactional(rollbackFor = {MessagingException.class, NonUniqueUsernameException.class})
    public void registerUser(UserDto userDto) throws MessagingException, NonUniqueUsernameException {
        String hash = RandomStringUtils.randomAlphabetic(32);

        checkUserEmail(userDto.getEmail());

        Account account = new Account();
        account.setEmail(userDto.getEmail());
        account.setFirstName(userDto.getFirstName());
        account.setLastName(userDto.getLastName());
        account.setPassword(passwordEncoder.encode(userDto.getPassword()));
        account.setEnabled(false);
        account.setHash(hash);
        account.setRole(Account.Role.USER);
        accountRepository.save(account);

        String message = generateHtmlRegistrationMessage(getEmailConfirmLink(hash));
        mailService.sendHtml(userDto.getEmail(), "Регистрация", message);
    }

    public boolean changePassword(Long accountId, String oldPass, String newPass){
        Account account = accountRepository.findById(accountId).get();
        if(!passwordEncoder.matches(oldPass, account.getPassword())) return false;
        account.setPassword(passwordEncoder.encode(newPass));
        accountRepository.save(account);
        return true;

    }

    public void checkUserEmail(String email) throws NonUniqueUsernameException {
        Optional<Account> optional = accountRepository.findByEmail(email);
        if(optional.isPresent())
            throw new NonUniqueUsernameException();
    }

    public void enableUserAccount(String hash) throws NotFoundEntityException {
        Account account =  accountRepository.findByHash(hash).orElseThrow(NotFoundEntityException::new);
        account.setHash(null);
        account.setEnabled(true);
        accountRepository.save(account);
    }

    private String generateHtmlRegistrationMessage(String url) {
        return "<p>" +
                "Для завершения процесса регистрации перейдите по " +
                "<a href=\"" +url + "\">ссылке</a>" +
                "</p>";
    }

    private String getEmailConfirmLink(String hash) {
        return "http://localhost:8080" + "/registration/enable-account" + "?hash=" + hash;
    }

}
