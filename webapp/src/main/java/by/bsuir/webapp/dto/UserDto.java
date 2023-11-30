package by.bsuir.webapp.dto;

import by.bsuir.webapp.util.validation.password.PasswordForm;
import by.bsuir.webapp.util.validation.password.PasswordMatches;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

@PasswordMatches(message = "пароли дожны совпадать")
public class UserDto implements PasswordForm {
    @NotNull
    @NotEmpty(message = "имя не может быть пустым")
    private String firstName;

    @NotNull
    @NotEmpty(message = "фамилия не может быть пустым")
    private String lastName;

    @NotNull
    @NotEmpty(message = "пароль не может быть пустым")
    private String password;

    private String matchingPassword;

    @Email(message = "некоректный адрес электронной почты")
    @NotNull
    @NotEmpty(message = "электронная почта не может быть пустым")
    private String email;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
