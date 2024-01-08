package bc1.gream.domain.order.entity;

import bc1.gream.domain.model.BaseEntity;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDateTime;
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
    @ManyToOne(fetch = FetchType.LAZY)
    private User buyer;
    
    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User seller;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private BigInteger priceToSell;

    @Column
    @NotNull
    private BigInteger finalPrice;

    @Column
    @NotNull
    private BigInteger salePurchasedPrice;

    private LocalDateTime orderedAt;

    @Builder
    private Order(BigInteger priceToSell, BigInteger finalPrice, BigInteger salePurchasedPrice, LocalDateTime orderedAt) {
        this.priceToSell = priceToSell;
        this.finalPrice = finalPrice;
        this.salePurchasedPrice = salePurchasedPrice;
        this.orderedAt = orderedAt;
    }
}