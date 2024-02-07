package bc1.gream.domain.sell.repository;

import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.user.entity.User;
import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellRepository extends JpaRepository<Sell, Long>, SellRepositoryCustom {

    Optional<Sell> findByIdAndUser(Long sellId, User user);

    List<Sell> findAllByUserAndDeadlineAtGreaterThan(User seller, LocalDateTime localDateTime);
}
