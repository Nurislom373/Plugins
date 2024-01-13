package uz.devops.observability.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.BeanUtils;
import org.springframework.stereotype.Service;
import uz.devops.observability.enumeration.ObserveType;
import uz.devops.observability.exceptions.AlreadyCreatedException;
import uz.devops.observability.exceptions.NotFoundException;
import uz.devops.observability.factories.resource.MetricsResourceFactory;
import uz.devops.observability.factories.resource.ResourceFactory;
import uz.devops.observability.models.observe.ObserveTypeDTO;
import uz.devops.observability.models.resource.DefaultResource;
import uz.devops.observability.models.resource.Resource;
import uz.devops.observability.models.resource.dto.ObserveCreateDTO;
import uz.devops.observability.models.resource.dto.ObserveUpdateDTO;
import uz.devops.observability.service.registry.RestRegistryManager;

import java.util.EnumSet;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/18/2023 2:32 PM
 */
@Slf4j
@Service
public class DefaultObservabilityService implements ObservabilityService {

    private final MetricsResourceFactory metricsResourceFactory;
    private final RestRegistryManager restRegistryManager;
    private final ResourceFactory resourceFactory;

    public DefaultObservabilityService(RestRegistryManager restRegistryManager, MetricsResourceFactory metricsResourceFactory,
                                       ResourceFactory resourceFactory) {
        this.restRegistryManager = restRegistryManager;
        this.metricsResourceFactory = metricsResourceFactory;
        this.resourceFactory = resourceFactory;
    }

    @Override
    public Set<ObserveTypeDTO> getObserveTypes() {
        return convertEnumToObserveTypeDTOs(EnumSet.allOf(ObserveType.class));
    }

    @Override
    public Resource create(ObserveCreateDTO dto) {
        validateName(dto);
        Resource resource = resourceFactory.create(dto);
        restRegistryManager.add(resource);
        return resource;
    }

    @Override
    public Resource delete(String name) {
        Resource resource = getResource(name);
        restRegistryManager.remove(resource);
        return resource;
    }

    @Override
    public Resource findOne(String name) {
        return restRegistryManager.find(name)
                .map(resource -> metricsResourceFactory.create((DefaultResource) resource))
                .orElseThrow(() -> new NotFoundException("Resource not found by name!"));
    }

    @Override
    public Resource update(ObserveUpdateDTO dto) {
        Resource resource = getResource(dto.getName());
        BeanUtils.copyProperties(dto, resource, "name");
        return resource;
    }

    @Override
    public Resource partialUpdate(ObserveUpdateDTO dto) {
        Resource resource = getResource(dto.getName());
        partialUpdate(dto, (DefaultResource) resource);
        return resource;
    }

    @Override
    public Set<Resource> findAll() {
        return this.restRegistryManager.findAll().stream()
                .map(resource -> metricsResourceFactory.create((DefaultResource) resource)).collect(Collectors.toSet());
    }

    private void partialUpdate(ObserveUpdateDTO dto, DefaultResource defaultResource) {
        if (Objects.nonNull(dto.getUri())) {
            defaultResource.setUri(dto.getUri());
        }
        if (Objects.nonNull(dto.getMethod())) {
            defaultResource.setMethod(dto.getMethod());
        }
        if (Objects.nonNull(dto.getObserveTypes()) && !dto.getObserveTypes().isEmpty()) {
            defaultResource.setObserveTypes(dto.getObserveTypes());
        }
    }

    private Resource getResource(String name) {
        Optional<Resource> resource = restRegistryManager.find(name);
        if (resource.isEmpty()) {
            throw new NotFoundException("Resource not found by name!");
        }
        return resource.get();
    }

    private void validateName(ObserveCreateDTO dto) {
        if (restRegistryManager.contains(dto.getName())) {
            throw new AlreadyCreatedException("Resource name must be unique! this name resource already created!");
        }
    }

    private Set<ObserveTypeDTO> convertEnumToObserveTypeDTOs(Set<ObserveType> types) {
        return types.stream().map(type -> new ObserveTypeDTO(type.name(), type.getDescription()))
                .collect(Collectors.toSet());
    }

}
