package bc1.gream.domain.buy.repository;

import static bc1.gream.domain.buy.entity.QBuy.buy;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.entity.QBuy;
import bc1.gream.domain.buy.repository.helper.BuyQueryOrderFactory;
import bc1.gream.domain.product.dto.response.BuyPriceToQuantityResponseDto;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.entity.QProduct;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
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
     * 상품 id와 가격에 대해 가장 먼저 생성된 구매입찰 반환
     *
     * @param productId 상품 id
     * @param price     가격
     * @return 가장 먼저 생성된 구매입찰
     */
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

    /**
     * 상품에 대한 구매입찰가격수량에 대한 조건조회,페이징 처리. 기본정렬은 가격 내림차순
     *
     * @param product  상품
     * @param pageable 페이징
     * @return 구매입찰가격수량
     */
    @Override
    public Page<BuyPriceToQuantityResponseDto> findAllPriceToQuantityOf(Product product, Pageable pageable) {
        // Get Orders By Columns
        OrderSpecifier[] orderSpecifiers = BuyQueryOrderFactory.getOrdersOf(pageable.getSort());

        // Query + Order + Paging
        List<BuyPriceToQuantityResponseDto> buyPriceToQuantityResponseDtos = queryFactory
            .select(buy.price, buy.count())
            .from(buy)
            .where(buy.product.eq(product))
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
            .where(buy.product.eq(product))
            .groupBy(buy.price)
            .orderBy(orderSpecifiers);

        return PageableExecutionUtils.getPage(buyPriceToQuantityResponseDtos, pageable, countQuery::fetchOne);
    }
}
