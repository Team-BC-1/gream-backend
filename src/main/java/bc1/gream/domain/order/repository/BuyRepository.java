package bc1.gream.domain.order.repository;

import bc1.gream.domain.order.entity.Buy;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BuyRepository extends JpaRepository<Buy, Long>, BuyRepositoryCustom {

//    @Query("select b from Buy b "
//        + "where b.product.id = :productId and b.price = :price "
//        + "order by b.createdAt desc limit 1")
////        + "limit 1")
//    Optional<Buy> findByProductIdAndPrice(@Param("productId") Long productId, @Param("price") Long price);
}
