package uz.devops.observability.web.rest;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import uz.devops.observability.exceptions.BadRequestException;
import uz.devops.observability.models.observe.ObserveTypeDTO;
import uz.devops.observability.models.resource.Resource;
import uz.devops.observability.models.resource.dto.ObserveCreateDTO;
import uz.devops.observability.models.resource.dto.ObserveUpdateDTO;
import uz.devops.observability.service.ObservabilityService;

import javax.validation.Valid;
import java.util.Objects;
import java.util.Set;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/18/2023 2:27 PM
 */
@RestController
@RequestMapping(value = "/api")
public class ObservabilityResource {

    private final ObservabilityService observabilityService;

    public ObservabilityResource(ObservabilityService observabilityService) {
        this.observabilityService = observabilityService;
    }

    @GetMapping(value = "/observability/types")
    public ResponseEntity<Set<ObserveTypeDTO>> getObserveTypes() {
        return new ResponseEntity<>(observabilityService.getObserveTypes(), HttpStatus.OK);
    }

    @GetMapping(value = "/observability")
    public ResponseEntity<Set<Resource>> findAllObserve() {
        return new ResponseEntity<>(observabilityService.findAll(), HttpStatus.OK);
    }

    @PostMapping(value = "/observability")
    public ResponseEntity<Resource> deleteObserve(@Valid @RequestBody ObserveCreateDTO dto) {
        return new ResponseEntity<>(observabilityService.create(dto), HttpStatus.OK);
    }

    @DeleteMapping(value = "/observability/{name}")
    public ResponseEntity<Resource> deleteObserve(@PathVariable String name) {
        if (Objects.isNull(name)) {
            throw new BadRequestException("name must not be null!");
        }
        return new ResponseEntity<>(observabilityService.delete(name), HttpStatus.OK);
    }

    @GetMapping(value = "/observability/{name}")
    public ResponseEntity<Resource> findOneObserve(@PathVariable String name) {
        if (Objects.isNull(name)) {
            throw new BadRequestException("name must not be null!");
        }
        return new ResponseEntity<>(observabilityService.findOne(name), HttpStatus.OK);
    }

    @PutMapping(value = "/observability")
    public ResponseEntity<Resource> updateObserve(@Valid @RequestBody ObserveUpdateDTO dto) {
        return new ResponseEntity<>(observabilityService.update(dto), HttpStatus.OK);
    }

    @PatchMapping(value = "/observability")
    public ResponseEntity<Resource> partialUpdateObserve(@RequestBody ObserveUpdateDTO dto) {
        if (Objects.isNull(dto)) {
            throw new BadRequestException("request must not be null!");
        }
        if (Objects.isNull(dto.getName())) {
            throw new BadRequestException("name must not be null!");
        }
        return new ResponseEntity<>(observabilityService.partialUpdate(dto), HttpStatus.OK);
    }

}
