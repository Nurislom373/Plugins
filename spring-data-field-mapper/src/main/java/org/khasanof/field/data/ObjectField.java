package org.khasanof.field.data;

import lombok.*;
import net.minidev.json.JSONObject;

/**
 * @author Nurislom
 * @see org.khasanof.field
 * @since 4/23/2024 6:20 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ObjectField {

    private String fieldName;
    private String fieldType;
    private JSONObject options = new JSONObject();

}
