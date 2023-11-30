package by.bsuir.webapp.repository;

import by.bsuir.webapp.model.Account;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface AccountRepository extends JpaRepository<Account, Long> {

    Optional<Account> findByEmail(String email);

    Optional<Account> findByHash(String hash);

    List<Account> findByRole(Account.Role role);
}
