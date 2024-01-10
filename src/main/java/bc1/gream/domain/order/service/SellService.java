package bc1.gream.domain.order.service;

import static bc1.gream.global.common.ResultCase.GIFTICON_NOT_FOUND;
import static bc1.gream.global.common.ResultCase.NOT_AUTHORIZED;
import static bc1.gream.global.common.ResultCase.PRODUCT_NOT_FOUND;
import static bc1.gream.global.common.ResultCase.SEARCH_RESULT_NOT_FOUND;
import static bc1.gream.global.common.ResultCase.SELL_BID_PRODUCT_NOT_FOUND;

import bc1.gream.domain.order.dto.request.SellBidRequestDto;
import bc1.gream.domain.order.dto.response.SellBidResponseDto;
import bc1.gream.domain.order.dto.response.SellCancelBidResponseDto;
import bc1.gream.domain.order.entity.Gifticon;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.entity.Sell;
import bc1.gream.domain.order.mapper.SellMapper;
import bc1.gream.domain.order.repository.GifticonRepository;
import bc1.gream.domain.order.repository.SellRepository;
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
public class SellService {

    private final SellRepository sellRepository;
    private final ProductRepository productRepository;
    private final GifticonRepository gifticonRepository;

    public SellBidResponseDto sellBidProduct(User user, SellBidRequestDto requestDto, Long productId) {
        Long price = requestDto.price();    // 판매하려는 가격
        Integer period = getPeriod(requestDto.period()); // requestDto에서 기간을 설정하는 값이 존재하는지 여부 체크하는 메소드
        LocalDate date = LocalDate.now();   // 현재 동작하고 있는 시스템의 날짜 표현
        LocalDateTime deadlineAt = date.atTime(LocalTime.MAX).plusDays(period); // 마감 기한을 위한 로직
        // date.atTime(LocalTime.Max)를 사용하면
        // 해당 date의 날짜의 가장 마지막 시간으로 세팅됩니다. 23시 59분 59초
        // 해당 date 기준으로 period만큼 날이 지난 날이 마감날로 세팅됩니다.
        Product product = getProductById(productId);

        Sell sell = Sell.builder()
            .price(price)
            .deadlineAt(deadlineAt)
            .user(user)
            .product(product)
            .build();

        Sell savedSell = sellRepository.save(sell);

        saveGifticon(requestDto.gifticonUrl(), savedSell, null); // Order는 우선 null로 입력 추후 즉시 구매 시 Order 넣어줄 예정

        return SellMapper.INSTANCE.toSellBidResponseDto(savedSell);
    }


    public SellCancelBidResponseDto sellCancelBid(User user, Long sellId) {
        Sell bidSell = findSellById(sellId);

        if (isNotSellerLoggedInUser(bidSell, user)) {
            throw new GlobalException(NOT_AUTHORIZED);
        }

        sellRepository.delete(bidSell);
        deleteGifticon(sellId);

        return new SellCancelBidResponseDto();
    }

    private Integer getPeriod(Integer period) {
        return Objects.requireNonNullElse(period, 7);
    }

    private Product getProductById(Long productId) {
        return productRepository.findById(productId).orElseThrow(
            () -> new GlobalException(PRODUCT_NOT_FOUND)
        );
    }

    protected Sell findSellById(Long sellId) {
        return sellRepository.findById(sellId).orElseThrow(
            () -> new GlobalException(SELL_BID_PRODUCT_NOT_FOUND)
        );
    }

    private boolean isNotSellerLoggedInUser(Sell sell, User user) {
        return !sell.getUser().getLoginId().equals(user.getLoginId());
    }

    private void saveGifticon(String gifticonUrl, Sell sell, Order order) {
        Gifticon gifticon = Gifticon.builder()
            .gifticonUrl(gifticonUrl)
            .sell(sell)
            .order(order)
            .build();
        gifticonRepository.save(gifticon);
    }

    private void deleteGifticon(Long sellId) {
        Gifticon gifticon = gifticonRepository.findBySell_Id(sellId).orElseThrow(
            () -> new GlobalException(GIFTICON_NOT_FOUND)
        );

        gifticonRepository.delete(gifticon);
    }
}
