package bc1.gream.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResultCase {

    // 성공 0번대
    SUCCESS(HttpStatus.OK, 0, "정상 처리 되었습니다");

    // 유저 1000번대

    // 상품 2000번대

    // 구매 3000번대

    // 판매 4000번대

    // 글로벌 5000번대

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
}
