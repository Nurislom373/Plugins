package uz.devops.observability.models.response;

import lombok.*;

/**
 * @author Nurislom
 * @see uz.devops.observability
 * @since 12/18/2023 3:08 PM
 */
@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ResponseDTO<T> {

    public static final ResponseDTO FAILED = new ResponseDTO(false, "unknown error", null);
    public static final ResponseDTO SUCCESS = new ResponseDTO(true, "success", null);

    private Boolean success;
    private String message;
    private T data;

    public ResponseDTO<T> success(Boolean success) {
        this.success = success;
        return this;
    }

    public ResponseDTO<T> message(String message) {
        this.message = message;
        return this;
    }

    public ResponseDTO<T> data(T data) {
        this.message = message;
        return this;
    }

}
