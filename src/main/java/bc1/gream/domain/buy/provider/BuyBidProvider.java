package bc1.gream.domain.buy.provider;

import bc1.gream.domain.buy.dto.request.BuyBidRequestDto;
import bc1.gream.domain.buy.dto.response.BuyBidResponseDto;
import bc1.gream.domain.buy.dto.response.BuyCancelBidResponseDto;
import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.mapper.BuyMapper;
import bc1.gream.domain.buy.repository.BuyRepository;
import bc1.gream.domain.buy.service.BuyService;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.coupon.entity.CouponStatus;
import bc1.gream.domain.coupon.service.command.CouponCommandService;
import bc1.gream.domain.coupon.service.qeury.CouponQueryService;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.sell.service.helper.deadline.Deadline;
import bc1.gream.domain.sell.service.helper.deadline.DeadlineCalculator;
import bc1.gream.domain.user.entity.User;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuyBidProvider {

    private final BuyRepository buyRepository;
    private final BuyService buyService;
    private final CouponQueryService couponQueryService;
    private final CouponCommandService couponCommandService;


    @Transactional
    public BuyBidResponseDto buyBidProduct(User buyer, BuyBidRequestDto requestDto, Product product) {
        Long price = requestDto.price();
        Integer period = Deadline.getPeriod(requestDto.period());
        LocalDateTime deadlineAt = DeadlineCalculator.calculateDeadlineBy(LocalDate.now(), LocalTime.MAX, period);

        Long couponId = requestDto.couponId();

        if (couponId != null) {
            Coupon coupon = couponQueryService.checkCoupon(couponId, buyer, CouponStatus.AVAILABLE);
            couponCommandService.changeCouponStatus(coupon, CouponStatus.IN_USE);
        }

        Buy buy = Buy.builder()
            .price(price)
            .deadlineAt(deadlineAt)
            .couponId(couponId)
            .user(buyer)
            .product(product)
            .build();

        Buy savedBuy = buyRepository.save(buy);

        return BuyMapper.INSTANCE.toBuyBidResponseDto(savedBuy);
    }

    @Transactional
    public BuyCancelBidResponseDto buyCancelBid(User buyer, Long buyId) {
        Buy buy = buyService.findBuyById(buyId);
        if (buy.getCouponId() != null) {
            Coupon coupon = couponQueryService.checkCoupon(buy.getCouponId(), buyer, CouponStatus.IN_USE);
            couponCommandService.changeCouponStatus(coupon, CouponStatus.AVAILABLE);
        }
        buyService.deleteBuyByIdAndUser(buy, buyer);

        return new BuyCancelBidResponseDto(buyId);
    }

}
