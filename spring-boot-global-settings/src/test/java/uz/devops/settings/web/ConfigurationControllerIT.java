package uz.devops.settings.web;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;
import uz.devops.settings.IntegrationTest;
import uz.devops.settings.util.BaseUtils;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * @author Nurislom
 * @see uz.devops.settings.web
 * @since 11/28/2023 5:45 PM
 */
@Disabled
@DirtiesContext
@IntegrationTest
@AutoConfigureMockMvc
public class ConfigurationControllerIT {

    private static final String GET_CONFIGURATIONS_URI = "/api/global/setting";
    private final ObjectMapper objectMapper = BaseUtils.OBJECT_MAPPER;

    @Autowired
    private MockMvc mockMvc;

    @Test
    @Transactional
    void getAllP2pCommissionGroups() throws Exception {
        // Get all the Global Setting Configurations
        mockMvc.perform(get(GET_CONFIGURATIONS_URI))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_VALUE));
    }

}
