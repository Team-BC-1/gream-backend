package bc1.gream.domain.user.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QUser is a Querydsl query type for User
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QUser extends EntityPathBase<User> {

    private static final long serialVersionUID = -389725033L;

    public static final QUser user = new QUser("user");

    public final bc1.gream.domain.model.QBaseEntity _super = new bc1.gream.domain.model.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final ListPath<bc1.gream.domain.product.entity.LikeProduct, bc1.gream.domain.product.entity.QLikeProduct> likeProducts = this.<bc1.gream.domain.product.entity.LikeProduct, bc1.gream.domain.product.entity.QLikeProduct>createList("likeProducts", bc1.gream.domain.product.entity.LikeProduct.class, bc1.gream.domain.product.entity.QLikeProduct.class, PathInits.DIRECT2);

    public final StringPath loginId = createString("loginId");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath nickname = createString("nickname");

    public final StringPath password = createString("password");

    public final EnumPath<Provider> provider = createEnum("provider", Provider.class);

    public final SetPath<bc1.gream.domain.order.entity.Order, bc1.gream.domain.order.entity.QOrder> purchasedOrders = this.<bc1.gream.domain.order.entity.Order, bc1.gream.domain.order.entity.QOrder>createSet("purchasedOrders", bc1.gream.domain.order.entity.Order.class, bc1.gream.domain.order.entity.QOrder.class, PathInits.DIRECT2);

    public final EnumPath<UserRole> role = createEnum("role", UserRole.class);

    public final SetPath<bc1.gream.domain.order.entity.Order, bc1.gream.domain.order.entity.QOrder> saleOrders = this.<bc1.gream.domain.order.entity.Order, bc1.gream.domain.order.entity.QOrder>createSet("saleOrders", bc1.gream.domain.order.entity.Order.class, bc1.gream.domain.order.entity.QOrder.class, PathInits.DIRECT2);

    public QUser(String variable) {
        super(User.class, forVariable(variable));
    }

    public QUser(Path<? extends User> path) {
        super(path.getType(), path.getMetadata());
    }

    public QUser(PathMetadata metadata) {
        super(User.class, metadata);
    }

}

