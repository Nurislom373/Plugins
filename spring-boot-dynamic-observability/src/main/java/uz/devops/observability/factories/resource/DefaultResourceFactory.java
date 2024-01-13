package uz.devops.observability.factories.resource;

import org.springframework.stereotype.Component;
import uz.devops.observability.models.resource.DefaultResource;
import uz.devops.observability.models.resource.Resource;
import uz.devops.observability.models.resource.dto.ObserveCreateDTO;

import static uz.devops.observability.utils.BaseUtils.asHttpMethod;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/15/2023 4:58 PM
 */
@Component
public class DefaultResourceFactory implements ResourceFactory {

    @Override
    public Resource create(ObserveCreateDTO createDTO) {
        return DefaultResource.builder()
                .uri(createDTO.getUri())
                .name(createDTO.getName())
                .method(createDTO.getMethod())
                .observeTypes(createDTO.getObserveTypes())
                .build();
    }

}
