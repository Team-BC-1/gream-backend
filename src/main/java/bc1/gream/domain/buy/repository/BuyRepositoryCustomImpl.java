package bc1.gream.domain.buy.repository;


import static bc1.gream.domain.buy.entity.QBuy.buy;
import static bc1.gream.domain.product.entity.QProduct.product;

import bc1.gream.domain.buy.dto.response.BuyCheckBidResponseDto;
import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.entity.QBuy;
import bc1.gream.domain.buy.repository.helper.BuyQueryOrderFactory;
import bc1.gream.domain.coupon.entity.QCoupon;
import bc1.gream.domain.product.dto.response.BuyPriceToQuantityResponseDto;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.entity.QProduct;
import bc1.gream.domain.user.entity.User;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import jakarta.persistence.LockModeType;
import java.time.LocalDateTime;
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

    /**
     * 상품에 대한 전체 구매입찰 조건조회,페이징 처리
     *
     * @param product  상품
     * @param pageable 페이징
     * @return 구매입찰
     */
    @Override
    public Page<Buy> findAllPricesOf(Product product, Pageable pageable) {
        // Get Orders By Columns
        OrderSpecifier[] orderSpecifiers = BuyQueryOrderFactory.getOrdersOf(pageable.getSort());

        // Query + Order + Paging
        List<Buy> buys = queryFactory
            .selectFrom(buy)
            .leftJoin(buy.product, QProduct.product)
            .where(buy.product.eq(product))
            .orderBy(orderSpecifiers)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(buy.count())
            .from(buy)
            .leftJoin(buy.product, QProduct.product)
            .where(buy.product.eq(product))
            .orderBy(orderSpecifiers);

        return PageableExecutionUtils.getPage(buys, pageable, countQuery::fetchOne);
    }

    /**
     * 상품 id와 가격에 대해 가장 먼저 생성된 구매입찰 반환. 마감기한이 현재시간 이후인지 확인
     *
     * @param productId 상품 id
     * @param price     가격
     * @return 가장 먼저 생성된 구매입찰
     */
    @Override
    public Optional<Buy> findByProductIdAndPrice(Long productId, Long price, LocalDateTime localDateTime) {
        Buy buy = queryFactory
            .selectFrom(QBuy.buy)
            .leftJoin(QBuy.buy.product, product)
            .where(QBuy.buy.product.id.eq(productId), QBuy.buy.price.eq(price), QBuy.buy.deadlineAt.gt(localDateTime))
            .orderBy(QBuy.buy.createdAt.asc())
            .setLockMode(LockModeType.PESSIMISTIC_WRITE)
            .fetchFirst();
        return Optional.ofNullable(buy);
    }

    /**
     * 상품에 대한 구매입찰가격수량에 대한 조건조회,페이징 처리. 마감기한이 현재시간 이후인지 비교. 기본정렬은 가격 내림차순
     *
     * @param product       상품
     * @param pageable      페이징
     * @param localDateTime 현재시간
     * @return 구매입찰가격수량
     */
    @Override
    public Page<BuyPriceToQuantityResponseDto> findAllPriceToQuantityOf(Product product, Pageable pageable, LocalDateTime localDateTime) {
        // Get Orders By Columns
        OrderSpecifier[] orderSpecifiers = BuyQueryOrderFactory.getOrdersOf(pageable.getSort());

        // Query + Order + Paging
        List<BuyPriceToQuantityResponseDto> buyPriceToQuantityResponseDtos = queryFactory
            .select(buy.price, buy.count())
            .from(buy)
            .leftJoin(buy.product, QProduct.product)
            .where(buy.product.eq(product), buy.deadlineAt.gt(localDateTime))
            .groupBy(buy.price)
            .orderBy(orderSpecifiers)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch()
            .stream()
            .map(tuple -> BuyPriceToQuantityResponseDto.builder()
                .buyPrice(tuple.get(buy.price))
                .quantity(tuple.get(buy.count()))
                .build()
            )
            .toList();

        JPAQuery<Long> countQuery = queryFactory
            .select(buy.count())
            .from(buy)
            .leftJoin(buy.product, QProduct.product)
            .where(buy.product.eq(product))
            .orderBy(orderSpecifiers);

        return PageableExecutionUtils.getPage(buyPriceToQuantityResponseDtos, pageable, countQuery::fetchOne);
    }

    @Override
    public void deleteBuysOfDeadlineBefore(LocalDateTime dateTime) {
        queryFactory
            .delete(buy)
            .where(buy.deadlineAt.before(dateTime))
            .execute();
    }

    /**
     * 마감기한이 현재시간 이후이고, 구매자의 현재 진행 중인 구매입찰정보에 대해 조회
     *
     * @param user          구매자
     * @param localDateTime 현재시간
     * @return 쿠폰을 포함한 구매입찰정보
     */
    @Override
    public List<BuyCheckBidResponseDto> findAllBuyBidCoupon(User user, LocalDateTime localDateTime) {
        return queryFactory.select(
                buy.id,
                buy.price,
                buy.product.id,
                buy.product.brand,
                buy.product.name,
                QCoupon.coupon.id,
                QCoupon.coupon.name,
                QCoupon.coupon
            ).from(buy)
            .leftJoin(buy.product, product)
            .leftJoin(QCoupon.coupon)
            .on(QCoupon.coupon.id.eq(buy.couponId))
            .where(buy.user.eq(user), buy.deadlineAt.gt(localDateTime))
            .fetch()
            .stream()
            .map(tuple -> BuyCheckBidResponseDto.builder()
                .buyId(tuple.get(buy.id))
                .price(tuple.get(buy.price))
                .productId(tuple.get(buy.product.id))
                .productName(tuple.get(buy.product.name))
                .productBrand(tuple.get(buy.product.brand))
                .couponId(tuple.get(QCoupon.coupon.id))
                .couponName(tuple.get(QCoupon.coupon.name))
                .discountPrice(tuple.get(buy.price))
                .coupon(tuple.get(QCoupon.coupon))
                .build()
            ).toList();
    }
}
