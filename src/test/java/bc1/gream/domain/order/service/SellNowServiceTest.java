package bc1.gream.domain.order.service;

import bc1.gream.domain.gifticon.service.GifticonService;
import bc1.gream.domain.order.service.command.OrderCommandService;
import bc1.gream.domain.user.service.CouponService;
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
class SellNowServiceTest implements BuyTest {

    @InjectMocks
    private SellNowService sellNowService;
    @Mock
    private BuyService buyService;
    @Mock
    private CouponService couponService;
    @Mock
    private OrderCommandService orderCommandService;
    @Mock
    private GifticonService gifticonService;

    @Test
    public void 구매입찰() {
        // GIVEN

        // WHEN

        // THEN

    }
}