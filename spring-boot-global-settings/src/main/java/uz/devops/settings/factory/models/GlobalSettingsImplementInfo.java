package uz.devops.settings.factory.models;

import lombok.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class GlobalSettingsImplementInfo {

    private String name;
    private Class<?> implementClass;
    private Class<?> parentImplementClass;
    private GlobalSettingsImplementInfo parentImplementInfo;
    private List<GlobalSettingsImplementTitleInfo> titles = new ArrayList<>();
    private List<GlobalSettingsImplementFieldInfo> fields = new ArrayList<>();

    public void addField(GlobalSettingsImplementFieldInfo fieldInfo) {
        this.fields.add(fieldInfo);
    }

    public GlobalSettingsImplementInfo parentImplementClass(Class<?> parentClass) {
        this.parentImplementClass = parentClass;
        return this;
    }

    public GlobalSettingsImplementInfo parentImplementInfo(GlobalSettingsImplementInfo parentImplementInfo) {
        this.parentImplementInfo = parentImplementInfo;
        this.parentImplementClass = parentImplementInfo.getImplementClass();
        return this;
    }

    public boolean hasParentClass() {
        return Objects.nonNull(parentImplementClass);
    }

    public boolean hasParent() {
        return Objects.nonNull(parentImplementInfo);
    }

}
