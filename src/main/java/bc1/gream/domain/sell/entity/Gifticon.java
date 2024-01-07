package bc1.gream.domain.sell.entity;

import bc1.gream.domain.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.OneToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_gifticon")
@Entity
public class Gifticon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "gifticon_url", nullable = false)
    private String gifticonImg;

    @OneToOne
    @JoinColumn(name = "sell_id", nullable = false)
    private Sell sell;

//    @OneToOne
//    @JoinColumn(name = "order_id")
//    private Order order;

    @Builder
    public Gifticon(String gifticonImg, Sell sell) {
        this.gifticonImg = gifticonImg;
        this.sell = sell;
//        this.order = order;
    }
}