package bc1.gream.domain.order.repository;

import static bc1.gream.domain.order.entity.QGifticon.gifticon;
import static bc1.gream.domain.order.entity.QSell.sell;
import static bc1.gream.domain.product.entity.QProduct.product;
import static bc1.gream.domain.user.entity.QUser.user;

import bc1.gream.domain.order.entity.Sell;
import bc1.gream.domain.order.repository.helper.SellQueryOrderFactory;
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
public class SellRepositoryCustomImpl implements SellRepositoryCustom {

    private final JPAQueryFactory queryFactory;

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

        return PageableExecutionUtils.getPage(sells, pageable, sells::size);
    }

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
}
