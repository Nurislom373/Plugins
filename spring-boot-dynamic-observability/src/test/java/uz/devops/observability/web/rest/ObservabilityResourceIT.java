package uz.devops.observability.web.rest;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.apache.commons.lang3.RandomStringUtils;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultHandlers;
import org.springframework.web.bind.annotation.RequestMethod;
import uz.devops.observability.ObservabilityTest;
import uz.devops.observability.enumeration.ObserveType;
import uz.devops.observability.factories.resource.ResourceFactory;
import uz.devops.observability.models.resource.Resource;
import uz.devops.observability.models.resource.dto.ObserveCreateDTO;
import uz.devops.observability.models.resource.dto.ObserveUpdateDTO;
import uz.devops.observability.service.registry.RestRegistryManager;

import java.util.Collections;
import java.util.Set;
import java.util.stream.IntStream;

import static org.hamcrest.Matchers.hasSize;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * @author Nurislom
 * @see uz.devops.observability.web.rest
 * @since 12/19/2023 2:11 PM
 */
@ObservabilityTest
@AutoConfigureMockMvc
public class ObservabilityResourceIT {

    private static final String NAME = "AAAAAAAAAAAA";
    private final ObjectMapper objectMapper = new ObjectMapper() {{
        registerModule(new JavaTimeModule());
    }};

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private RestRegistryManager restRegistryManager;

    @Autowired
    private ResourceFactory resourceFactory;

    @BeforeEach
    void beforeEach() {
        // clear all created resources
        restRegistryManager.clear();
    }

