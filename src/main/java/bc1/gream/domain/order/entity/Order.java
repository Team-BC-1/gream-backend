package bc1.gream.domain.order.entity;

import bc1.gream.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
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