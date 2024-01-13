package uz.devops.settings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import uz.devops.settings.domain.GlobalSettingTitle;

/**
 * @author Nurislom
 * @see uz.devops.settings.repository
 * @since 11/25/2023 3:47 PM
 */
@Repository
public interface GlobalSettingTitleRepository extends JpaRepository<GlobalSettingTitle, Long> {
}
