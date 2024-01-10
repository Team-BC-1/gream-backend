package bc1.gream.domain.product.repository.helper;


import static bc1.gream.domain.product.entity.QProduct.product;

import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;

public final class ProductQueryOrderFactory {

    public static OrderSpecifier[] getOrdersOf(Sort sort) {

        List<OrderSpecifier> orders = new ArrayList<>();

        if (!ObjectUtils.isEmpty(sort)) {
            for (Sort.Order order : sort) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty()) {
                    case "id" -> {
                        OrderSpecifier<?> orderId = QueryDslUtil.getSortedColumn(direction, product, "id");
                        orders.add(orderId);
                    }
                    case "name" -> {
                        OrderSpecifier<?> orderName = QueryDslUtil.getSortedColumn(direction, product, "name");
                        orders.add(orderName);
                    }
                    case "brand" -> {
                        OrderSpecifier<?> orderBrand = QueryDslUtil.getSortedColumn(direction, product, "brand");
                        orders.add(orderBrand);
                    }
                    default -> {
                    }
                }
            }
        }
        return orders.toArray(OrderSpecifier[]::new);
    }
}
