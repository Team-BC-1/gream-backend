package bc1.gream.global.exception;

import bc1.gream.global.common.ErrorResponseDto;
import bc1.gream.global.common.InvalidInputMapper;
import bc1.gream.global.common.InvalidInputResponseDto;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.common.ResultCase;
import java.util.List;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public RestResponse<List<InvalidInputResponseDto>> handlerValidationException(MethodArgumentNotValidException ex) {
        List<InvalidInputResponseDto> invalidInputList = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(InvalidInputMapper.INSTANCE::toInvalidInputResponseDto)
            .toList();

        return RestResponse.error(ResultCase.INVALID_INPUT, invalidInputList);
    }

    @ExceptionHandler(GlobalException.class)
    public RestResponse<ErrorResponseDto> handleGlobalException(GlobalException e) {
        return RestResponse.error(e.getResultCase(), new ErrorResponseDto());
    }
}
