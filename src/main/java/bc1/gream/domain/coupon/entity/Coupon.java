package bc1.gream.domain.coupon.entity;

import bc1.gream.domain.common.model.BaseEntity;
import bc1.gream.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Entity
@Table(name = "tb_coupon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Getter
public class Coupon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "name", nullable = false)
    private String name;

    @Enumerated(value = EnumType.STRING)
    @Column(name = "discount_type", nullable = false)
    private DiscountType discountType;

    @Column(nullable = false)
    private Long discount;

    @Enumerated(value = EnumType.STRING)
    @Column
    private CouponStatus status;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id")
    private User user;

    @Builder
    private Coupon(String name, DiscountType discountType, Long discount, CouponStatus status, User user) {
        this.name = name;
        this.discountType = discountType;
        this.discount = discount;
        this.status = status;
        this.user = user;
    }

    public void changeStatus(CouponStatus status) {
        this.status = status;
    }
}
