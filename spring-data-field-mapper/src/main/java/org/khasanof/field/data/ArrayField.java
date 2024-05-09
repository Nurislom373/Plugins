package org.khasanof.field.data;

import lombok.*;

/**
 * @author Nurislom
 * @see org.khasanof.field.data
 * @since 4/23/2024 6:25 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class ArrayField extends ObjectField {

    private boolean isArray = true;

}
