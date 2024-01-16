package bc1.gream.domain.sell.service;

import bc1.gream.domain.buy.service.BuyService;
import bc1.gream.domain.coupon.service.CouponService;
import bc1.gream.domain.gifticon.service.GifticonCommandService;
import bc1.gream.domain.order.service.command.OrderCommandService;
import bc1.gream.domain.sell.provider.SellNowProvider;
import bc1.gream.test.BuyTest;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

@ExtendWith(MockitoExtension.class)
@Rollback(value = true)
@Transactional
class SellNowProviderTest implements BuyTest {

    @InjectMocks
    private SellNowProvider sellNowProvider;
    @Mock
    private BuyService buyService;
    @Mock
    private CouponService couponService;
    @Mock
    private OrderCommandService orderCommandService;
    @Mock
    private GifticonCommandService gifticonCommandService;

    @Test
    public void 구매입찰() {
        // GIVEN

        // WHEN

        // THEN

    }
}