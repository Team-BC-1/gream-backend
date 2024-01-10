package bc1.gream.domain.sell.repository;

import static bc1.gream.domain.sell.entity.QSell.sell;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.entity.QProduct;
import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.repository.helper.SellQueryOrderFactory;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
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
}
