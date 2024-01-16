package bc1.gream.domain.buy.service;

import static bc1.gream.global.common.ResultCase.SELL_BID_PRODUCT_NOT_FOUND;

import bc1.gream.domain.buy.dto.request.BuyNowRequestDto;
import bc1.gream.domain.buy.dto.response.BuyNowResponseDto;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.order.repository.OrderRepository;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.repository.SellRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.exception.GlobalException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuyNowService {

    private final SellRepository sellRepository;
    private final BuyService buyService;
    private final OrderRepository orderRepository;

    @Transactional(propagation = Propagation.REQUIRED)
    public BuyNowResponseDto buyNowProduct(User user, BuyNowRequestDto requestDto, Long productId) {
        Long price = requestDto.price();    // finalPrice
        Sell sell = sellRepository.findByProductIdAndPrice(productId, price).orElseThrow(
            () -> new GlobalException(SELL_BID_PRODUCT_NOT_FOUND)
        );
        Long expectedPrice = buyService.calcDiscount(requestDto.couponId(), price, user);

        Order order = Order.builder()
            .product(sell.getProduct())
            .buyer(user)
            .seller(sell.getUser())
            .finalPrice(price)
            .expectedPrice(expectedPrice)
            .build();

        Order savedOrder = orderRepository.save(order);
        buyService.orderGifticonBySellId(sell.getId(), savedOrder);
        sellRepository.delete(sell);

        return OrderMapper.INSTANCE.toBuyNowResponseDto(savedOrder);
    }
}
