package bc1.gream.domain.user.repository;

import bc1.gream.domain.user.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {
    
}
