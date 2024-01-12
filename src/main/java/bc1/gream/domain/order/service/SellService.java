package bc1.gream.domain.order.service;

import static bc1.gream.global.common.ResultCase.BUY_BID_PRODUCT_NOT_FOUND;
import static bc1.gream.global.common.ResultCase.GIFTICON_NOT_FOUND;
import static bc1.gream.global.common.ResultCase.NOT_AUTHORIZED;
import static bc1.gream.global.common.ResultCase.PRODUCT_NOT_FOUND;
import static bc1.gream.global.common.ResultCase.SELL_BID_PRODUCT_NOT_FOUND;

import bc1.gream.domain.order.dto.request.SellBidRequestDto;
import bc1.gream.domain.order.dto.request.SellNowRequestDto;
import bc1.gream.domain.order.dto.response.SellBidResponseDto;
import bc1.gream.domain.order.dto.response.SellCancelBidResponseDto;
import bc1.gream.domain.order.dto.response.SellNowResponseDto;
import bc1.gream.domain.order.entity.Buy;
import bc1.gream.domain.order.entity.Gifticon;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.entity.Sell;
import bc1.gream.domain.order.mapper.OrderMapper;
import bc1.gream.domain.order.mapper.SellMapper;
import bc1.gream.domain.order.repository.BuyRepository;
import bc1.gream.domain.order.repository.GifticonRepository;
import bc1.gream.domain.order.repository.OrderRepository;
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
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class SellService {

    private final SellRepository sellRepository;
    private final ProductRepository productRepository;
    private final GifticonRepository gifticonRepository;
    private final BuyRepository buyRepository;
    private final OrderRepository orderRepository;

    private final BuyService buyService;

    public SellBidResponseDto sellBidProduct(User user, SellBidRequestDto requestDto, Long productId) {
        Long price = requestDto.price();    // 판매하려는 가격
        Integer period = getPeriod(requestDto.period()); // requestDto에서 기간을 설정하는 값이 존재하는지 여부 체크하는 메소드
        LocalDate date = LocalDate.now();   // 현재 동작하고 있는 시스템의 날짜 표현
        LocalDateTime deadlineAt = date.atTime(LocalTime.MAX).plusDays(period); // 마감 기한을 위한 로직
        // date.atTime(LocalTime.Max)를 사용하면
        // 해당 date의 날짜의 가장 마지막 시간으로 세팅됩니다. 23시 59분 59초
        // 해당 date 기준으로 period만큼 날이 지난 날이 마감날로 세팅됩니다.
        Product product = getProductById(productId);
        Gifticon gifticon = saveGifticon(requestDto.gifticonUrl(), null); // Order는 우선 null로 입력 추후 즉시 구매 시 Order 넣어줄 예정

        Sell sell = Sell.builder()
            .price(price)
            .deadlineAt(deadlineAt)
            .user(user)
            .product(product)
            .gifticon(gifticon)
            .build();

        Sell savedSell = sellRepository.save(sell);

        return SellMapper.INSTANCE.toSellBidResponseDto(savedSell);
    }


    public SellCancelBidResponseDto sellCancelBid(User user, Long sellId) {
        Sell bidSell = findSellById(sellId);

        if (!isSellerLoggedInUser(bidSell, user)) {
            throw new GlobalException(NOT_AUTHORIZED);
        }

        Long gifticonId = bidSell.getGifticon().getId();

        sellRepository.delete(bidSell);
        deleteGifticon(gifticonId);

        return new SellCancelBidResponseDto();
    }

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

    private boolean isSellerLoggedInUser(Sell sell, User user) {
        return sell.getUser().getLoginId().equals(user.getLoginId());
    }

    private Gifticon saveGifticon(String gifticonUrl, Order order) {
        Gifticon gifticon = Gifticon.builder()
            .gifticonUrl(gifticonUrl)
            .order(order)
            .build();
        return gifticonRepository.save(gifticon);
    }

    private void deleteGifticon(Long gifticonId) {
        Gifticon gifticon = gifticonRepository.findById(gifticonId).orElseThrow(
            () -> new GlobalException(GIFTICON_NOT_FOUND)
        );

        gifticonRepository.delete(gifticon);
    }

    /**
     * Product에 대한 판매입찰가 내역 페이징 조회
     *
     * @param product  이모티콘 상품
     * @param pageable 페이징 요청 데이터
     * @return 판매입찰가 내역 페이징 데이터
     */
    @Transactional(readOnly = true)
    public Page<Sell> findAllSellBidsOf(Product product, Pageable pageable) {
        return sellRepository.findAllPricesOf(product, pageable);
    }
}
