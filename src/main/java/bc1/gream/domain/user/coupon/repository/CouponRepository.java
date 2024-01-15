package bc1.gream.domain.user.coupon.repository;

import bc1.gream.domain.user.coupon.entity.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long> {

}
