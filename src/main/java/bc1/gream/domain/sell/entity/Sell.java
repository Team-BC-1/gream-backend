package bc1.gream.domain.sell.entity;

import bc1.gream.domain.common.model.BaseEntity;
import bc1.gream.domain.gifticon.entity.Gifticon;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
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
@Table(name = "tb_sell")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Sell extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "price", nullable = false)
    private Long price;

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
    @JsonIgnore
    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "gifticon_id", nullable = false)
    private Gifticon gifticon;

    @Builder
    private Sell(Long price, LocalDateTime deadlineAt, User user, Product product, Gifticon gifticon) {
        this.price = price;
        this.deadlineAt = deadlineAt;
        this.user = user;
        this.product = product;
        this.gifticon = gifticon;
    }
}
