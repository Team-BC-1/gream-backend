package bc1.gream.domain.gifticon.entity;

import bc1.gream.domain.common.model.BaseEntity;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Lob;
import jakarta.persistence.Table;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@Table(name = "tb_gifticon")
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class Gifticon extends BaseEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Lob
    @Column(name = "gifticon_url")
    private String gifticonUrl;

    @Builder
    private Gifticon(String gifticonUrl) {
        this.gifticonUrl = gifticonUrl;
    }
}
