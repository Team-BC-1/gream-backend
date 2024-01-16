package bc1.gream.domain.gifticon.repository;

import bc1.gream.domain.order.entity.Gifticon;
import bc1.gream.domain.order.entity.QGifticon;
import bc1.gream.domain.order.entity.QOrder;
import bc1.gream.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class GifticonRepositoryCustomImpl implements GifticonRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Gifticon> findAllSoldBySeller(User user) {
        return queryFactory
            .selectFrom(QGifticon.gifticon)
            .leftJoin(QGifticon.gifticon.order, QOrder.order)
            .where(QGifticon.gifticon.order.seller.eq(user))
            .fetch();
    }
}