    @Test
    void testGetObserveTypes() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/observability/types"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());
    }

    @Test
    void testFindAllObserve() throws Exception {
        int count = 10;
        createMockResourcesAndRegister(count);

        mockMvc.perform(get("/api/observability").contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isArray())
                .andExpect(jsonPath("$.*", hasSize(10)));
    }

    @Test
    void testCreateObserve() throws Exception {
        ObserveCreateDTO createDTO = new ObserveCreateDTO();
        createDTO.setUri("AAAAAAAA");
        createDTO.setName("BBBBBBBBB");
        createDTO.setMethod(RequestMethod.GET);
        createDTO.setObserveTypes(Set.of(ObserveType.TIMER));

        mockMvc.perform(post("/api/observability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());

        Set<Resource> afterResourceSize = restRegistryManager.findAll();

        assertEquals(afterResourceSize.size(), 1);
    }

    @Test
    void testCreateObserveShouldBadRequestFirst() throws Exception {
        ObserveCreateDTO createDTO = new ObserveCreateDTO();
        createDTO.setUri("AAAAAAAA");
        createDTO.setMethod(RequestMethod.GET);
        createDTO.setObserveTypes(Set.of(ObserveType.TIMER));

        int beforeResourcesSize = restRegistryManager.findAll().size();

        mockMvc.perform(post("/api/observability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        Set<Resource> afterResource = restRegistryManager.findAll();

        assertEquals(afterResource.size(), beforeResourcesSize);
    }

    @Test
    void testCreateObserveShouldBadRequestSecond() throws Exception {
        ObserveCreateDTO createDTO = new ObserveCreateDTO();
        createDTO.setUri("AAAAAAAA");
        createDTO.setName("BBBBBBBBBB");
        createDTO.setObserveTypes(Set.of(ObserveType.TIMER));

        int beforeResourcesSize = restRegistryManager.findAll().size();

        mockMvc.perform(post("/api/observability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        Set<Resource> afterResource = restRegistryManager.findAll();

        assertEquals(afterResource.size(), beforeResourcesSize);
    }

    @Test
    void testCreateObserveShouldBadRequestThird() throws Exception {
        ObserveCreateDTO createDTO = new ObserveCreateDTO();
        createDTO.setUri("AAAAAAAA");
        createDTO.setName("BBBBBBBBBB");
        createDTO.setMethod(RequestMethod.GET);

        int beforeResourcesSize = restRegistryManager.findAll().size();

        mockMvc.perform(post("/api/observability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        Set<Resource> afterResource = restRegistryManager.findAll();

        assertEquals(afterResource.size(), beforeResourcesSize);
    }

    @Test
    void testCreateObserveShouldBadRequestFourth() throws Exception {
        ObserveCreateDTO createDTO = new ObserveCreateDTO();
        createDTO.setUri("AAAAAAAA");
        createDTO.setName("BBBBBBBBBB");
        createDTO.setMethod(RequestMethod.GET);
        createDTO.setObserveTypes(Collections.emptySet());

        int beforeResourcesSize = restRegistryManager.findAll().size();

        mockMvc.perform(post("/api/observability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDTO)))
                .andExpect(status().isBadRequest())
                .andDo(MockMvcResultHandlers.print());

        Set<Resource> afterResource = restRegistryManager.findAll();

        assertEquals(afterResource.size(), beforeResourcesSize);
    }

    @Test
    void testDeleteObserve() throws Exception {
        Resource resource = createResource();
        restRegistryManager.add(resource);

        int beforeResourcesSize = restRegistryManager.findAll().size();

        mockMvc.perform(delete("/api/observability/{name}", resource.getName())
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());

        Set<Resource> afterResourceSize = restRegistryManager.findAll();

        assertEquals(afterResourceSize.size(), beforeResourcesSize - 1);
    }

    @Test
    void testDeleteObserveShouldNotFound() throws Exception {
        Resource resource = createResource();
        restRegistryManager.add(resource);

        int beforeResourcesSize = restRegistryManager.findAll().size();

        mockMvc.perform(delete("/api/observability/{name}", NAME)
                        .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isNotFound())
                .andExpect(jsonPath("$").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());

        Set<Resource> afterResourceSize = restRegistryManager.findAll();

        assertEquals(afterResourceSize.size(), beforeResourcesSize);
    }

    @Test
    void testUpdateObserve() throws Exception {
        Resource resource = createResource();
        restRegistryManager.add(resource);
        ObserveUpdateDTO updateDTO = new ObserveUpdateDTO();
        updateDTO.setMethod(RequestMethod.DELETE);
        updateDTO.setObserveTypes(Set.of(ObserveType.TIMER));
        updateDTO.setName(resource.getName());
        updateDTO.setUri("AAAAAAAAAAAAA");

        int beforeResourcesSize = restRegistryManager.findAll().size();

        mockMvc.perform(put("/api/observability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        Set<Resource> afterResources = restRegistryManager.findAll();

        // assertions
        Resource next = getNext(afterResources);
        assertEquals(afterResources.size(), beforeResourcesSize);
        assertEquals(next.getMethod(), updateDTO.getMethod());
        assertEquals(next.getName(), updateDTO.getName());
        assertEquals(next.getUri(), updateDTO.getUri());
    }

    @Test
    void testPartialUpdateObserve() throws Exception {
        Resource resource = createResource();
        restRegistryManager.add(resource);
        ObserveUpdateDTO updateDTO = new ObserveUpdateDTO();
        updateDTO.setName(resource.getName());
        updateDTO.setMethod(RequestMethod.DELETE);

        int beforeResourcesSize = restRegistryManager.findAll().size();

        mockMvc.perform(patch("/api/observability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$").isNotEmpty());

        Set<Resource> afterResources = restRegistryManager.findAll();

        // assertions
        Resource next = getNext(afterResources);
        assertEquals(afterResources.size(), beforeResourcesSize);
        assertEquals(next.getMethod(), updateDTO.getMethod());
    }

    @Test
    void testPartialUpdateObserveShouldBadRequest() throws Exception {
        Resource resource = createResource();
        restRegistryManager.add(resource);
        ObserveUpdateDTO updateDTO = new ObserveUpdateDTO();
        updateDTO.setName(null);
        updateDTO.setMethod(RequestMethod.DELETE);

        mockMvc.perform(patch("/api/observability")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(updateDTO)))
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$").isNotEmpty())
                .andDo(MockMvcResultHandlers.print());
    }

    private static Resource getNext(Set<Resource> afterResources) {
        return afterResources.iterator().next();
    }

    private void createMockResourcesAndRegister(int count) {
        IntStream.range(0, count)
                .forEach(i -> restRegistryManager.add(createResource()));
    }

    private Resource createResource() {
        return resourceFactory.create(new ObserveCreateDTO(RandomStringUtils.random(10, true, true),
                RandomStringUtils.random(10, true, true), RequestMethod.GET, Collections.emptySet()));
    }

}
