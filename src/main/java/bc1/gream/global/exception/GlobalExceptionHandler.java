package bc1.gream.global.exception;

import bc1.gream.global.common.ErrorResponseDto;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.common.ResultCase;
import java.util.List;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResponse<List<ObjectError>> handlerValidationException(MethodArgumentNotValidException ex) {
        BindingResult bindingResult = ex.getBindingResult();
        return RestResponse.error(ResultCase.INVALID_INPUT, bindingResult.getAllErrors());
    }

    @ExceptionHandler(GlobalException.class)
    public RestResponse<ErrorResponseDto> handleGlobalException(GlobalException e) {
        return RestResponse.error(e.getResultCase(), new ErrorResponseDto());
    }
}
