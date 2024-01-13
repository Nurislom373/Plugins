package uz.devops.settings.domain;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import uz.devops.settings.domain.enumuration.InputType;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@Entity
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "global_setting_fields")
public class GlobalSettingFields implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "field_name", length = 256, nullable = false)
    private String fieldName;

    @Enumerated(EnumType.STRING)
    @Column(name = "input_type", nullable = false)
    private InputType inputType;

    @Column(name = "field_type", nullable = false)
    private String fieldType;

    @Column(name = "field_class_type", nullable = false)
    private String fieldClassType;

    @Lob
    @Column(name = "field_value")
    private String fieldValue;

    @ManyToOne
    @JoinColumn(name = "global_setting_info_id", nullable = false,
            foreignKey = @ForeignKey(name = "fk_global_setting_info_id"))
    private GlobalSettingInfo info;

    @OneToMany(cascade = CascadeType.MERGE)
    @JoinTable(
            name = "global_setting_fields_title",
            joinColumns = {@JoinColumn(name = "global_setting_fields_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "global_setting_title_id", referencedColumnName = "id")}
    )
    private List<GlobalSettingTitle> titles;

    public GlobalSettingFields(String fieldName, String fieldValue) {
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    public GlobalSettingFields(Long id, String fieldName, String fieldValue) {
        this.id = id;
        this.fieldName = fieldName;
        this.fieldValue = fieldValue;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        GlobalSettingFields that = (GlobalSettingFields) o;

        if (!Objects.equals(id, that.id)) return false;
        if (!Objects.equals(fieldName, that.fieldName)) return false;
        if (!Objects.equals(inputType, that.inputType)) return false;
        if (!Objects.equals(fieldType, that.fieldType)) return false;
        if (!Objects.equals(fieldClassType, that.fieldClassType))
            return false;
        return Objects.equals(fieldValue, that.fieldValue);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + (fieldName != null ? fieldName.hashCode() : 0);
        result = 31 * result + (inputType != null ? inputType.hashCode() : 0);
        result = 31 * result + (fieldType != null ? fieldType.hashCode() : 0);
        result = 31 * result + (fieldClassType != null ? fieldClassType.hashCode() : 0);
        result = 31 * result + (fieldValue != null ? fieldValue.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "GlobalSetting{" +
                "id=" + id +
                ", fieldName='" + fieldName + '\'' +
                ", inputType='" + inputType + '\'' +
                ", fieldType='" + fieldType + '\'' +
                ", fieldClassType='" + fieldClassType + '\'' +
                ", fieldValue='" + fieldValue + '\'' +
                '}';
    }
}
