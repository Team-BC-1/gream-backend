package bc1.gream.domain.coupon.repository;

import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.user.entity.User;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface CouponRepository extends JpaRepository<Coupon, Long>,CouponRepositoryCustom {

}
