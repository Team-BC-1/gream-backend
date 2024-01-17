package bc1.gream.domain.product.repository;

import static bc1.gream.domain.product.entity.QLikeProduct.likeProduct;
import static bc1.gream.domain.product.entity.QProduct.product;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.helper.ProductQueryOrderFactory;
import bc1.gream.domain.user.entity.User;
import com.querydsl.core.types.OrderSpecifier;
import com.querydsl.jpa.impl.JPAQueryFactory;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Pageable;

@RequiredArgsConstructor
public class LikeProductRepositoryCustomImpl implements LikeProductRepositoryCustom {

    private final JPAQueryFactory queryFactory;

    @Override
    public List<Product> findByUserID(User user, Pageable pageable) {
        OrderSpecifier[] ordersOf = ProductQueryOrderFactory.getOrdersOf(pageable.getSort());

        // Query + Order + Paging
        return queryFactory
            .select(product)
            .from(likeProduct)
            .leftJoin(likeProduct.product, product)
            .where(
                likeProduct.user.eq(user)
            )
            .orderBy(ordersOf)
            .offset(pageable.getOffset())
            .limit(pageable.getPageSize())
            .fetch();
    }
}
