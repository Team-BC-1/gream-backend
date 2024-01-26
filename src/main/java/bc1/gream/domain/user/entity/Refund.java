package bc1.gream.domain.user.entity;

import bc1.gream.domain.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_refund")
public class Refund extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private Long point;

    @Column(nullable = false)
    private String bank;

    @Column(nullable = false)
    private String accountNumber;

    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    @Builder
    private Refund(Long point, String bank, String accountNumber, User user) {
        this.point = point;
        this.bank = bank;
        this.accountNumber = accountNumber;
        this.user = user;
    }
}
