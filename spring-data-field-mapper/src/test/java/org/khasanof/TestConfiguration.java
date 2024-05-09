package org.khasanof;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 4/24/2024 12:24 PM
 */
@Configuration
public class TestConfiguration {

    /**
     *
     * @return
     */
    @Bean
    public ObjectMapper objectMapper() {
        return new ObjectMapper() {{
            registerModule(new JavaTimeModule());
        }};
    }
}
