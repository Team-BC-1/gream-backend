package bc1.gream.domain.buy.repository.helper;

import static org.junit.jupiter.api.Assertions.assertEquals;

import bc1.gream.domain.buy.entity.QBuy;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class BuyQueryOrderFactoryTest {

    @Test
    public void 기본정렬기준생성() {
        // GIVEN
        Pageable pageable = PageRequest.of(0, 10);

        // WHEN
        OrderSpecifier[] orders = BuyQueryOrderFactory.getOrdersOf(pageable.getSort());

        // THEN
        assertEquals(Order.DESC, orders[0].getOrder());
        assertEquals(QBuy.buy.price, orders[0].getTarget());
    }
}