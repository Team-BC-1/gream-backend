package bc1.gream.domain.buy.repository;


import bc1.gream.domain.buy.dto.response.BuyCheckBidResponseDto;
import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.entity.QBuy;
import bc1.gream.domain.buy.repository.helper.BuyQueryOrderFactory;
import bc1.gream.domain.coupon.entity.QCoupon;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.entity.QProduct;
import bc1.gream.domain.user.entity.User;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import java.util.Optional;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;
import org.springframework.stereotype.Repository;

@Repository
@RequiredArgsConstructor
public class BuyRepositoryCustomImpl implements BuyRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public Page<Buy> findAllPricesOf(Product product, Pageable pageable) {
        // Get Orders By Columns
        OrderSpecifier[] orderSpecifiers = BuyQueryOrderFactory.getOrdersOf(pageable.getSort());

        // Query + Order + Paging
        List<Buy> buys = queryFactory
            .selectFrom(QBuy.buy)
            .leftJoin(QBuy.buy.product, QProduct.product)
            .where(QBuy.buy.product.eq(product))
            .orderBy(orderSpecifiers)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return PageableExecutionUtils.getPage(buys, pageable, buys::size);
    }

    @Override
    public Optional<Buy> findByProductIdAndPrice(Long productId, Long price) {
        Buy buy = queryFactory
            .selectFrom(QBuy.buy)
            .leftJoin(QBuy.buy.product, QProduct.product)
            .where(QBuy.buy.product.id.eq(productId), QBuy.buy.price.eq(price))
            .orderBy(QBuy.buy.createdAt.asc())
            .fetchFirst();
        return Optional.ofNullable(buy);
    }

    @Override
    public List<BuyCheckBidResponseDto> findAllBuyBidCoupon(User user) {
        return queryFactory.select(
                QBuy.buy.id,
                QBuy.buy.price,
                QBuy.buy.product.id,
                QBuy.buy.product.brand,
                QBuy.buy.product.name,
                QCoupon.coupon.id,
                QCoupon.coupon.name,
                QCoupon.coupon
            ).from(QBuy.buy)
            .leftJoin(QCoupon.coupon).on(QCoupon.coupon.id.eq(QBuy.buy.couponId))
            .where(QBuy.buy.user.eq(user))
            .fetch()
            .stream()
            .map(tuple -> BuyCheckBidResponseDto.builder()
                .buyId(tuple.get(QBuy.buy.id))
                .price(tuple.get(QBuy.buy.price))
                .productId(tuple.get(QBuy.buy.product.id))
                .productName(tuple.get(QBuy.buy.product.name))
                .productBrand(tuple.get(QBuy.buy.product.brand))
                .couponId(tuple.get(QCoupon.coupon.id))
                .couponName(tuple.get(QCoupon.coupon.name))
                .discountPrice(tuple.get(QBuy.buy.price))
                .coupon(tuple.get(QCoupon.coupon))
                .build()
            ).toList();
    }
}
