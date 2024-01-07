package bc1.gream.global.exception;

import bc1.gream.global.common.ResultCase;
import lombok.Getter;

@Getter
public class GlobalException extends RuntimeException {

    private final ResultCase resultCase;

    public GlobalException(ResultCase resultCase) {
        super(resultCase.getMessage());
        this.resultCase = resultCase;
    }
}
