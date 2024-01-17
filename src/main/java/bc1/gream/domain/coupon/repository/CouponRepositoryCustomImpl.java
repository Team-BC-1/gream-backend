package bc1.gream.domain.coupon.repository;

import static bc1.gream.domain.coupon.entity.CouponStatus.AVAILABLE;
import static bc1.gream.domain.coupon.entity.CouponStatus.IN_USE;
import static bc1.gream.domain.coupon.entity.QCoupon.coupon;

import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.user.entity.QUser;
import bc1.gream.domain.user.entity.User;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class CouponRepositoryCustomImpl implements CouponRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Coupon> availableCoupon(User user) {
        return queryFactory
            .selectFrom(coupon)
            .leftJoin(coupon.user, QUser.user)
            .where(
                coupon.user.eq(user),
                coupon.status.eq(AVAILABLE)
            )
            .fetch();
    }

    @Override
    public List<Coupon> unavailable(User user) {
        return queryFactory
            .selectFrom(coupon)
            .leftJoin(coupon.user, QUser.user)
            .where(
                coupon.user.eq(user),
                coupon.status.eq(IN_USE)
            )
            .fetch();
    }
}

