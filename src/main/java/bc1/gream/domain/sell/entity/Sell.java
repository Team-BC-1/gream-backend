package bc1.gream.domain.sell.entity;

import bc1.gream.domain.model.BaseEntity;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.user.entity.User;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_sell")
public class Sell extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price", nullable = false)
    private Long price;

    @Column(name = "is_close", nullable = false)
    private Boolean isClose;

    @Column(name = "deadline")
    private LocalDateTime deadlineAt;

    @Column(name = "is_now", nullable = false)
    private Boolean isNow;

    @Column(name = "payment_type", nullable = false)
    private String paymentType;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "product_id", nullable = false)
    private Product product;

    @OneToOne(mappedBy = "sell")
    private Gifticon gifticon;

    @Builder
    public Sell(Long price, Boolean isClose, LocalDateTime deadlineAt, Boolean isNow, String paymentType, User user, Product product) {
        this.price = price;
        this.isClose = isClose;
        this.deadlineAt = deadlineAt;
        this.isNow = isNow;
        this.paymentType = paymentType;
        this.user = user;
        this.product = product;
    }
}
