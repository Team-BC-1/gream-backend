package bc1.gream.global.common;

import java.io.Serializable;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RestResponse<T> implements Serializable {

    private HttpStatus status;
    private Integer code;
    private String message;
    private T data;

    public static <T> RestResponse<T> success(T data) {
        return RestResponse.<T>builder()
            .status(ResultCase.SUCCESS.getHttpStatus())
            .code(ResultCase.SUCCESS.getCode())
            .message(ResultCase.SUCCESS.getMessage())
            .data(data)
            .build();
    }

    public static <T> RestResponse<T> error(ResultCase ResultCase, T data) {
        return RestResponse.<T>builder()
            .status(ResultCase.getHttpStatus())
            .code(ResultCase.getCode())
            .message(ResultCase.getMessage())
            .data(data)
            .build();
    }
}
