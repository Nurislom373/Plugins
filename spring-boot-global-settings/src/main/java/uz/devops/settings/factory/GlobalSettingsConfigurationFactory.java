package uz.devops.settings.factory;

import lombok.Getter;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.BeanFactory;
import org.springframework.beans.factory.BeanFactoryAware;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.Ordered;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;
import uz.devops.settings.annotation.GlobalSettingDefaultValue;
import uz.devops.settings.annotation.GlobalSettingTitleContainer;
import uz.devops.settings.annotation.GlobalSettings;
import uz.devops.settings.factory.models.GlobalSettingsImplementFieldInfo;
import uz.devops.settings.factory.models.GlobalSettingsImplementInfo;
import uz.devops.settings.factory.models.GlobalSettingsImplementTitleInfo;
import uz.devops.settings.manager.data.SimpleGlobalSettingsDataManager;
import uz.devops.settings.service.packages.GlobalSettingsPackagesService;
import uz.devops.settings.value.GlobalSettingValue;
import uz.devops.settings.value.GlobalSettingValueFactory;

import javax.annotation.PostConstruct;
import java.lang.reflect.Field;
import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;

@Service(GlobalSettingsConfigurationFactory.NAME)
public class GlobalSettingsConfigurationFactory implements Ordered, BeanFactoryAware {

    public static final String NAME = "globalSettingsConfigurationFactory";

    private final ApplicationContext applicationContext;
    private final SimpleGlobalSettingsDataManager globalSettingsDataManager;
    private final GlobalSettingFactory globalSettingImplementFactory;

    @Getter
    private final List<GlobalSettingsImplementInfo> infos = new CopyOnWriteArrayList<>();

    private final Logger log = LoggerFactory.getLogger(this.getClass());

    public GlobalSettingsConfigurationFactory(ApplicationContext applicationContext, GlobalSettingFactory globalSettingImplementFactory,
                                              SimpleGlobalSettingsDataManager globalSettingsDataManager) {
        this.applicationContext = applicationContext;
        this.globalSettingImplementFactory = globalSettingImplementFactory;
        this.globalSettingsDataManager = globalSettingsDataManager;
    }

    public GlobalSettingsImplementInfo getInfo(Class<?> settingClass) {
        return infos.stream().filter((f) -> Objects.equals(f.getImplementClass().getName(),
                        settingClass.getName()))
                .findFirst().orElse(null);
    }

    public Optional<GlobalSettingsImplementFieldInfo> getFieldInfo(String fieldName) {
        return infos.stream()
                .flatMap(info -> info.getFields().stream())
                .filter(fieldInfo -> Objects.equals(fieldInfo.getGlobalSettingValue().getName(), fieldName))
                .findFirst();
    }

    @Override
    public void setBeanFactory(BeanFactory beanFactory) throws BeansException {
        initialize();
    }

    public void initialize() {
        try {
            tryInitialize();
            globalSettingsDataManager.execute(infos);
        } catch (Exception var1) {
            log.debug("Error to try invoke method initialize, ERROR" + var1.getMessage());
            var1.printStackTrace();
        }

    }

    @SneakyThrows
    private void tryInitialize() {
        log.debug("Start try initialize");
        try {
            ApplicationContext context = applicationContext;
            if (context == null) {
                throw new Exception("Cannot get packages, maybe beans not initialized!");
            } else {
                GlobalSettingsPackagesService service = context.getBean(GlobalSettingsPackagesService.class);
                if (service == null) {
                    throw new Exception("Cannot get packages, maybe beans not initialized!");
                } else {
                    String[] packages = service.getPackages();
                    if (packages == null) {
                        throw new Exception("Cannot get packages, maybe beans not initialized or packages annotation value is null!");
                    } else {
                        ClassPathScanningCandidateComponentProvider scanner = new ClassPathScanningCandidateComponentProvider(false);
                        scanner.addIncludeFilter(new AnnotationTypeFilter(GlobalSettings.class));

                        for (String packageInfo : packages) {
                            Set<BeanDefinition> classes = scanner.findCandidateComponents(packageInfo);
                            ArrayList<BeanDefinition> classesList = new ArrayList<>(classes);
                            classesList.forEach(this::initializeWithBean);
                        }

                    }
                }
            }
        } catch (Throwable var10) {
            throw var10;
        }
    }

    @SneakyThrows
    private void initializeWithBean(BeanDefinition beanDefinition) {
        try {
            initializeInfo(Class.forName(beanDefinition.getBeanClassName()));
        } catch (Throwable var2) {
            log.debug("Error to try invoke method initializeWithBean, ERROR" + var2.getMessage());
            throw var2;
        }
    }

    private void initializeInfo(Class<?> settingsClass) {
        try {
            tryInitializeInfo(settingsClass);
        } catch (Exception var2) {
            log.debug("Error to try invoke method InitializeInfo, ERROR" + var2.getMessage());
            var2.printStackTrace();
        }
    }

