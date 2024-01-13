package uz.devops.settings.config;

import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.context.properties.ConfigurationPropertiesScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import uz.devops.settings.GlobalSettingsAutoConfiguration;

@Configuration
@EntityScan(basePackages = {GlobalSettingsAutoConfiguration.PACKAGE})
@EnableJpaRepositories(basePackages = {GlobalSettingsAutoConfiguration.PACKAGE})
@ConfigurationPropertiesScan(basePackages = {GlobalSettingsAutoConfiguration.PACKAGE})
public class GlobalSettingConfiguration {
}
