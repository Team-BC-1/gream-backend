package bc1.gream.domain.order.service;

import static bc1.gream.global.common.ResultCase.BUY_BID_PRODUCT_NOT_FOUND;
import static bc1.gream.global.common.ResultCase.NOT_AUTHORIZED;
import static bc1.gream.global.common.ResultCase.PRODUCT_NOT_FOUND;

import bc1.gream.domain.order.dto.request.BuyBidRequestDto;
import bc1.gream.domain.order.dto.response.BuyBidResponseDto;
import bc1.gream.domain.order.dto.response.BuyCancelBidResponseDto;
import bc1.gream.domain.order.entity.Buy;
import bc1.gream.domain.order.repository.BuyRepository;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.exception.GlobalException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class BuyService {

    private final BuyRepository buyRepository;
    private final ProductRepository productRepository;

    public BuyBidResponseDto buyBidProduct(User user, BuyBidRequestDto requestDto, Long productId) {
        Long price = requestDto.price();
        Integer period = getPeriod(requestDto.period());
        LocalDate date = LocalDate.now();
        LocalDateTime deadlineAt = date.atTime(LocalTime.MAX).plusDays(period);
        Product product = getProductById(productId);

        Buy buy = Buy.builder()
            .price(price)
            .deadlineAt(deadlineAt)
            .user(user)
            .product(product)
            .build();

        Buy savedBuy = buyRepository.save(buy);

        return BuyServiceMapper.INSTANCE.toBuyBidResponseDto(savedBuy);
    }


    public BuyCancelBidResponseDto buyCancelBid(User user, Long buyId) {
        Buy buyBid = findBuyById(buyId);

        if (isNotBuyerLoggedInUser(buyBid, user)) {
            throw new GlobalException(NOT_AUTHORIZED);
        }

        buyRepository.delete(buyBid);

        return new BuyCancelBidResponseDto();
    }

    private Integer getPeriod(Integer period) {
        return Objects.requireNonNullElse(period, 7);
    }

    private Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(
            () -> new GlobalException(PRODUCT_NOT_FOUND)
        );
    }

    protected Buy findBuyById(Long buyId) {
        return buyRepository.findById(buyId).orElseThrow(
            () -> new GlobalException(BUY_BID_PRODUCT_NOT_FOUND)
        );
    }

    private boolean isNotBuyerLoggedInUser(Buy buy, User user) {
        return !buy.getUser().getLoginId().equals(user.getLoginId());
    }
}