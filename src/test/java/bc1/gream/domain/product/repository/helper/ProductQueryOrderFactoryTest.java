package bc1.gream.domain.product.repository.helper;

import static bc1.gream.domain.product.entity.QProduct.product;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import java.util.Arrays;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort.Direction;

class ProductQueryOrderFactoryTest {

    @Test
    public void id입력_에대한Sort_반환() {
        // GIVEN
        Pageable page = PageRequest.of(0, 10, Direction.ASC, "id");

        // WHEN
        OrderSpecifier[] orders = ProductQueryOrderFactory.getOrdersOf(page.getSort());

        // THEN
        boolean hasTarget = Arrays.stream(orders)
            .anyMatch(orderSpecifier -> orderSpecifier.getTarget().equals(product.id));
        boolean hasOrder = Arrays.stream(orders)
            .anyMatch(orderSpecifier -> orderSpecifier.getOrder().equals(Order.ASC));
        assertTrue(hasTarget);
        assertTrue(hasOrder);
    }

    @Test
    public void name입력_에대한Sort_반환() {
        // GIVEN
        Pageable page = PageRequest.of(0, 10, Direction.ASC, "name");

        // WHEN
        OrderSpecifier[] orders = ProductQueryOrderFactory.getOrdersOf(page.getSort());

        // THEN
        boolean hasTarget = Arrays.stream(orders)
            .anyMatch(orderSpecifier -> orderSpecifier.getTarget().equals(product.name));
        boolean hasOrder = Arrays.stream(orders)
            .anyMatch(orderSpecifier -> orderSpecifier.getOrder().equals(Order.ASC));
        assertTrue(hasTarget);
        assertTrue(hasOrder);
    }

    @Test
    public void brand입력_에대한Sort_반환() {
        // GIVEN
        Pageable page = PageRequest.of(0, 10, Direction.ASC, "brand");

        // WHEN
        OrderSpecifier[] orders = ProductQueryOrderFactory.getOrdersOf(page.getSort());

        // THEN
        boolean hasTarget = Arrays.stream(orders)
            .anyMatch(orderSpecifier -> orderSpecifier.getTarget().equals(product.brand));
        boolean hasOrder = Arrays.stream(orders)
            .anyMatch(orderSpecifier -> orderSpecifier.getOrder().equals(Order.ASC));
        assertTrue(hasTarget);
        assertTrue(hasOrder);
    }

    @Test
    public void 이외입력_에대한Sort_반환하지_않음() {
        // GIVEN
        Pageable page = PageRequest.of(0, 10, Direction.ASC, "asdasd");

        // WHEN
        OrderSpecifier[] orders = ProductQueryOrderFactory.getOrdersOf(page.getSort());

        // THEN
        assertEquals(0, orders.length);
    }
}