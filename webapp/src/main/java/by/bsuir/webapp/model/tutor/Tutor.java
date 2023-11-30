package by.bsuir.webapp.model.tutor;

import by.bsuir.webapp.model.Account;

import javax.persistence.*;
import java.util.List;
import java.util.Objects;

@Entity
@Table
public class Tutor {
    @Id
    private Long id;

    @OneToOne(fetch = FetchType.LAZY)
    @MapsId
    Account account;

    @Column(nullable = true, insertable = true, updatable = true)
    private String bio;

    @Column(nullable = true, insertable = true, updatable = true)
    private Integer experience;

    @org.hibernate.annotations.Type(type = "by.bsuir.webapp.common.domain.type.OrderedEnumUserType",
            parameters = {
                    @org.hibernate.annotations.Parameter(name = "enumClass", value = "by.bsuir.webapp.model.tutor.Sex")
            })
    @Column(nullable = true, insertable = true, updatable = true)
    private Sex sex;

    @Column(nullable = false, insertable = true, updatable = true)
    private Boolean hidden;

    @Column(nullable = true, insertable = true, updatable = true)
    private String photoUrl;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = true, insertable = true, updatable = true)
    private ScienceDegree scienceDegree;

    @OneToMany(
            mappedBy = "tutor",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<MobilePhone> mobilePhones;

    @OneToMany(
            mappedBy = "tutor",
            cascade = CascadeType.ALL,
            orphanRemoval = true
    )
    private List<PriceListItem> priceListItems;

    @Column(nullable = true, insertable = true, updatable = true)
    private Double rating;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Sex getSex() {
        return sex;
    }

    public void setSex(Sex sex) {
        this.sex = sex;
    }

    public Boolean isHidden() {
        return hidden;
    }

    public void setHidden(Boolean hidden) {
        this.hidden = hidden;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoBase64) {
        this.photoUrl = photoBase64;
    }

    public ScienceDegree getScienceDegree() {
        return scienceDegree;
    }

    public void setScienceDegree(ScienceDegree scienceDegree) {
        this.scienceDegree = scienceDegree;
    }

    public List<MobilePhone> getMobilePhones() {
        return mobilePhones;
    }

    public void setMobilePhones(List<MobilePhone> mobilePhones) {
        this.mobilePhones = mobilePhones;
    }

    public List<PriceListItem> getPriceListItems() {
        return priceListItems;
    }

    public void setPriceListItems(List<PriceListItem> priceListItems) {
        this.priceListItems = priceListItems;
    }

    public Double getRating() {
        return rating;
    }

    public void setRating(Double rating) {
        this.rating = rating;
    }

    public String getBio() {
        return bio;
    }

    public void setBio(String bio) {
        this.bio = bio;
    }

    @Override
    public String toString() {
        return String.format("%s; %1.2f; %s; %s" ,
                account.toString(),
                rating,
                Objects.toString(experience, ""),
                Objects.toString(sex, ""));
    }
}
