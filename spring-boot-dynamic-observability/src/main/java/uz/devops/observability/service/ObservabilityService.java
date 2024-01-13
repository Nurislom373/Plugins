package uz.devops.observability.service;

import uz.devops.observability.models.observe.ObserveTypeDTO;
import uz.devops.observability.models.resource.Resource;
import uz.devops.observability.models.resource.dto.ObserveCreateDTO;
import uz.devops.observability.models.resource.dto.ObserveUpdateDTO;

import java.util.Set;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/18/2023 2:32 PM
 */
public interface ObservabilityService {

    Set<ObserveTypeDTO> getObserveTypes();

    Resource create(ObserveCreateDTO dto);

    Resource delete(String name);

    Resource findOne(String name);

    Resource update(ObserveUpdateDTO dto);

    Resource partialUpdate(ObserveUpdateDTO dto);

    Set<Resource> findAll();

}
