package bc1.gream.domain.product.entity;

import bc1.gream.domain.model.BaseEntity;
import bc1.gream.domain.order.entity.Order;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.math.BigInteger;
import java.util.HashSet;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.DynamicInsert;
import org.hibernate.annotations.DynamicUpdate;

@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_product")
@Entity
@Getter
@DynamicUpdate
@DynamicInsert
public class Product extends BaseEntity {

    @JsonIgnore
    @OneToMany(mappedBy = "product", targetEntity = Order.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private final Set<Order> orders = new HashSet<>();

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

    @Builder
    public Product(String brand, String name, String imageUrl, String description, BigInteger price) {
        this.brand = brand;
        this.name = name;
        this.imageUrl = imageUrl;
        this.description = description;
        this.price = price;
    }
}
