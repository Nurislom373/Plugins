package uz.devops.settings.config;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Getter
@Setter
@ConfigurationProperties(prefix = "global-settings")
public class GlobalSettingsProperty {

    private PersistAuto persistAuto = PersistAuto.DROP_CREATE;
    private Boolean ignoreNullFields = Boolean.TRUE;

    public GlobalSettingsProperty() {
    }

    @Getter
    @RequiredArgsConstructor
    public enum PersistAuto {
         DROP_CREATE, CREATE, UPDATE, NONE
    }

}
