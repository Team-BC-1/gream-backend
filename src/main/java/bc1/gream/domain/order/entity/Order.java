package bc1.gream.domain.order.entity;

import bc1.gream.domain.model.BaseEntity;
import bc1.gream.domain.product.entity.Product;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import java.time.LocalDateTime;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Entity
@Table(name = "tb_order")
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
public class Order extends BaseEntity {

//    @ManyToOne
//    private 판매입찰 판매입찰;

//    @ManyToOne
//    private 구매입찰 구매입찰;

//    @OneToOne
//    @JoinColumn(name = "coupon_id")
//    private Coupon coupon;

    @ManyToOne
    @JoinColumn(name = "product_id")
    private Product product;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    @NotNull
    private OrderState state;

    @Column
    @NotNull
    private BigInteger buyPrice;

    private LocalDateTime tradeDate;

    private Boolean isInstantTrade;
}