package bc1.gream.domain.order.repository;

import static org.junit.jupiter.api.Assertions.assertEquals;

import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.domain.user.repository.UserRepository;
import bc1.gream.global.config.QueryDslConfig;
import bc1.gream.global.jpa.AuditingConfig;
import bc1.gream.test.OrderTest;
import java.util.List;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase.Replace;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.context.annotation.Import;
import org.springframework.test.context.ActiveProfiles;

@ActiveProfiles("test")
@DataJpaTest
@AutoConfigureTestDatabase(replace = Replace.NONE)
@Import({QueryDslConfig.class, AuditingConfig.class})
class OrderRepositoryTest implements OrderTest {

    @Autowired
    private UserRepository userRepository;
    @Autowired
    private ProductRepository productRepository;
    @Autowired
    private OrderRepository orderRepository;
    private User buyer;
    private User seller;
    private Product product;
    private Order pastOrder;
    private Order recentOrder;

    @BeforeEach
    void setUp() {
        buyer = userRepository.save(TEST_BUYER);
        seller = userRepository.save(TEST_SELLER);
        product = productRepository.save(TEST_PRODUCT);
        pastOrder = orderRepository.save(
            Order.builder()
                .product(product)
                .buyer(buyer)
                .seller(seller)
                .finalPrice(TEST_ORDER_FINAL_PRICE)
                .expectedPrice(TEST_ORDER_EXPECTED_PRICE)
                .build()
        );
        recentOrder = orderRepository.save(
            Order.builder()
                .product(product)
                .buyer(buyer)
                .seller(seller)
                .finalPrice(TEST_ORDER_FINAL_PRICE)
                .expectedPrice(TEST_ORDER_EXPECTED_PRICE)
                .build()
        );
    }

    @AfterEach
    void tearDown() {
        orderRepository.deleteAllInBatch();
        productRepository.deleteAllInBatch();
        userRepository.deleteAllInBatch();
    }

    @Test
    @DisplayName("가장 최근 순으로 거래가를 조회합니다.")
    public void 가장최근순_거래가_조회() {
        // WHEN
        List<Order> tradedOrders = orderRepository.findAllByProductOrderByCreatedAtDesc(product);

        // THEN
        assertEquals(2, tradedOrders.size());
        assertEquals(recentOrder, tradedOrders.get(0));
        assertEquals(pastOrder, tradedOrders.get(1));
    }
}