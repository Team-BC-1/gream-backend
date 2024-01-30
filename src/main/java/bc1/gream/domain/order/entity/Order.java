package bc1.gream.domain.order.entity;


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
import jakarta.validation.constraints.NotNull;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Entity
@Table(name = "tb_order")
@Getter
@DynamicUpdate
@DynamicInsert
public class Order extends BaseEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private Product product;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private User buyer;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    private User seller;

    @OneToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "gifticon_id", unique = true)
    private Gifticon gifticon;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private Long finalPrice; // 최종거래가격

    @Column
    @NotNull
    private Long expectedPrice; // 구매희망가격

    @Builder
    private Order(Product product, User buyer, User seller, Long finalPrice, Long expectedPrice, Gifticon gifticon) {
        this.product = product;
        this.buyer = buyer;
        this.seller = seller;
        this.finalPrice = finalPrice;
        this.expectedPrice = expectedPrice;
        this.gifticon = gifticon;
    }
}