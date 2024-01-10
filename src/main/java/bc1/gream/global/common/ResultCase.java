package bc1.gream.global.common;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;

@Getter
@RequiredArgsConstructor
public enum ResultCase {

    // 성공 0번대
    SUCCESS(HttpStatus.OK, 0, "정상 처리 되었습니다"),

    // 유저 1000번대
    // 중복된 로그인ID 입력 409
    DUPLICATED_LOGIN_ID(HttpStatus.CONFLICT, 1000, "중복된 로그인ID를 입력하셨습니다."),
    // 중복된 닉네임 입력 409
    DUPLICATED_NICKNAME(HttpStatus.CONFLICT, 1001, "중복된 닉네임을 입력하셨습니다."),
    // 존재하지 않는 사용자 404,
    USER_NOT_FOUND(HttpStatus.NOT_FOUND, 1002, "유저를 찾을 수 없습니다."),
    // 유효하지 않은 토큰 400
    INVALID_TOKEN(HttpStatus.BAD_REQUEST, 1003, "유효하지 않은 토큰입니다."),

    // 상품 2000번대
    // 검색 결과 없음 404
    SEARCH_RESULT_NOT_FOUND(HttpStatus.NOT_FOUND, 2000, "검색 결과가 없습니다."),
    // 존재하지 않는 상품 404
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, 2001, "해당 상품은 존재하지 않습니다."),

    // 구매 3000번대
    // 구매 요청 대상 상품이 이미 판매되었음 409
    PRODUCT_SOLD_OUT(HttpStatus.CONFLICT, 3000, "구매 요청 대상 상품이 이미 판매되었습니다."),
    // 구매 요청 대상 상품이 존재하지 않음 404
    BUY_PRODUCT_SOLD_OUT(HttpStatus.NOT_FOUND, 3001, "구매 요청 대상 상품이 이미 판매되었습니다."),
    // 입찰 마감 기한 초과 409
    DEADLINE_EXCEEDED(HttpStatus.CONFLICT, 3002, "구매 요청 대상 상품이 이미 판매되었습니다."),

    // 판매 4000번대
    // 판매 요청에 대상 상품이 존재하지 않음 404
    // 입찰 마감 기한 초과 409

    // 글로벌 5000번대
    // 권한 없음 403
    NOT_AUTHORIZED(HttpStatus.FORBIDDEN, 5000, "해당 요청에 대한 권한이 없습니다."),
    // 잘못된 형식의 입력 400
    INVALID_INPUT(HttpStatus.BAD_REQUEST, 5001, "유효하지 않은 입력값"),
    // 시스템 에러 500
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5002, "알 수 없는 에러가 발생했습니다."),
    // 잘못된 도메인 정렬값 입력 400
    INVALID_ORDER_CRITERIA(HttpStatus.BAD_REQUEST, 5001, "유효하지 않은 도메인 정렬값");

    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
}
