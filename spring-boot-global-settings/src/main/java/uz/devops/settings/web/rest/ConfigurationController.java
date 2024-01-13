package uz.devops.settings.web.rest;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.devops.settings.repository.GlobalSettingFieldsRepository;
import uz.devops.settings.repository.GlobalSettingInfoRepository;
import uz.devops.settings.service.dto.UpdateField;
import uz.devops.settings.service.dto.configuration.Configuration;
import uz.devops.settings.service.configuration.ConfigurationService;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;

/**
 * @author Nurislom
 * @see uz.devops.settings.web.rest
 * @since 11/23/2023 4:16 PM
 */
@Slf4j
@RestController
@RequestMapping(value = "/api/global/setting")
public class ConfigurationController {

    private final ConfigurationService configurationService;
    private final GlobalSettingInfoRepository globalSettingInfoRepository;
    private final GlobalSettingFieldsRepository globalSettingFieldsRepository;

    public ConfigurationController(GlobalSettingFieldsRepository globalSettingFieldsRepository, ConfigurationService configurationService,
                                   GlobalSettingInfoRepository globalSettingInfoRepository) {
        this.globalSettingFieldsRepository = globalSettingFieldsRepository;
        this.configurationService = configurationService;
        this.globalSettingInfoRepository = globalSettingInfoRepository;
    }

    @GetMapping
    public ResponseEntity<List<Configuration>> getGlobalConfigurationWithFields() {
        log.debug("REST request to get list of global setting configurations");
        return ResponseEntity.ok(configurationService.getGlobalConfigurationsWithFields());
    }

    @GetMapping(value = "/configurations")
    public ResponseEntity<List<Configuration>> getGlobalConfigurations() {
        log.debug("REST request to get list of global setting only configurations");
        return ResponseEntity.ok(configurationService.getConfigurations());
    }

    @GetMapping(value = "/fields/{infoId}")
    public ResponseEntity<Object> getConfigurationFields(@PathVariable("infoId") Long id) {
        if (Objects.isNull(id)) {
            return new ResponseEntity<>("infoId must not be null!", HttpStatus.BAD_REQUEST);
        }
        if (!globalSettingInfoRepository.existsById(id)) {
            return new ResponseEntity<>("Configuration not found by id!", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(configurationService.getConfigurationFields(id));
    }

    @PutMapping(value = "/field")
    public ResponseEntity<Object> updateConfigurationField(@Valid @RequestBody UpdateField field) {
        log.debug("REST request to update global setting configuration field");
        if (Objects.isNull(field)) {
            return new ResponseEntity<>("update field body must not be null!", HttpStatus.BAD_REQUEST);
        }
        if (!globalSettingFieldsRepository.existsById(field.getId())) {
            return new ResponseEntity<>("configuration field not found by id!", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(configurationService.updateField(field));
    }

    @PutMapping(value = "/fields")
    public ResponseEntity<Object> updateConfigurationFields(@Valid @RequestBody List<UpdateField> fields) {
        log.debug("REST request to update global setting configuration fields");
        if (Objects.isNull(fields)) {
            return new ResponseEntity<>("update fields body must not be null!", HttpStatus.BAD_REQUEST);
        }
        if (fields.isEmpty()) {
            return new ResponseEntity<>("update fields body must not be empty!", HttpStatus.BAD_REQUEST);
        }
        return ResponseEntity.ok(configurationService.updateFields(fields));
    }

}
