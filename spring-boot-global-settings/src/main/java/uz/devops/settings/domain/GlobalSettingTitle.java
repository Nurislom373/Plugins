package uz.devops.settings.domain;

import lombok.*;

import javax.persistence.*;

/**
 * @author Nurislom
 * @see uz.devops.settings.domain
 * @since 11/23/2023 4:28 PM
 */
@Getter
@Setter
@Entity
@ToString
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "global_setting_title")
public class GlobalSettingTitle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "title", nullable = false)
    private String title;

    @Column(name = "language", nullable = false)
    private String language;

}
