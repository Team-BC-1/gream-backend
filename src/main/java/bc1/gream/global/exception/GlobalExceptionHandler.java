package bc1.gream.global.exception;

import bc1.gream.global.common.ErrorResponseDto;
import bc1.gream.global.common.InvalidInputMapper;
import bc1.gream.global.common.InvalidInputResponseDto;
import bc1.gream.global.common.RestResponse;
import bc1.gream.global.common.ResultCase;
import java.util.List;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindException;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class GlobalExceptionHandler {

    /**
     * RequestBody 파라미터 검증 오류 발생에 대한 핸들러
     *
     * @param ex RequestBody 파라미터 검증오류에 따른 MethodArgumentNotValidException
     * @return 잘못입력된 값 리스트
     */
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<RestResponse<List<InvalidInputResponseDto>>> handlerValidationException(MethodArgumentNotValidException ex) {
        List<InvalidInputResponseDto> invalidInputList = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(InvalidInputMapper.INSTANCE::toInvalidInputResponseDto)
            .toList();

        return RestResponse.error(ResultCase.INVALID_INPUT, invalidInputList);
    }

    /**
     * ModelAttribute 파라미터 검증 오류 발생에 대한 핸들러
     *
     * @param ex ModelAttribute 파라미터 검증오류에 따른 BindException
     * @return 잘못입력된 값 리스트
     */
    @ExceptionHandler(BindException.class)
    public ResponseEntity<RestResponse<List<InvalidInputResponseDto>>> handlerValidationException(BindException ex) {
        List<InvalidInputResponseDto> invalidInputList = ex.getBindingResult()
            .getFieldErrors()
            .stream()
            .map(InvalidInputMapper.INSTANCE::toInvalidInputResponseDto)
            .toList();

        return RestResponse.error(ResultCase.INVALID_INPUT, invalidInputList);
    }

    /**
     * Thread 오류 대한 핸들러
     *
     * @param ex Thread 오류에 따른 InterruptedException
     * @return Thread 에러케이스와 에러리스폰스
     */
    @ExceptionHandler(InterruptedException.class)
    public ResponseEntity<RestResponse<ErrorResponseDto>> handlerInterruptedException(InterruptedException ex) {
        return RestResponse.error(ResultCase.THREAD_ERROR, new ErrorResponseDto());
    }

    /**
     * Business 오류 발생에 대한 핸들러
     *
     * @param e Business 오류에 따른 GlobalException
     * @return 에러케이스와 에러리스폰스
     */
    @ExceptionHandler(GlobalException.class)
    public ResponseEntity<RestResponse<ErrorResponseDto>> handleGlobalException(GlobalException e) {
        return RestResponse.error(e.getResultCase(), new ErrorResponseDto());
    }
}
