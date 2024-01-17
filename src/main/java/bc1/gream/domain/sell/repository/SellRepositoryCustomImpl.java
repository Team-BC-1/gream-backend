package bc1.gream.domain.sell.repository;

import static bc1.gream.domain.order.entity.QGifticon.gifticon;
import static bc1.gream.domain.product.entity.QProduct.product;
import static bc1.gream.domain.sell.entity.QSell.sell;
import static bc1.gream.domain.user.entity.QUser.user;

import bc1.gream.domain.product.dto.response.SellPriceToQuantityResponseDto;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.entity.QProduct;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.repository.helper.SellQueryOrderFactory;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
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
public class SellRepositoryCustomImpl implements SellRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    /**
     * 상품에 대한 전체 판매입찰 조건조회,페이징 처리
     *
     * @param product  상품
     * @param pageable 페이징
     * @return 판매입찰
     */
    @Override
    public Page<Sell> findAllPricesOf(Product product, Pageable pageable) {
        // Get Orders By Columns
        OrderSpecifier[] orderSpecifiers = SellQueryOrderFactory.getOrdersOf(pageable.getSort());

        // Query + Order + Paging
        List<Sell> sells = queryFactory
            .selectFrom(sell)
            .leftJoin(sell.product, QProduct.product)
            .where(sell.product.eq(product))
            .orderBy(orderSpecifiers)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(sell.count())
            .from(sell)
            .leftJoin(sell.product, QProduct.product)
            .where(sell.product.eq(product))
            .orderBy(orderSpecifiers);

        return PageableExecutionUtils.getPage(sells, pageable, countQuery::fetchOne);
    }

    /**
     * 상품 id와 가격에 대해 가장 나중에 생성된 판매입찰 반환
     *
     * @param productId 상품 id
     * @param price     가격
     * @return 가장 나중에 생성된 판매입찰 반환
     */
    @Override
    public Optional<Sell> findByProductIdAndPrice(Long productId, Long price) {
        Sell foundSell = queryFactory.selectFrom(sell)
            .leftJoin(sell.product, product)
            .leftJoin(sell.gifticon, gifticon)
            .leftJoin(sell.user, user)
            .where(sell.product.id.eq(productId), sell.price.eq(price))
            .orderBy(sell.createdAt.asc())
            .fetchFirst();
        return Optional.ofNullable(foundSell);
    }

    /**
     * 상품에 대한 판매입찰가격수량에 대한 조건조회,페이징 처리. 기본정렬은 가격 오름차순
     *
     * @param product  상품
     * @param pageable 페이징
     * @return 판매입찰가격수량
     */
    @Override
    public Page<SellPriceToQuantityResponseDto> findAllPriceToQuantityOf(Product product, Pageable pageable) {
        // Get Orders By Columns
        OrderSpecifier[] orderSpecifiers = SellQueryOrderFactory.getOrdersOf(pageable.getSort());

        // Query + Order + Paging
        List<SellPriceToQuantityResponseDto> priceToQuantities = queryFactory
            .select(sell.price, sell.count())
            .from(sell)
            .leftJoin(sell.product, QProduct.product)
            .where(sell.product.eq(product))
            .groupBy(sell.price)
            .orderBy(orderSpecifiers)
            .fetch()
            .stream()
            .map(tuple -> SellPriceToQuantityResponseDto.builder()
                .sellPrice(tuple.get(sell.price))
                .quantity(tuple.get(sell.count()))
                .build()
            )
            .toList();

        JPAQuery<Long> countQuery = queryFactory
            .select(sell.count())
            .from(sell)
            .leftJoin(sell.product, QProduct.product)
            .where(sell.product.eq(product))
            .groupBy(sell.price)
            .orderBy(orderSpecifiers);

        return PageableExecutionUtils.getPage(priceToQuantities, pageable, countQuery::fetchOne);
    }

    @Override
    public void deleteSellsOfDeadlineBefore(LocalDateTime dateTime) {
        queryFactory
            .delete(sell)
            .where(sell.deadlineAt.before(dateTime))
            .execute();
    }
}
