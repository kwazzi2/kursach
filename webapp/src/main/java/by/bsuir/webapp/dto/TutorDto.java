package by.bsuir.webapp.dto;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class TutorDto {
    @NotNull
    @NotEmpty(message = "имя не может быть пустым")
    private String firstName;
    @NotNull
    @NotEmpty(message = "фамилия не может быть пустым")
    private String lastName;
    private String middleName;
    private String bio;
    @PositiveOrZero(message = "от 0 до 100")
    @Max(value = 100, message = "от 0 до 100")
    private Integer experience;
    private Integer sex;
    private Integer scienceDegree;

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getMiddleName() {
        return middleName;
    }

    public void setMiddleName(String middleName) {
        this.middleName = middleName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Integer getSex() {
        return sex;
    }

    public void setSex(Integer sex) {
        this.sex = sex;
    }

    public Integer getScienceDegree() {
        return scienceDegree;
    }

    public void setScienceDegree(Integer scienceDegree) {
        this.scienceDegree = scienceDegree;
    }
}
