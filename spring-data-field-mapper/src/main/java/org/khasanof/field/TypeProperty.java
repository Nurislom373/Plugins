package org.khasanof.field;

import lombok.*;

import java.lang.reflect.Field;

/**
 * @author Nurislom
 * @see org.khasanof.factories.property
 * @since 4/24/2024 3:58 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class TypeProperty implements Property {

    private String fieldName;
    private Class<?> fieldType;
    private Field field;

}
