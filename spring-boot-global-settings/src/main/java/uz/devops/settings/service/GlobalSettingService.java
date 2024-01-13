package uz.devops.settings.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import uz.devops.settings.domain.GlobalSettingFields;
import uz.devops.settings.repository.GlobalSettingFieldsRepository;

import java.util.List;
import java.util.Optional;

//@Service
public class GlobalSettingService {
    private static final Logger log = LoggerFactory.getLogger(GlobalSettingService.class);
//    @Autowired
    private GlobalSettingFieldsRepository globalSettingRepository;

    public Optional<GlobalSettingFields> findOne(Long settingId) {
        log.info("find setting by id: {}", settingId);
        return this.globalSettingRepository.findById(settingId);
    }

    public List<GlobalSettingFields> findAll() {
        log.info("find all settings");
        return this.globalSettingRepository.findAll();
    }

    public GlobalSettingFields save(GlobalSettingFields globalSetting) {
        log.info("create setting: {}", globalSetting);
        return globalSetting.getId() != null ? null : (GlobalSettingFields) this.globalSettingRepository.save(globalSetting);
    }

    public GlobalSettingFields update(GlobalSettingFields globalSetting) {
        log.info("update setting: {}", globalSetting);
        return globalSetting.getId() == null ? null : (GlobalSettingFields) this.globalSettingRepository.save(globalSetting);
    }

    public void delete(Long settingId) {
        log.info("delete setting by id: {}", settingId);
        this.globalSettingRepository.deleteById(settingId);
    }
}
