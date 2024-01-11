package bc1.gream.domain.order.repository;

import bc1.gream.domain.order.entity.Sell;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SellRepository extends JpaRepository<Sell, Long> {

    @Query("select s from Sell s "
        + "where s.price = :price and s.product.id = :productId "
        + "order by s.createdAt asc "
        + "limit 1")
    Optional<Sell> findByProductIdAndPrice(Long productId, Long price);
}
