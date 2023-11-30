package by.bsuir.webapp.dto;

import java.util.Objects;

public class FilterDto {
    private Long id;
    private String name;
    private boolean isChecked;

    public FilterDto() {
    }

    public FilterDto(Long id, String name) {
        this.id = id;
        this.name = name;
        this.isChecked = false;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public boolean isChecked() {
        return isChecked;
    }

    public void setChecked(boolean checked) {
        isChecked = checked;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        FilterDto filterDto = (FilterDto) o;
        return Objects.equals(id, filterDto.id);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id);
    }
}
