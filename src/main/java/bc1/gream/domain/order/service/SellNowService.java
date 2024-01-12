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
import bc1.gream.domain.user.entity.User;
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

    public SellNowResponseDto sellNowProduct(User user, SellNowRequestDto requestDto, Long productId) {
        Buy buy = buyRepository.findByProductIdAndPrice(productId, requestDto.price()).orElseThrow(
            () -> new GlobalException(BUY_BID_PRODUCT_NOT_FOUND)
        );
        Long price = buy.getPrice();
        User buyer = buy.getUser();
        Long expectedPrice = buyService.calcDiscount(buy.getCouponId(), price, buyer);

        Order order = Order.builder()
            .product(buy.getProduct())
            .buyer(buy.getUser())
            .seller(user)
            .finalPrice(price)
            .expectedPrice(expectedPrice)
            .build();

        Order savedOrder = orderRepository.save(order);
        saveGifticon(requestDto.gifticonUrl(), savedOrder);
        buyRepository.delete(buy);

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
