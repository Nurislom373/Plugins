package uz.devops.settings.service.packages;

import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.stereotype.Service;
import uz.devops.settings.annotation.GlobalSettingsPackage;

import java.util.Arrays;
import java.util.List;
import java.util.Map;

@Service
public class GlobalSettingsPackagesServiceImpl implements GlobalSettingsPackagesService {

    private final ApplicationContext context;

    public GlobalSettingsPackagesServiceImpl(ApplicationContext applicationContext) {
        this.context = applicationContext;
    }

    public String[] getPackages() {
        if (context == null) {
            return null;
        } else {
            Map<String, Object> applicationBeans = context.getBeansWithAnnotation(SpringBootApplication.class);
            List<Object> applicationBeansList = Arrays.asList(applicationBeans.values().toArray());
            if (applicationBeansList.isEmpty()) {
                return null;
            } else {
                Object applicationBean = applicationBeansList.get(0);
                if (applicationBean == null) {
                    return null;
                } else {
                    Class<?> appClass = applicationBean.getClass();
                    Package appPackage = appClass.getPackage();
                    GlobalSettingsPackage globalSettingsPackage = (GlobalSettingsPackage) appClass.getAnnotation(GlobalSettingsPackage.class);
                    if (globalSettingsPackage == null) {
                        return new String[]{appPackage.getName()};
                    } else {
                        String[] packages = globalSettingsPackage.packages();
                        return packages.length == 0 ? new String[]{appPackage.getName()} : packages;
                    }
                }
            }
        }
    }
}
