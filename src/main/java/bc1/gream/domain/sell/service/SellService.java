package bc1.gream.domain.sell.service;

import static bc1.gream.global.common.ResultCase.*;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.product.service.ProductService;
import bc1.gream.domain.sell.dto.request.SellBidRequestDto;
import bc1.gream.domain.sell.dto.response.SellBidResponseDto;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.repository.SellRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.Objects;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellService {

    private final SellRepository sellRepository;
    private final ProductRepository productRepository;

    public SellBidResponseDto sellBidProduct(User user, SellBidRequestDto requestDto, Long productId) {
        Long price = requestDto.price();
        LocalDate date = LocalDate.now();
        Integer period = getPeriod(requestDto.period());
        LocalDateTime deadlineAt = date.atTime(LocalTime.MAX).plusDays(period);
        Product product = getProductById(productId);

        Sell sell = Sell.builder()
            .price(price)
            .deadlineAt(deadlineAt)
            .user(user)
            .product(product)
            .build();

        Sell savedSell = sellRepository.save(sell);

        return SellServiceMapper.INSTANCE.toSellBidResponseDto(savedSell);
    }

    private Integer getPeriod(Integer period) {
        return Objects.requireNonNullElse(period, 7);
    }

    private Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(
            () -> new GlobalException(PRODUCT_NOT_FOUND)
        );
    }
}
