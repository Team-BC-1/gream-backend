package bc1.gream.domain.user.entity;

import bc1.gream.domain.common.model.BaseEntity;
import bc1.gream.domain.order.entity.Order;
import bc1.gream.domain.product.entity.LikeProduct;
import bc1.gream.domain.product.entity.Product;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import lombok.AccessLevel;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;

@Getter
@Entity
@NoArgsConstructor(access = AccessLevel.PROTECTED)
@Table(name = "tb_user")
public class User extends BaseEntity {

    @JsonIgnore
    @OneToMany(mappedBy = "buyer", targetEntity = Order.class)
    private final Set purchasedOrders = new HashSet();

    @JsonIgnore
    @OneToMany(mappedBy = "seller", targetEntity = Order.class)
    private final Set saleOrders = new HashSet();

    @JsonIgnore
    @OneToMany(mappedBy = "user", targetEntity = LikeProduct.class, cascade = CascadeType.ALL, orphanRemoval = true)
    private final List<LikeProduct> likeProducts = new ArrayList<>();

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "login_id", unique = true, nullable = false, length = 20)
    private String loginId;

    @Column(name = "nickname", unique = true, nullable = false, length = 20)
    private String nickname;

    @Column(name = "password", nullable = false, length = 72) // BCrypt 암호화 시 72글자
    private String password;

    @Column(name = "point") //
    private Long point;

    @Column(name = "role", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private UserRole role;

    @Column(name = "provider", nullable = false, length = 30)
    @Enumerated(EnumType.STRING)
    private Provider provider;

    @Builder
    private User(String loginId, String nickname, String password, UserRole role, Provider provider) {
        this.loginId = loginId;
        this.nickname = nickname;
        this.password = password;
        this.role = role;
        this.provider = provider;
        this.point = 100000L;
    }

    public void addLikeProduct(Product product) {
        LikeProduct likeProduct = new LikeProduct(this, product);
        product.getLikeProducts().add(likeProduct);
        this.likeProducts.add(likeProduct);
    }

    public void removeLikeProduct(Product product) {
        LikeProduct likeProduct = this.likeProducts.stream()
            .filter(lp -> lp.getProduct().equals(product))
            .findAny().orElseThrow(() -> new GlobalException(ResultCase.PRODUCT_NOT_FOUND));
        this.likeProducts.remove(likeProduct);
        product.getLikeProducts().remove(likeProduct);
    }

    public void increasePoint(Long finalPrice) {
        this.point += finalPrice;
    }
}
