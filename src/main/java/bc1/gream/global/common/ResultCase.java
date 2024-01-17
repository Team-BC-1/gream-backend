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
    // 유효하지 않은 토큰 401
    INVALID_TOKEN(HttpStatus.UNAUTHORIZED, 1003, "유효하지 않은 토큰입니다."),
    // 로그인 필요 403
    LOGIN_REQUIRED(HttpStatus.UNAUTHORIZED, 1004, "로그인이 필요합니다."),
    // 만료된 액세스 토큰 401
    EXPIRED_ACCESS_TOKEN(HttpStatus.UNAUTHORIZED, 1005, "만료된 Access Token"),
    // 만료된 리프레쉬 토큰 401
    EXPIRED_REFRESH_TOKEN(HttpStatus.UNAUTHORIZED, 1006, "만료된 Refresh Token"),

    // 상품 2000번대
    // 검색 결과 없음 404
    SEARCH_RESULT_NOT_FOUND(HttpStatus.NOT_FOUND, 2000, "검색 결과가 없습니다."),
    // 존재하지 않는 상품 404
    PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, 2001, "해당 상품은 존재하지 않습니다."),

    // 구매 3000번대
    // 구매 요청 대상 상품이 이미 판매되었음 409
    BUY_PRODUCT_SOLD_OUT(HttpStatus.CONFLICT, 3000, "구매 요청 대상 상품이 이미 판매되었습니다."),
    // 구매 요청 대상 상품이 존재하지 않음 404
    BUY_BID_NOT_FOUND(HttpStatus.NOT_FOUND, 3001, "해당 구매 입찰 건은 존재하지 않습니다."),
    // 입찰 마감 기한 초과 409
    DEADLINE_EXCEEDED(HttpStatus.CONFLICT, 3002, "요청 상품의 등록 기한이 마감되었습니다."),

    // 판매 4000번대
    // 판매 요청 대상 상품이 이미 구매되었음 409
    SELL_PRODUCT_SOLD_OUT(HttpStatus.CONFLICT, 4000, "판매 요청 대상 상품이 이미 구매되었습니다."),
    // 판매 요청에 대상 상품이 존재하지 않음 404
    SELL_BID_PRODUCT_NOT_FOUND(HttpStatus.NOT_FOUND, 4001, "해당 판매 입찰 건은 존재하지 않습니다."),
    GIFTICON_NOT_FOUND(HttpStatus.NOT_FOUND, 4002, "해당 id의 기프티콘은 존재하지 않습니다."),
    // 입찰 마감 기한 초과 409

    // 글로벌 5000번대
    // 권한 없음 403
    NOT_AUTHORIZED(HttpStatus.FORBIDDEN, 5000, "해당 요청에 대한 권한이 없습니다."),
    // 잘못된 형식의 입력 400
    INVALID_INPUT(HttpStatus.BAD_REQUEST, 5001, "유효하지 않은 입력값"),
    // 시스템 에러 500
    SYSTEM_ERROR(HttpStatus.INTERNAL_SERVER_ERROR, 5002, "알 수 없는 에러가 발생했습니다."),
    // 잘못된 도메인 정렬값 입력 400
    INVALID_ORDER_CRITERIA(HttpStatus.BAD_REQUEST, 5001, "유효하지 않은 도메인 정렬값"),

    // 쿠폰 6000번대
    // 쿠폰이 존재하지 않을 때 404
    COUPON_NOT_FOUND(HttpStatus.NOT_FOUND, 6000, "해당 쿠폰은 존재하지 않습니다."),
    COUPON_STATUS_CHANGE_FAIL(HttpStatus.CONFLICT, 6001, "쿠폰의 상태 변경에 실패했습니다."),
    COUPON_ALREADY_IN_USED(HttpStatus.NOT_FOUND, 6002, "해당 쿠폰은 이미 사용중입니다."),
    COUPON_TYPE_NOT_FOUND(HttpStatus.NOT_FOUND, 6003, "해당 쿠폰 종류는 존재하지 않는 종류입니다."),
    COUPON_TYPE_INVALID_RATE(HttpStatus.CONFLICT, 6004, "올바른 형식의 고정할인가를 입력해주세요."),
    COUPON_TYPE_INVALID_FIXED(HttpStatus.CONFLICT, 6005, "올바른 형식의 할인율을 입력해주세요.");
    private final HttpStatus httpStatus;
    private final Integer code;
    private final String message;
}
