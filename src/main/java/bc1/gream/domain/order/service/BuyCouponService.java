package bc1.gream.domain.order.service;

import bc1.gream.domain.order.entity.Buy;
import bc1.gream.domain.user.entity.CouponStatus;
import bc1.gream.domain.user.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyCouponService { // 순환 참조를 예방하기 위한 BuyService와 CouponService의 콤비네이션 서비스

    private final CouponService couponService;
    private final BuyService buyService;


    public void changeCouponStatus(Long buyId, CouponStatus couponStatus) { // 쿠폰의 상태를 바꾸는 서비스

        Buy buy = checkBuy(buyId);
        Long couponId = buy.getCouponId();

        if (couponId == null) {
            return;
        }

        changeCouponStatusByCouponId(couponId, couponStatus);
    }

    public void changeCouponStatusByCouponId(Long couponId, CouponStatus couponStatus) {
        couponService.changeCouponStatus(couponId, couponStatus);
    }

    private Buy checkBuy(Long buyId) {
        return buyService.findBuyById(buyId);
    }
}
