package bc1.gream.domain.order.service;

import static bc1.gream.global.common.ResultCase.BUY_BID_PRODUCT_NOT_FOUND;

import bc1.gream.domain.order.dto.request.SellNowRequestDto;
import bc1.gream.domain.order.dto.response.SellNowResponseDto;
import bc1.gream.domain.order.entity.Buy;
import bc1.gream.domain.order.entity.Gifticon;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.order.repository.BuyRepository;
import bc1.gream.domain.order.repository.GifticonRepository;
import bc1.gream.domain.order.repository.OrderRepository;
import bc1.gream.domain.order.service.helper.CouponCalculator;
import bc1.gream.domain.user.entity.Coupon;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.service.CouponService;
import bc1.gream.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellNowService {

    private final GifticonRepository gifticonRepository;
    private final BuyRepository buyRepository;
    private final OrderRepository orderRepository;

    private final BuyService buyService;
    private final CouponService couponService;

    public SellNowResponseDto sellNowProduct(User user, SellNowRequestDto requestDto, Long productId) {
        // 해당상품과 가격에 대한 구매입찰을 가져옴
        Buy buy = buyRepository.findByProductIdAndPrice(productId, requestDto.price())
            .orElseThrow(() -> new GlobalException(BUY_BID_PRODUCT_NOT_FOUND));

        // 주문서를 발행함
        Long price = buy.getPrice();
        User buyer = buy.getUser();
        Coupon coupon = couponService.findCouponById(buy.getCouponId(), buyer);
        Long expectedPrice = CouponCalculator.calculatedDiscout(coupon, price);
        Order order = Order.builder()
            .product(buy.getProduct())
            .buyer(buy.getUser())
            .seller(user)
            .finalPrice(price)
            .expectedPrice(expectedPrice)
            .build();
        Order savedOrder = orderRepository.save(order);

        // 즉시구매 요청값에 따른 새로운 기프티콘을 발행
        saveGifticon(requestDto.gifticonUrl(), savedOrder);
        // 구매입찰을 지움
        buyRepository.delete(buy);

        // 매퍼를 통해 변환
        return OrderMapper.INSTANCE.toSellNowResponseDto(savedOrder);
    }

    private void saveGifticon(String gifticonUrl, Order order) {
        Gifticon gifticon = Gifticon.builder()
            .gifticonUrl(gifticonUrl)
            .order(order)
            .build();
        gifticonRepository.save(gifticon);
    }
}
