package bc1.gream.domain.buy.entity;

import bc1.gream.domain.common.model.BaseEntity;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tb_buy")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Buy extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    private Long price;

    @Column(name = "coupon_id")
    private Long couponId;

    @Column(name = "deadline_at")
    private LocalDateTime deadlineAt;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @Builder
    private Buy(Long price, LocalDateTime deadlineAt, Long couponId, User user, Product product) {
        this.price = price;
        this.couponId = couponId;
        this.deadlineAt = deadlineAt;
        this.user = user;
        this.product = product;
    }
}
