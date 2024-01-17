package bc1.gream.domain.product.repository;


import static bc1.gream.domain.product.entity.QProduct.product;

import bc1.gream.domain.product.dto.unit.ProductCondition;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.helper.ProductQueryConditionFactory;
import bc1.gream.domain.product.repository.helper.ProductQueryOrderFactory;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQuery;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.support.PageableExecutionUtils;

//@Repository
@RequiredArgsConstructor
public class ProductRepositoryCustomImpl implements ProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> findAllBy(ProductCondition condition) {
        return queryFactory
            .selectFrom(product)
            .where(
                ProductQueryConditionFactory.brandEquals(condition.brand()),
                ProductQueryConditionFactory.nameEquals(condition.name()),
                ProductQueryConditionFactory.hasPriceRangeOf(condition.startPrice(), condition.endPrice())
            )
            .fetch();
    }

    @Override
    public Page<Product> findAllByPaging(ProductCondition condition, Pageable pageable) {
        // Get Order By Columns
        OrderSpecifier[] ordersOf = ProductQueryOrderFactory.getOrdersOf(pageable.getSort());

        // Query + Order + Paging
        List<Product> products = queryFactory
            .selectFrom(product)
            .where(
                ProductQueryConditionFactory.brandEquals(condition.brand()),
                ProductQueryConditionFactory.nameEquals(condition.name()),
                ProductQueryConditionFactory.hasPriceRangeOf(condition.startPrice(), condition.endPrice())
            )
            .orderBy(ordersOf)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();

        JPAQuery<Long> countQuery = queryFactory
            .select(product.count())
            .from(product)
            .where(
                ProductQueryConditionFactory.brandEquals(condition.brand()),
                ProductQueryConditionFactory.nameEquals(condition.name()),
                ProductQueryConditionFactory.hasPriceRangeOf(condition.startPrice(), condition.endPrice())
            )
            .orderBy(ordersOf);

        return PageableExecutionUtils.getPage(products, pageable, countQuery::fetchOne);
    }
}