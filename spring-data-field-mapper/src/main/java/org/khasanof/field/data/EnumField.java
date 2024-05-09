package org.khasanof.field.data;

import lombok.*;

import java.util.List;

/**
 * @author Nurislom
 * @see org.khasanof.field.data
 * @since 4/23/2024 6:24 PM
 */
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@ToString(callSuper = true)
public class EnumField extends ObjectField {

    private List<String> values;

}
