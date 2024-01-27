package bc1.gream.domain.coupon.entity;

import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import com.fasterxml.jackson.annotation.JsonCreator;
import java.util.stream.Stream;

public enum CouponStatus {
    AVAILABLE,
    IN_USE,
    ALREADY_USED;

    @JsonCreator
    public static CouponStatus deserializeRequest(String couponStatusRequest) {
        return Stream.of(CouponStatus.values())
            .filter(couponStatus -> couponStatus.toString().equals(couponStatusRequest.toUpperCase()))
            .findAny()
            .orElseThrow(() -> new GlobalException(ResultCase.INVALID_INPUT));
    }
}
