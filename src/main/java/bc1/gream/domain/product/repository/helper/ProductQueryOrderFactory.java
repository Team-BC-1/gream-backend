package bc1.gream.domain.product.repository.helper;


import com.querydsl.core.types.OrderSpecifier;
import org.springframework.data.domain.Sort;

public final class ProductQueryOrderFactory {

    public static OrderSpecifier[] getOrdersOf(Sort sort) {

//        List<OrderSpecifier> orders = new ArrayList<>();
//
//        if (!ObjectUtils.isEmpty(sort)) {
//            for (Sort.Order order : sort) {
//                Order direction = order.getDirection().isAscending() ? Order.ASC : Order.DESC;
//
//                switch (order.getProperty()) {
//                    case "id" -> {
//                        OrderSpecifier<?> orderId = QueryDslUtil.getSortedColumn(direction, product, "id");
//                        orders.add(orderId);
//                    }
//                    case "name" -> {
//                        OrderSpecifier<?> orderName = QueryDslUtil.getSortedColumn(direction, product, "name");
//                        orders.add(orderName);
//                    }
//                    case "brand" -> {
//                        OrderSpecifier<?> orderBrand = QueryDslUtil.getSortedColumn(direction, product, "brand");
//                        orders.add(orderBrand);
//                    }
//                    default -> {
//                    }
//                }
//            }
//        }
//        return orders.toArray(OrderSpecifier[]::new);
        return null;
    }
}
