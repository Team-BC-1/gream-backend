package bc1.gream.domain.order.repository;

import bc1.gream.domain.gifticon.entity.QGifticon;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.order.entity.QOrder;
import bc1.gream.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class OrderRepositoryCutomImpl implements OrderRepositoryCutom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Order> findAllBoughtBy(User user) {
        return queryFactory
            .selectFrom(QOrder.order)
            .leftJoin(QOrder.order.gifticon, QGifticon.gifticon)
            .where(QOrder.order.buyer.eq(user))
            .fetch();
    }
}
