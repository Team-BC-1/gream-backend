package bc1.gream.domain.order.repository;

import bc1.gream.domain.order.entity.Order;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Order.class, idClass = Long.class)
public interface OrderRepository {

}
