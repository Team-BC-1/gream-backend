package bc1.gream.domain.buy.repository.helper;

import static bc1.gream.domain.buy.entity.QBuy.buy;

import bc1.gream.global.querydsl.QueryDslUtil;
import com.querydsl.core.types.Order;
import com.querydsl.core.types.OrderSpecifier;
import java.util.ArrayList;
import java.util.List;
import org.springframework.data.domain.Sort;
import org.springframework.util.ObjectUtils;

public final class BuyQueryOrderFactory {

    /**
     * 정렬기준에 따른 Buy에 대한 정렬배열, 기본은 가격 내림차순
     *
     * @param sort 정렬 기준
     * @return
     */
    public static OrderSpecifier[] getOrdersOf(Sort sort) {

        List<OrderSpecifier> orders = new ArrayList<>();

        if (sort.isEmpty()) {
            OrderSpecifier<?> orderCreatedAt = QueryDslUtil.getSortedColumn(Order.DESC, buy, "price");
            orders.add(orderCreatedAt);
        }

        if (!ObjectUtils.isEmpty(sort)) {
            for (Sort.Order order : sort) {
                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
                switch (order.getProperty()) {
                    case "id" -> {
                        OrderSpecifier<?> orderId = QueryDslUtil.getSortedColumn(direction, buy, "id");
                        orders.add(orderId);
                    }
                    case "price" -> {
                        OrderSpecifier<?> orderPrice = QueryDslUtil.getSortedColumn(direction, buy, "price");
                        orders.add(orderPrice);
                    }
                    case "createdAt" -> {
                        OrderSpecifier<?> orderCreatedAt = QueryDslUtil.getSortedColumn(direction, buy, "createdAt");
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
