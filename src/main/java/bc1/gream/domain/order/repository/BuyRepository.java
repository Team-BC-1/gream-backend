package bc1.gream.domain.order.repository;

import bc1.gream.domain.order.entity.Buy;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface BuyRepository extends JpaRepository<Buy, Long>, BuyRepositoryCustom {

    @Query("select b from Buy b "
        + "where b.product.id = :productId and b.price = :price "
        + "order by b.createdAt asc "
        + "limit 1")
    Optional<Buy> findByProductIdAndPrice(Long productId, Long price);
}
