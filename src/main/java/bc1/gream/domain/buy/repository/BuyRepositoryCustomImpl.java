package bc1.gream.domain.buy.repository;


import bc1.gream.domain.buy.dto.response.BuyCheckBidResponseDto;
import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.entity.QBuy;
import bc1.gream.domain.buy.repository.helper.BuyQueryOrderFactory;
import bc1.gream.domain.coupon.entity.QCoupon;
import bc1.gream.domain.coupon.helper.CouponCalculator;
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
            .map(tuple -> {
                /** 매핑을 사용하고 싶었지만.. 혹시 방법이 있다면 알려주시면 감사하겠습니다..ㅠ
                 현재 Buy에 coupon이 연관관계가 없어서 Join으로 가져와도 Buy Entity 자체에 Coupon에 대한 정보는
                 Coupon의 id만 존재하는 상황입니다.. 현재 불가피하게 repository에서 로직을 처리했지만
                 뭔가 다른 방법이 있지 않을까 고민하고 있는 중입니다...
                 */
                Long discountPrice = tuple.get(QBuy.buy.price);
                if (tuple.get(QCoupon.coupon.id) != null) {
                    discountPrice = CouponCalculator.calculateDiscount(tuple.get(QCoupon.coupon), tuple.get(QBuy.buy.price));
                }

                return BuyCheckBidResponseDto.builder()
                    .buyId(tuple.get(QBuy.buy.id))
                    .price(tuple.get(QBuy.buy.price))
                    .productId(tuple.get(QBuy.buy.product.id))
                    .productName(tuple.get(QBuy.buy.product.name))
                    .productBrand(tuple.get(QBuy.buy.product.brand))
                    .couponId(tuple.get(QCoupon.coupon.id))
                    .couponName(tuple.get(QCoupon.coupon.name))
                    .discountPrice(discountPrice)
                    .build();
            })
            .toList();
    }
}
