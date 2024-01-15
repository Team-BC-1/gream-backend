package bc1.gream.domain.order.repository;

import bc1.gream.domain.order.entity.Sell;
import bc1.gream.domain.user.entity.User;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface SellRepository extends JpaRepository<Sell, Long>, SellRepositoryCustom {

//    @Query(value = "select s from tb_sell s "
//        + "where s.price = :price and s.product.id = :productId "
//        + "order by s.createdAt desc "
//        + "limit 1", nativeQuery = true)
//    Optional<Sell> findByProductIdAndPrice(Long productId, Long price);

    Optional<Sell> findByIdAndUser(Long sellId, User user);
}
