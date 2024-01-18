package bc1.gream.domain.user.repository;

import bc1.gream.domain.user.entity.User;
import java.util.List;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

public interface UserRepository extends JpaRepository<User, Long> {

    Optional<User> findByLoginId(String loginId);

    boolean existsByLoginId(String loginId);

    boolean existsByNickname(String nickname);

    @Query("select lp.product.id from LikeProduct lp where lp.user = :user")
    List<Long> findAllLikeProductIdByUser(User user);
}
