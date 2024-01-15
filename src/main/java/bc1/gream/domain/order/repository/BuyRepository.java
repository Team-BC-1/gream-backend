package bc1.gream.domain.order.repository;

import bc1.gream.domain.order.entity.Buy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyRepository extends JpaRepository<Buy, Long>, BuyRepositoryCustom {

}
