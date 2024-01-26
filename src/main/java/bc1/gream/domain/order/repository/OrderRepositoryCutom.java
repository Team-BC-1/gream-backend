package bc1.gream.domain.order.repository;

import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.user.entity.User;
import java.util.List;

public interface OrderRepositoryCutom {

    List<Order> findAllBoughtBy(User user);
}
