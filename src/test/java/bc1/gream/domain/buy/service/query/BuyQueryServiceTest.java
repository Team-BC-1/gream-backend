package bc1.gream.domain.buy.service.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;

import bc1.gream.domain.buy.dto.response.BuyCheckBidResponseDto;
import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.repository.BuyRepository;
import bc1.gream.domain.coupon.helper.CouponCalculator;
import bc1.gream.domain.product.dto.response.BuyPriceToQuantityResponseDto;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import bc1.gream.test.BuyTest;
import bc1.gream.test.CouponTest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class BuyQueryServiceTest implements BuyTest, CouponTest {

    @InjectMocks
    private BuyQueryService buyQueryService;
    @Mock
    private BuyRepository buyRepository;

    @Test
    void 구매_아이디로_구매입찰_찾아오는_서비스_기능_성공_테스트() {

        // given
        given(buyRepository.findById(any(Long.class))).willReturn(Optional.of(TEST_BUY));

        // when
        Buy buy = buyQueryService.findBuyById(any(Long.class));

        // then
        assertThat(buy.getPrice()).isEqualTo(TEST_BUY_PRICE);
        assertThat(buy.getUser()).isEqualTo(TEST_USER);
        assertThat(buy.getProduct()).isEqualTo(TEST_PRODUCT);
    }

    @Test
    void 구매_아이디로_구매입찰_찾아오는_서비스_기능_실패_테스트() {

        // given
        given(buyRepository.findById(any(Long.class))).willReturn(Optional.empty());

        // when
        GlobalException exception = assertThrows(GlobalException.class, () -> {
            buyQueryService.findBuyById(any(Long.class));
        });

        // then
        assertThat(exception.getResultCase()).isEqualTo(ResultCase.BUY_BID_NOT_FOUND);
        assertThat(exception.getResultCase().getCode()).isEqualTo(3001);
        assertThat(exception.getResultCase().getMessage()).isEqualTo("해당 구매 입찰 건은 존재하지 않습니다.");
    }

    @Test
    void 특정_상품의_구매입찰_중_같은_가격의_개수를_모두_카운트하고_페이징하여_출력하는_서비스_기능_성공_테스트() {

        // given
        Pageable pageable = PageRequest.of(0, 5);

        List<BuyPriceToQuantityResponseDto> responseDtoList = new ArrayList<>();

        for (int i = 1; i <= 5; i++) {
            BuyPriceToQuantityResponseDto responseDto = BuyPriceToQuantityResponseDto.builder()
                .buyPrice(1000L * i)
                .quantity((long) i)
                .build();
            responseDtoList.add(responseDto);
        }

        int start = (int) pageable.getOffset();
        int end = Math.min((start + pageable.getPageSize()), responseDtoList.size());
        Page<BuyPriceToQuantityResponseDto> responseDtoPage = new PageImpl<>(responseDtoList.subList(start, end), pageable,
            responseDtoList.size());

        given(buyRepository.findAllPriceToQuantityOf(any(Product.class), any(Pageable.class))).willReturn(responseDtoPage);
        // when
        Page<BuyPriceToQuantityResponseDto> returnPage = buyQueryService.findAllBuyBidsOf(TEST_PRODUCT, pageable);

        // then
        assertThat(returnPage.getSize()).isEqualTo(5);
        assertThat(returnPage.getContent().get(0).buyPrice()).isEqualTo(1000L);
        assertThat(returnPage.getContent().get(1).buyPrice()).isEqualTo(2000L);
        assertThat(returnPage.getContent().get(2).buyPrice()).isEqualTo(3000L);
        assertThat(returnPage.getContent().get(3).buyPrice()).isEqualTo(4000L);
        assertThat(returnPage.getContent().get(4).buyPrice()).isEqualTo(5000L);
    }

    @Test
    void 가장_최근의_구매입찰을_찾아오는_서비스_기능_성공_테스트() {

        // given
        given(buyRepository.findByProductIdAndPrice(any(Long.class), any(Long.class))).willReturn(Optional.of(TEST_BUY));

        // when
        Buy resultBuy = buyQueryService.getRecentBuyBidOf(TEST_PRODUCT_ID, TEST_BUY_PRICE);

        // then
        assertThat(resultBuy.getPrice()).isEqualTo(TEST_BUY.getPrice());
        assertThat(resultBuy.getProduct()).isEqualTo(TEST_BUY.getProduct());
        assertThat(resultBuy.getUser()).isEqualTo(TEST_BUY.getUser());
    }

    @Test
    void 가장_최근의_구매입찰을_찾아오는_서비스_기능_실패_테스트() {

        // given
        given(buyRepository.findByProductIdAndPrice(any(Long.class), any(Long.class))).willReturn(Optional.empty());

        // when
        GlobalException exception = assertThrows(GlobalException.class, () -> {
            buyQueryService.getRecentBuyBidOf(TEST_PRODUCT_ID, TEST_BUY_PRICE);
        });

        // then
        assertThat(exception.getResultCase()).isEqualTo(ResultCase.BUY_BID_NOT_FOUND);
        assertThat(exception.getResultCase().getMessage()).isEqualTo(ResultCase.BUY_BID_NOT_FOUND.getMessage());
        assertThat(exception.getResultCase().getCode()).isEqualTo(ResultCase.BUY_BID_NOT_FOUND.getCode());
    }

    @Test
    void 현재_진행중인_유저의_구매입찰_전체_조회_서비스_기능_성공_테스트() {

        // given
        List<BuyCheckBidResponseDto> responseDtoList = new ArrayList<>();
        for (int i = 1; i <= 5; i++) {
            BuyCheckBidResponseDto responseDto = BuyCheckBidResponseDto.builder()
                .buyId((long) i)
                .price(1000L * i)
                .discountPrice(1000L * i)
                .coupon((i % 2 == 0) ? TEST_COUPON_FIX : null)
                .build();

            responseDtoList.add(responseDto);
        }
        given(buyRepository.findAllBuyBidCoupon(any(User.class))).willReturn(responseDtoList);

        // when
        List<BuyCheckBidResponseDto> resultList = buyQueryService.findAllBuyBidCoupon(TEST_USER);

        // then
        assertThat(resultList.size()).isEqualTo(5);
        assertThat(resultList.get(0).discountPrice()).isEqualTo(responseDtoList.get(0).discountPrice());
        assertThat(resultList.get(1).discountPrice()).isEqualTo(
            CouponCalculator.calculateDiscount(TEST_COUPON_FIX, responseDtoList.get(1).discountPrice()));
        assertThat(resultList.get(2).discountPrice()).isEqualTo(responseDtoList.get(2).discountPrice());
        assertThat(resultList.get(3).discountPrice()).isEqualTo(
            CouponCalculator.calculateDiscount(TEST_COUPON_FIX, responseDtoList.get(3).discountPrice()));
        assertThat(resultList.get(4).discountPrice()).isEqualTo(responseDtoList.get(4).discountPrice());
    }

    @Test
    void 유저의_포인트_체크하는_서비스_기능_성공_테스트() {

        // given
        ReflectionTestUtils.setField(TEST_USER, "point", 10000L);

        // when - then
        buyQueryService.userPointCheck(TEST_USER, 5000L);
    }

    @Test
    void 유저의_포인트_체크하는_서비스_기능_실패_테스트() {

        // given
        ReflectionTestUtils.setField(TEST_USER, "point", 3000L);

        // when
        GlobalException exception = assertThrows(GlobalException.class, () -> {
            buyQueryService.userPointCheck(TEST_USER, 5000L);
        });

        // then
        assertThat(exception.getResultCase()).isEqualTo(ResultCase.NOT_ENOUGH_POINT);
        assertThat(exception.getResultCase().getCode()).isEqualTo(1007);
        assertThat(exception.getResultCase().getMessage()).isEqualTo("유저의 포인트가 부족합니다");
    }
}