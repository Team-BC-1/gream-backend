package bc1.gream.domain.order.repository;

import bc1.gream.domain.order.entity.Sell;
import bc1.gream.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellRepository extends JpaRepository<Sell, Long>, SellRepositoryCustom {

    Optional<Sell> findByIdAndUser(Long sellId, User user);
}
