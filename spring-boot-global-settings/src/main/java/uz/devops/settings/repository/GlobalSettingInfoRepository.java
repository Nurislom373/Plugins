package uz.devops.settings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.devops.settings.domain.GlobalSettingFields;
import uz.devops.settings.domain.GlobalSettingInfo;
import uz.devops.settings.service.dto.projection.GlobalSettingProjection;

import java.util.List;
import java.util.Optional;

/**
 * @author Nurislom
 * @see uz.devops.settings.repository
 * @since 11/21/2023 2:19 PM
 */
@Repository
public interface GlobalSettingInfoRepository extends JpaRepository<GlobalSettingInfo, Long> {

    Optional<GlobalSettingInfo> findTop1ByImplementClass(String implementClass);

    boolean existsByImplementClass(String implementClass);

    @Query(value = "select gs.id as id, gs.class_name as className, gs.parent_id as parentId, " +
            "gs.implement_class as implementClass, gst.title " +
            "from global_setting_info gs " +
            "left join global_setting_info_title gsit on gsit.global_setting_info_id = gs.id " +
            "left join global_setting_title gst on gst.id = gsit.global_setting_title_id " +
            "where (gst.language IS NOT NULL and gst.language = :lang) OR gst.language IS NULL", nativeQuery = true)
    List<GlobalSettingProjection> findAllByQuery(@Param("lang") String lang);

}