    private void tryInitializeInfo(Class<?> settingsClass) {
        log.debug("Start try initialize info");
        try {
            if (!settingsClass.isAnnotationPresent(GlobalSettings.class)) {
                log.debug("GlobalSettings annotation not found in parameter settingsClass");
            } else {
                GlobalSettingsImplementInfo implementInfo = createGlobalImplementInfoWithType(settingsClass);
                GlobalSettingTitleContainer annotation = (GlobalSettingTitleContainer) settingsClass.getDeclaredAnnotation(GlobalSettingTitleContainer.class);
                if (annotation != null) {
                    GlobalSettingsImplementTitleInfo[] titles = (GlobalSettingsImplementTitleInfo[]) Arrays.stream(annotation.value()).map(GlobalSettingsImplementTitleInfo::new).toArray(GlobalSettingsImplementTitleInfo[]::new);
                    implementInfo.setTitles(Arrays.asList(titles));
                }
                tryCreateGlobalImplementInfoFields(settingsClass.getDeclaredFields(), implementInfo);
            }
        } catch (Throwable var5) {
            throw var5;
        }
    }

    private void tryInitializeInfoV2(Class<?> settingClass, GlobalSettingsImplementInfo parentInfo) {
        try {
            GlobalSettingsImplementInfo implementInfo = createGlobalImplementInfoWithType(settingClass, parentInfo);
            tryCreateGlobalImplementInfoFields(settingClass.getDeclaredFields(), implementInfo);
        } catch (Throwable ex) {
            log.debug("Error to try invoke method tryInitializeInfo, ERROR" + ex.getMessage());
            ex.printStackTrace();
        }
    }

    private void initializeFieldInfo(Field field) {
        try {
            GlobalSettingsImplementInfo implementInfo = createGlobalImplementInfoWithType(field.getType());
            tryCreateGlobalImplementInfoFields(field.getType().getDeclaredFields(), implementInfo);
        } catch (Exception var3) {
            log.debug("Error to try invoke method tryInitializeFieldInfo, ERROR" + var3.getMessage());
            var3.printStackTrace();
        }
    }

    private void tryCreateGlobalImplementInfoFields(Field[] fields, GlobalSettingsImplementInfo implementInfo) {
        Arrays.stream(fields).forEach(field -> tryInitializeFieldInfo(field, implementInfo));
    }

    @SneakyThrows
    private void tryInitializeFieldInfo(Field field, GlobalSettingsImplementInfo info) {
        log.debug("Start try initialize field info");
        try {
            if (field.getType().isAnnotationPresent(GlobalSettings.class)) {
                return;
            }
            GlobalSettingsImplementFieldInfo fieldInfo = new GlobalSettingsImplementFieldInfo(info);
            GlobalSettingValue<?> globalSettingValue = GlobalSettingValueFactory.getSettingValue(field.getType(), true);
            if (globalSettingValue == null) {
                throw new Exception("Setting value not implemented for: " + field);
            } else {
                globalSettingValue.setName(field.getDeclaringClass().getSimpleName() + "." + field.getName());
                globalSettingValue.setFieldInfo(fieldInfo);
                globalSettingValue.setIdentifierName(field.getName());
                GlobalSettingDefaultValue globalSettingDefaultValue = (GlobalSettingDefaultValue) field.getAnnotation(GlobalSettingDefaultValue.class);
                if (globalSettingDefaultValue != null) {
                    globalSettingValue.setDefaultValue(globalSettingDefaultValue.defaultValue());
                }

                GlobalSettingTitleContainer annotation = (GlobalSettingTitleContainer) field.getDeclaredAnnotation(GlobalSettingTitleContainer.class);
                if (annotation != null) {
                    GlobalSettingsImplementTitleInfo[] titles = (GlobalSettingsImplementTitleInfo[]) Arrays.stream(annotation.value()).map(GlobalSettingsImplementTitleInfo::new).toArray(GlobalSettingsImplementTitleInfo[]::new);
                    fieldInfo.setTitles(Arrays.asList(titles));
                }

                globalSettingValue.setSettingField(field);
                fieldInfo.setGlobalSettingValue(globalSettingValue);
                info.addField(fieldInfo);
            }
        } catch (Throwable var7) {
            log.debug("Error to try invoke method tryInitializeFieldInfo, ERROR" + var7.getMessage());
            throw var7;
        }
    }

    private GlobalSettingsImplementInfo createGlobalImplementInfoWithType(Class<?> type) {
        infos.removeIf((f) -> Objects.equals(f.getImplementClass().getName(), type.getName()));
        return globalSettingImplementFactory.createImplementInfo(type, infos);
    }

    private GlobalSettingsImplementInfo createGlobalImplementInfoWithType(Class<?> type, GlobalSettingsImplementInfo parentInfo) {
        infos.removeIf((f) -> Objects.equals(f.getImplementClass().getName(), type.getName()));
        return globalSettingImplementFactory.createImplementInfo(type, infos)
                .parentImplementInfo(parentInfo);
    }

    @Override
    public int getOrder() {
        return Ordered.HIGHEST_PRECEDENCE;
    }
}
