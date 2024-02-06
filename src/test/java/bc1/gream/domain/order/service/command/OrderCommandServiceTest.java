package bc1.gream.domain.order.service.command;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;

import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.repository.OrderRepository;
import bc1.gream.test.BuyTest;
import bc1.gream.test.CouponTest;
import bc1.gream.test.OrderTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class OrderCommandServiceTest implements OrderTest, BuyTest, CouponTest {

    @InjectMocks
    private OrderCommandService orderCommandService;
    @Mock
    private OrderRepository orderRepository;

    @Test
    void 즉시구매로_인하여_새로운_주문이_생기는_서비스_기능_성공_테스트_쿠폰이_있을떄() {
        // given - when
        orderCommandService.saveOrderOfBuy(TEST_BUY_COUPON, TEST_SELLER, TEST_COUPON_FIX);

        // then
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void 즉시구매로_인하여_새로운_주문이_생기는_서비스_기능_성공_테스트_쿠폰이_없을때() {
        // given - when
        orderCommandService.saveOrderOfBuyNotCoupon(TEST_BUY, TEST_SELLER);

        // then
        verify(orderRepository, times(1)).save(any(Order.class));
    }

    @Test
    void () {
    }

    @Test
    void saveOrderOfSellNotCoupon() {
    }
}