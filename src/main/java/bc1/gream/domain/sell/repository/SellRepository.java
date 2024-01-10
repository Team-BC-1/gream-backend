package bc1.gream.domain.sell.repository;

import bc1.gream.domain.sell.entity.Sell;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellRepository extends JpaRepository<Sell, Long>, SellRepositoryCustom {

}
