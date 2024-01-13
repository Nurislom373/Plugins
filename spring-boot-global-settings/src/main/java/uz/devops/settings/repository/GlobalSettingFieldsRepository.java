package uz.devops.settings.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import uz.devops.settings.domain.GlobalSettingFields;
import uz.devops.settings.service.dto.projection.GlobalSettingFieldsProjection;

import java.util.List;
import java.util.Optional;

@Repository
public interface GlobalSettingFieldsRepository extends JpaRepository<GlobalSettingFields, Long> {

    List<GlobalSettingFields> findAllByInfo_Id(Long info_id);

    Optional<GlobalSettingFields> findTop1ByInfo_IdAndFieldName(Long info_id, String fieldName);

    boolean existsByFieldName(String fieldName);

    Optional<GlobalSettingFields> findByFieldName(String fieldName);

    @Query(value = "select gs.id as id, gs.field_name as fieldName, gs.field_class_type as fieldClassType, " +
            "gs.field_type as fieldType, gs.input_type as inputType, gs.field_value as fieldValue, gst.title as title " +
            "from global_setting_fields gs " +
            "left join global_setting_info gsi on gsi.id = gs.global_setting_info_id " +
            "left join global_setting_fields_title gsft on gsft.global_setting_fields_id = gs.id " +
            "left join global_setting_title gst on gst.id = gsft.global_setting_title_id " +
            "where gsi.id = :info_id and ((gst.language IS NOT NULL and gst.language = :lang) OR gst.language IS NULL)",
            nativeQuery = true)
    List<GlobalSettingFieldsProjection> findAllByQuery(@Param("info_id") Long infoId, @Param("lang") String lang);

    @Query(value = "select gsf.id as id, gsf.field_name as fieldName, gsf.field_class_type as fieldClassType, " +
            "gsf.field_type as fieldType, gsf.input_type as inputType, gsf.field_value as fieldValue, gst.title as title " +
            "from global_setting_fields gsf " +
            "left join global_setting_fields_title gsft on gsft.global_setting_fields_id = gsf.id " +
            "left join global_setting_title gst on gst.id = gsft.global_setting_title_id " +
            "where gsf.id = :field_id and ((gst.language IS NOT NULL and gst.language = :lang) OR gst.language IS NULL)",
            nativeQuery = true)
    GlobalSettingFieldsProjection findOneByQuery(@Param("field_id") Long fieldId, @Param("lang") String lang);

}
