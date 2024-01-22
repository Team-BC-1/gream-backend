package bc1.gream.domain.payment.toss.entity;

import bc1.gream.domain.common.model.BaseEntity;
import bc1.gream.domain.user.entity.User;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
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
import lombok.Setter;

@Getter
@Entity
@Table(name = "toss_payment")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class TossPayment extends BaseEntity {

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY, cascade = CascadeType.PERSIST)
    @JoinColumn(name = "user")
    private User user;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @Enumerated(EnumType.STRING)
    private PayType payType;

    @Column
    private Long amount;

    @Column
    private Long orderId;

    @Column
    @Enumerated(EnumType.STRING)
    private OrderName orderName;

    @Column
    @Setter
    private String paymentKey;

    @Builder
    private TossPayment(User user, PayType payType, Long amount, Long orderId, OrderName orderName) {
        this.user = user;
        this.payType = payType;
        this.amount = amount;
        this.orderId = orderId;
        this.orderName = orderName;
    }
}