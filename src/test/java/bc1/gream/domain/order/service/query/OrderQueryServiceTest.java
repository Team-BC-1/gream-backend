package bc1.gream.domain.order.service.query;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.repository.OrderRepository;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.test.OrderTest;
import java.time.LocalDateTime;
import java.util.List;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;


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
        Order pastOrder = Order.builder()
            .createdAt(LocalDateTime.of(2024, 1, 1, 12, 12))
            .build();
        Order recentOrder = Order.builder()
            .createdAt(LocalDateTime.of(2024, 1, 13, 12, 12))
            .build();
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
}