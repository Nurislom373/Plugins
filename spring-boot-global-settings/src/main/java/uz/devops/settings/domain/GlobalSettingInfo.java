package uz.devops.settings.domain;

import lombok.*;

import javax.persistence.*;
import java.io.Serializable;
import java.util.List;

/**
 * @author Nurislom
 * @see uz.devops.settings.domain
 * @since 11/21/2023 2:17 PM
 */
@Getter
@Setter
@Entity
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "global_setting_info")
public class GlobalSettingInfo implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "parent_id")
    private Long parentId;

    @Column(name = "class_name", nullable = false)
    private String className;

    @Column(name = "implement_class", nullable = false)
    private String implementClass;

    @OneToMany(cascade = {CascadeType.REMOVE})
    @JoinTable(
            name = "global_setting_info_title",
            joinColumns = {@JoinColumn(name = "global_setting_info_id", referencedColumnName = "id")},
            inverseJoinColumns = {@JoinColumn(name = "global_setting_title_id", referencedColumnName = "id")}
    )
    private List<GlobalSettingTitle> titles;

}
