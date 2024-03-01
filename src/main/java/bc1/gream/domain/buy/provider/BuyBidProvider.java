package bc1.gream.domain.buy.provider;

import bc1.gream.domain.buy.dto.request.BuyBidRequestDto;
import bc1.gream.domain.buy.dto.response.BuyBidResponseDto;
import bc1.gream.domain.buy.dto.response.BuyCancelBidResponseDto;
import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.mapper.BuyMapper;
import bc1.gream.domain.buy.repository.BuyRepository;
import bc1.gream.domain.buy.service.command.BuyCommandService;
import bc1.gream.domain.buy.service.query.BuyQueryService;
import bc1.gream.domain.buy.validator.BuyAvailabilityVerifier;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.CouponStatus;
import bc1.gream.domain.coupon.helper.CouponCalculator;
import bc1.gream.domain.coupon.service.command.CouponCommandService;
import bc1.gream.domain.coupon.service.qeury.CouponQueryService;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.sell.service.helper.deadline.DeadlineCalculator;
import bc1.gream.domain.user.entity.User;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuyBidProvider {

    private final BuyRepository buyRepository;
    private final BuyCommandService buyCommandService;
    private final BuyQueryService buyQueryService;
    private final CouponQueryService couponQueryService;
    private final CouponCommandService couponCommandService;


    @Transactional
    public BuyBidResponseDto buyBidProduct(User buyer, BuyBidRequestDto requestDto, Product product) {
        // 쿠폰 조회
        Coupon coupon = couponQueryService.getCouponFrom(requestDto.couponId(), buyer);
        // 구매자에 대한 구매가능성 여부 검증
        BuyAvailabilityVerifier.verifyBuyerEligibility(requestDto.price(), coupon, buyer);
        // 할인적용된 최종가격
        Long finalPrice = CouponCalculator.calculateDiscount(coupon, requestDto.price());

        // 마감기한에 따른 구매입찰 생성, 저장
        LocalDateTime deadlineAt = DeadlineCalculator.getDeadlineOf(requestDto.period());
        Buy buy = Buy.builder()
            .price(requestDto.price())
            .deadlineAt(deadlineAt)
            .couponId(requestDto.couponId())
            .user(buyer)
            .product(product)
            .build();
        Buy savedBuy = buyRepository.save(buy);

        // 구매자 포인트 삭감
        buyer.decreasePoint(finalPrice);

        return BuyMapper.INSTANCE.toBuyBidResponseDto(savedBuy);
    }

    @Transactional
    public BuyCancelBidResponseDto buyCancelBid(User buyer, Long buyId) {
        Buy buy = buyQueryService.findBuyById(buyId);
        Long finalPrice = buy.getPrice();
        if (buy.getCouponId() != null) {
            Coupon coupon = couponQueryService.checkCoupon(buy.getCouponId(), buyer, CouponStatus.IN_USE);
            couponCommandService.changeCouponStatus(coupon, CouponStatus.AVAILABLE);
            finalPrice = CouponCalculator.calculateDiscount(coupon, finalPrice);
        }
        buyCommandService.deleteBuyByIdAndUser(buy, buyer);
        buyer.increasePoint(finalPrice);

        return new BuyCancelBidResponseDto(buyId);
    }
}