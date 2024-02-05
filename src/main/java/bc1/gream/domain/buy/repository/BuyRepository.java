package bc1.gream.domain.buy.repository;

import bc1.gream.domain.buy.entity.Buy;
import java.time.LocalDateTime;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BuyRepository extends JpaRepository<Buy, Long>, BuyRepositoryCustom {

    @Query("select b from Buy b where b.id = ?1 and b.deadlineAt <= ?2")
    Optional<Buy> findByIdAndDeadlineAtLessThan(Long id, LocalDateTime deadlineAt);

}
