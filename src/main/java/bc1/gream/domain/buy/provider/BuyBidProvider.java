package bc1.gream.domain.buy.provider;

import static bc1.gream.global.common.ResultCase.NOT_AUTHORIZED;

import bc1.gream.domain.buy.dto.request.BuyBidRequestDto;
import bc1.gream.domain.buy.dto.response.BuyBidResponseDto;
import bc1.gream.domain.buy.dto.response.BuyCancelBidResponseDto;
import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.mapper.BuyMapper;
import bc1.gream.domain.buy.repository.BuyRepository;
import bc1.gream.domain.buy.service.BuyService;
import bc1.gream.domain.common.facade.ChangingCouponStatusFacade;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.sell.service.helper.deadline.Deadline;
import bc1.gream.domain.sell.service.helper.deadline.DeadlineCalculator;
import bc1.gream.domain.user.coupon.entity.CouponStatus;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.exception.GlobalException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuyBidProvider {

    private final BuyRepository buyRepository;
    private final BuyService buyService;
    private final ChangingCouponStatusFacade changingCouponStatusFacade;


    @Transactional
    public BuyBidResponseDto buyBidProduct(User user, BuyBidRequestDto requestDto, Product product) {
        Long price = requestDto.price();
        Integer period = Deadline.getPeriod(requestDto.period());
        LocalDateTime deadlineAt = DeadlineCalculator.calculateDeadlineBy(LocalDate.now(), LocalTime.MAX, period);
        Long couponId = requestDto.couponId();

        Buy buy = Buy.builder()
            .price(price)
            .deadlineAt(deadlineAt)
            .couponId(couponId)
            .user(user)
            .product(product)
            .build();

        Buy savedBuy = buyRepository.save(buy);

        changingCouponStatusFacade.changeCouponStatusByCouponId(requestDto.couponId(), user, CouponStatus.IN_USE);

        return BuyMapper.INSTANCE.toBuyBidResponseDto(savedBuy);
    }

    @Transactional(propagation = Propagation.REQUIRED)
    public BuyCancelBidResponseDto buyCancelBid(User user, Long buyId) {
        Buy buyBid = buyService.findBuyById(buyId);

        if (!buyService.isBuyerLoggedInUser(buyBid, user)) {
            throw new GlobalException(NOT_AUTHORIZED);
        }

        buyRepository.delete(buyBid);

        changingCouponStatusFacade.changeCouponStatus(buyId, user, CouponStatus.AVAILABLE);

        return new BuyCancelBidResponseDto(buyId);
    }

}
