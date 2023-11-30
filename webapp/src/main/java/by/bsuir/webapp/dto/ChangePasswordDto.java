package by.bsuir.webapp.dto;

import by.bsuir.webapp.util.validation.password.PasswordForm;
import by.bsuir.webapp.util.validation.password.PasswordMatches;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@PasswordMatches(message = "пароли дожны совпадать")
public class ChangePasswordDto implements PasswordForm {
    @NotNull
    @NotEmpty(message = "пароль не может быть пустым")
    private String oldPassword;
    @NotNull
    @NotEmpty(message = "пароль не может быть пустым")
    private String password;
    private String matchingPassword;

    public String getOldPassword() {
        return oldPassword;
    }

    public void setOldPassword(String oldPassword) {
        this.oldPassword = oldPassword;
    }

    @Override
    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
    @Override
    public String getMatchingPassword() {
        return matchingPassword;
    }

    public void setMatchingPassword(String matchingPassword) {
        this.matchingPassword = matchingPassword;
    }
}
