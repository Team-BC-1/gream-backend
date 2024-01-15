package bc1.gream.domain.order.repository;

import bc1.gream.domain.order.entity.Sell;
import bc1.gream.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface SellRepository extends JpaRepository<Sell, Long>, SellRepositoryCustom {

    @Query("select s from Sell s "
        + "where s.price = :price and s.product.id = :productId "
        + "order by s.createdAt asc "
        + "limit 1")
    Optional<Sell> findByProductIdAndPrice(Long productId, Long price);

    Optional<Sell> findByIdAndUser(Long sellId, User user);
}
