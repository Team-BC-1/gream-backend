package bc1.gream.domain.order.repository;

import bc1.gream.domain.order.entity.Gifticon;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;

public interface GifticonRepository extends JpaRepository<Gifticon, Long> {
    Optional<Gifticon> findBySell_Id(Long id);
}
