package bc1.gream.domain.product.entity;

import bc1.gream.domain.model.BaseEntity;
import bc1.gream.domain.order.entity.Order;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.List;
import lombok.AccessLevel;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@NoArgsConstructor
@AllArgsConstructor(access = AccessLevel.PACKAGE)
@Entity(name = "tb_product")
@Getter
@Builder
@DynamicUpdate
@DynamicInsert
public class Product extends BaseEntity {

    @OneToMany(mappedBy = "product", targetEntity = Order.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<Order> orders = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column
    @NotNull
    private String brand;

    @Column
    @NotNull
    private String name;

    @Column
    @NotNull
    private String imageUrl;

    @Column
    @NotNull
    private String description;

    @Column
    @NotNull
    private BigInteger price;
}
