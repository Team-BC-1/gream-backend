package bc1.gream.domain.buy.repository;

import static bc1.gream.domain.buy.entity.QBuy.buy;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.repository.helper.BuyQueryOrderFactory;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.entity.QProduct;
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
            .selectFrom(buy)
            .leftJoin(buy.product, QProduct.product)
            .where(buy.product.eq(product))
            .orderBy(orderSpecifiers)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        return PageableExecutionUtils.getPage(buys, pageable, buys::size);
    }

    @Override
    public Optional<Buy> findByProductIdAndPrice(Long productId, Long price) {
        Buy foundBuy = queryFactory
            .selectFrom(buy)
            .leftJoin(buy.product, QProduct.product)
            .where(buy.product.id.eq(productId), buy.price.eq(price))
            .orderBy(buy.createdAt.asc())
            .fetchFirst();
        return Optional.ofNullable(foundBuy);
    }
}
