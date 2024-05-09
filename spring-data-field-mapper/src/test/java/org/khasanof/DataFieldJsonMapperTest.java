package org.khasanof;

import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.time.MonthDay;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

/**
 * @author Nurislom
 * @see org.khasanof
 * @since 4/24/2024 12:11 PM
 */
@SpringBootTest(classes = { DataFieldsMapperAutoConfiguration.class, TestConfiguration.class })
public class DataFieldJsonMapperTest {

    @Autowired
    private DataFieldsMapper dataFieldsMapper;

    @Test
    void firstTestTransformShouldSuccess() {
        String transform = dataFieldsMapper.transform(Employee.class);
        assertNotNull(transform);
    }

    @Test
    void secondTestTransformShouldSuccess() {
        String transform = dataFieldsMapper.transform(Transform.class);
        assertNotNull(transform);
    }

    @Test
    void thirdTestTransformShouldSuccess() {
        String transform = dataFieldsMapper.transform(Proportion.class);
        assertNotNull(transform);
    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Employee {

        private String name;
        private Integer age;

    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Transform {

        private String text;
        private FieldTypes types;

    }

    @Getter
    @Setter
    @ToString
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Proportion {

        @NotNull
        private boolean check;

        @NotNull
        private int workDays;

        @NotEmpty
        private Set<MonthDay> holidays;
    }
}
