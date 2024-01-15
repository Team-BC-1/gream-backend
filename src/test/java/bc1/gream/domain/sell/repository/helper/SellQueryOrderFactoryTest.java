package bc1.gream.domain.sell.repository.helper;

import com.querydsl.core.types.OrderSpecifier;
import org.junit.jupiter.api.Test;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

class SellQueryOrderFactoryTest {

    @Test
    public void 기본정렬기준생성() {
        // GIVEN
        Pageable pageable = PageRequest.of(0, 10);

        // WHEN
        OrderSpecifier[] orders = SellQueryOrderFactory.getOrdersOf(pageable.getSort());

        // THEN
        for (OrderSpecifier os : orders) {
            System.out.println("os.getOrder() = " + os.getOrder());
            System.out.println("os.getTarget() = " + os.getTarget());
            System.out.println("os = " + os);
        }
    }

}