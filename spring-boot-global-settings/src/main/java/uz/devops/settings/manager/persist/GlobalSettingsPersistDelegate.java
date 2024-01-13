package uz.devops.settings.manager.persist;

import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import uz.devops.settings.config.GlobalSettingsProperty;
import uz.devops.settings.factory.models.GlobalSettingsImplementInfo;
import uz.devops.settings.manager.GlobalSettingsExecution;

import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Nurislom
 * @see uz.devops.settings.manager.persist
 * @since 12/1/2023 7:05 PM
 */
@Slf4j
@Service
public class GlobalSettingsPersistDelegate {

    private static final Map<GlobalSettingsProperty.PersistAuto, Class<? extends GlobalSettingsPersist>> persistMap = new HashMap<>();
    private final GlobalSettingsProperty globalSettingsProperty;

    static {
        persistMap.put(GlobalSettingsProperty.PersistAuto.NONE, GlobalSettingsNonePersist.class);
        persistMap.put(GlobalSettingsProperty.PersistAuto.UPDATE, GlobalSettingsUpdatePersist.class);
        persistMap.put(GlobalSettingsProperty.PersistAuto.CREATE, GlobalSettingsCreatePersist.class);
        persistMap.put(GlobalSettingsProperty.PersistAuto.DROP_CREATE, GlobalSettingsDropCreatePersist.class);
    }

    public GlobalSettingsPersistDelegate(GlobalSettingsProperty globalSettingsProperty) {
        this.globalSettingsProperty = globalSettingsProperty;
    }

    public void execute(List<GlobalSettingsImplementInfo> infos, PersistBeans beans) {
        try {
            var constructor = persistMap.get(globalSettingsProperty.getPersistAuto()).getConstructor(PersistBeans.class);
            GlobalSettingsExecution settingsExecution = GlobalSettingsExecution.factory(beans);
            beans.globalSettingsExecution(settingsExecution);
            GlobalSettingsPersist persist = constructor.newInstance(beans);
            persist.persist(infos);
        } catch (NoSuchMethodException | InvocationTargetException | InstantiationException | IllegalAccessException e) {
            e.printStackTrace();
        }
    }

}
