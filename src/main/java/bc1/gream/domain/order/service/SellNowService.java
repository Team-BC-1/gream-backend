package bc1.gream.domain.order.service;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.repository.BuyRepository;
import bc1.gream.domain.buy.service.BuyService;
import bc1.gream.domain.gifticon.service.GifticonService;
import bc1.gream.domain.order.dto.request.SellNowRequestDto;
import bc1.gream.domain.order.dto.response.SellNowResponseDto;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.order.service.command.OrderCommandService;
import bc1.gream.domain.user.entity.Coupon;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.service.CouponService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
@Transactional
public class SellNowService {

    private final BuyRepository buyRepository;
    private final BuyService buyService;
    private final CouponService couponService;
    private final OrderCommandService orderCommandService;
    private final GifticonService gifticonService;

    public SellNowResponseDto sellNowProduct(User user, SellNowRequestDto requestDto, Long productId) {
        // 해당상품과 가격에 대한 구매입찰
        Buy buy = buyService.getRecentBuyBidOf(productId, requestDto.price());
        // 쿠폰 조회
        Coupon coupon = couponService.findCouponById(buy.getCouponId(), buy.getUser());
        // 새로운 주문
        Order order = orderCommandService.saveOrderOfBuy(buy, user, coupon);
        // 새로운 기프티콘 저장
        gifticonService.saveGifticon(requestDto.gifticonUrl(), order);

        // 구매입찰 삭제
        buyRepository.delete(buy);

        // 매퍼를 통해 변환
        return OrderMapper.INSTANCE.toSellNowResponseDto(order);
    }
}
