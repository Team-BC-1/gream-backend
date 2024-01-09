package bc1.gream.domain.product.service.query.impl;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.mockito.BDDMockito.given;

import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.service.query.OrderQueryService;
import bc1.gream.domain.product.service.query.ProductOrderQueryService;
import bc1.gream.domain.product.service.query.ProductQueryService;
import bc1.gream.test.OrderTest;
import bc1.gream.test.ProductTest;
import bc1.gream.test.UserTest;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

@ExtendWith(MockitoExtension.class)
class ProductOrderQueryServiceImplDelTest implements UserTest, ProductTest, OrderTest {

    @InjectMocks
    ProductOrderQueryService productOrderQueryService;

    @Mock
    ProductQueryService productQueryService;

    @Mock
    OrderQueryService orderQueryService;

    @Test
    @DisplayName("상품에 대한 모든 체결거래 내역을 조회합니다.")
    public void 체결거래내역_조회() {
        // GIVEN
        given(productQueryService.findBy(TEST_PRODUCT_ID)).willReturn(TEST_PRODUCT);
        given(orderQueryService.findAllTradesOf(TEST_PRODUCT)).willReturn(List.of(TEST_ORDER));

        // WHEN
        List<Order> allTradesOf = productOrderQueryService.findAllTradesOf(TEST_PRODUCT_ID);

        // THEN
        assertFalse(allTradesOf.isEmpty());
        assertEquals(1, allTradesOf.size());
    }
}