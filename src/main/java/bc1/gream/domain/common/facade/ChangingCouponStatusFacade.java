package bc1.gream.domain.common.facade;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.service.BuyService;
import bc1.gream.domain.user.coupon.entity.CouponStatus;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.service.CouponService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class ChangingCouponStatusFacade { // 순환 참조를 예방하기 위한 BuyService와 CouponService의 콤비네이션 서비스

    private final CouponService couponService;
    private final BuyService buyService;


    @Transactional
    public void changeCouponStatus(Long buyId, User user, CouponStatus couponStatus) { // 쿠폰의 상태를 바꾸는 서비스

        Buy buy = checkBuy(buyId);
        Long couponId = buy.getCouponId();

        changeCouponStatusByCouponId(couponId, user, couponStatus);
    }

    @Transactional
    public void changeCouponStatusByCouponId(Long couponId, User user, CouponStatus couponStatus) {
        if (couponId == null) {
            return;
        }
        couponService.changeCouponStatus(couponId, user, couponStatus);
    }

    private Buy checkBuy(Long buyId) {
        return buyService.findBuyById(buyId);
    }

}
