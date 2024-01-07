package bc1.gream.domain.sell.service.impl;

import static java.time.LocalDateTime.now;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.sell.dto.request.SellBidRequestDto;
import bc1.gream.domain.sell.dto.request.SellNowRequestDto;
import bc1.gream.domain.sell.dto.response.SellBidResponseDto;
import bc1.gream.domain.sell.dto.response.SellNowResponseDto;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.repository.SellRepository;
import bc1.gream.domain.sell.service.SellService;
import bc1.gream.domain.sell.service.SellServiceMapper;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import jakarta.transaction.Transactional;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class SellServiceImpl implements SellService {

    private final SellRepository sellRepository;
    private final ProductRepository productRepository;

    @Override
    @Transactional
    public SellNowResponseDto sellNowProduct(User user, SellNowRequestDto requestDto, Long productId) {
        Sell sell = nowSellProduct(user, requestDto, productId);
        Sell savedSell = sellRepository.save(sell);

        return SellServiceMapper.INSTANCE.toSellNowResponseDto(savedSell);
    }

    @Override
    public SellBidResponseDto sellBidProduct(User user, SellBidRequestDto requestDto, Long productId) {
        Sell sell = bidSellProduct(user, requestDto, productId);
        Sell savedSell = sellRepository.save(sell);
        return SellServiceMapper.INSTANCE.toSellBidResponseDto(savedSell);
    }

    private Sell nowSellProduct(User user, SellNowRequestDto requestDto, Long productId) {

        Long price = requestDto.price();
        Boolean isClose = true;
        LocalDateTime deadLineAt = now();
        Product product = findProductById(productId);
        Boolean isNow = true;
        String paymentType = requestDto.paymentType();

        return Sell.builder()
            .price(price)
            .isClose(isClose)
            .deadlineAt(deadLineAt)
            .isNow(isNow)
            .paymentType(paymentType)
            .user(user)
            .product(product)
            .build();
    }

    private Sell bidSellProduct(User user, SellBidRequestDto requestDto, Long productId) {

        Long price = requestDto.price();
        Boolean isClose = false;
        LocalDateTime deadLineAt = now().plusDays(7);
        if (requestDto.period() != null) {
            deadLineAt = now().plusDays(requestDto.period());
        }
        Product product = findProductById(productId);
        Boolean isNow = false;
        String paymentType = requestDto.paymentType();

        return Sell.builder()
            .price(price)
            .isClose(isClose)
            .deadlineAt(deadLineAt)
            .isNow(isNow)
            .paymentType(paymentType)
            .product(product)
            .user(user)
            .build();
    }


    private Product findProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(
            () -> new GlobalException(ResultCase.PRODUCT_NOT_FOUND)
        );
    }

}
