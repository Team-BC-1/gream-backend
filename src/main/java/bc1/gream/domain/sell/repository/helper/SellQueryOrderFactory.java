package bc1.gream.domain.sell.repository.helper;


import static bc1.gream.domain.sell.entity.QSell.sell;

import bc1.gream.domain.product.repository.helper.QueryDslUtil;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;

public final class SellQueryOrderFactory {

    public static OrderSpecifier[] getOrdersOf(Sort sort) {

        List<OrderSpecifier> orders = new ArrayList<>();

        if (!ObjectUtils.isEmpty(sort)) {
            for (Sort.Order order : sort) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;

                switch (order.getProperty()) {
                    case "id" -> {
                        OrderSpecifier<?> orderId = QueryDslUtil.getSortedColumn(direction, sell, "id");
                        orders.add(orderId);
                    }
                    case "price" -> {
                        OrderSpecifier<?> orderPrice = QueryDslUtil.getSortedColumn(direction, sell, "price");
                        orders.add(orderPrice);
                    }
                    case "createdAt" -> {
                        OrderSpecifier<?> orderCreatedAt = QueryDslUtil.getSortedColumn(direction, sell, "createdAt");
                        orders.add(orderCreatedAt);
                    }
                    default -> {
                    }
                }
            }
        }
        return orders.toArray(OrderSpecifier[]::new);
    }

}
