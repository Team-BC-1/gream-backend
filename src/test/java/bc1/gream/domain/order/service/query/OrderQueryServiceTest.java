package bc1.gream.domain.order.service.query;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.repository.OrderRepository;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.user.entity.User;
import bc1.gream.test.OrderTest;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.test.util.ReflectionTestUtils;


@ExtendWith(MockitoExtension.class)
class OrderQueryServiceTest implements OrderTest {

    @Mock
    private OrderRepository orderRepository;

    @InjectMocks
    private OrderQueryService orderQueryService;

    @Test
    @DisplayName("가장 최근 순으로 거래가를 조회합니다.")
    public void 가장최근순_거래가_조회() {
        // GIVEN
        Product mockProduct = mock(Product.class);
        Order pastOrder = Order.builder().build();
        ReflectionTestUtils.setField(pastOrder, "createdAt", LocalDateTime.of(2024, 1, 13, 12, 12));

        Order recentOrder = Order.builder().build();
        ReflectionTestUtils.setField(recentOrder, "createdAt", LocalDateTime.of(2024, 1, 13, 12, 12));
        List<Order> mockOrders = List.of(recentOrder, pastOrder);

        // WHEN
        when(orderRepository.findAllByProductOrderByCreatedAtDesc(mockProduct))
            .thenReturn(mockOrders);
        List<Order> trades = orderQueryService.findAllTradesOf(mockProduct);

        // THEN
        assertEquals(2, trades.size());
        assertEquals(recentOrder, trades.get(0));
        assertEquals(pastOrder, trades.get(1));
    }

    @Test
    void 판매자_기준_주문_전체_조회하는_서비스_기능_성공_테스트() {
        // given
        List<Order> orderList = new ArrayList<>();

        for (int i = 0; i < 5; i++) {
            Order order = Order.builder()
                .buyer(TEST_BUYER)
                .seller(TEST_SELLER)
                .product(TEST_PRODUCT)
                .finalPrice(1000L * i)
                .expectedPrice(1000L * i)
                .build();

            orderList.add(order);
        }

        given(orderRepository.findAllBySellerOrderByCreatedAtDesc(any(User.class))).willReturn(orderList);
        // when
        List<Order> resultList = orderQueryService.findAllOrderBySeller(TEST_SELLER);

        // then
        assertThat(resultList.get(0).getExpectedPrice()).isEqualTo(orderList.get(0).getExpectedPrice());
        assertThat(resultList.get(1).getExpectedPrice()).isEqualTo(orderList.get(1).getExpectedPrice());
        assertThat(resultList.get(2).getExpectedPrice()).isEqualTo(orderList.get(2).getExpectedPrice());
        assertThat(resultList.get(3).getExpectedPrice()).isEqualTo(orderList.get(3).getExpectedPrice());
        assertThat(resultList.get(4).getExpectedPrice()).isEqualTo(orderList.get(4).getExpectedPrice());

    }
}