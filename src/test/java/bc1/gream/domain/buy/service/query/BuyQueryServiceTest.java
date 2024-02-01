package bc1.gream.domain.buy.service.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

import bc1.gream.domain.buy.repository.BuyRepository;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import bc1.gream.test.UserTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;

@ExtendWith(MockitoExtension.class)
class BuyQueryServiceTest implements UserTest {

    @InjectMocks
    private BuyQueryService buyQueryService;
    @Mock
    private BuyRepository buyRepository;

    @Test
    void findBuyById() {
    }

    @Test
    void findAllBuyBidsOf() {
    }

    @Test
    void getRecentBuyBidOf() {
    }

    @Test
    void findAllBuyBidCoupon() {
    }

    @Test
    void 유저의_포인트_체크하는_기능_성공_테스트() {

        // given
        ReflectionTestUtils.setField(TEST_USER, "point", 10000L);

        // when - then
        buyQueryService.userPointCheck(TEST_USER, 5000L);
    }

    @Test
    void 유저의_포인트_체크하는_기능_실패_테스트() {

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