package bc1.gream.domain.order.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QOrder is a Querydsl query type for Order
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QOrder extends EntityPathBase<Order> {

    private static final long serialVersionUID = -1062913497L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QOrder order = new QOrder("order1");

    public final bc1.gream.domain.model.QBaseEntity _super = new bc1.gream.domain.model.QBaseEntity(this);

    public final bc1.gream.domain.user.entity.QUser buyer;

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<java.math.BigInteger> finalPrice = createNumber("finalPrice", java.math.BigInteger.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final DateTimePath<java.time.LocalDateTime> orderedAt = createDateTime("orderedAt", java.time.LocalDateTime.class);

    public final NumberPath<java.math.BigInteger> priceToSell = createNumber("priceToSell", java.math.BigInteger.class);

    public final bc1.gream.domain.product.entity.QProduct product;

    public final NumberPath<java.math.BigInteger> salePurchasedPrice = createNumber("salePurchasedPrice", java.math.BigInteger.class);

    public final bc1.gream.domain.user.entity.QUser seller;

    public QOrder(String variable) {
        this(Order.class, forVariable(variable), INITS);
    }

    public QOrder(Path<? extends Order> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QOrder(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QOrder(PathMetadata metadata, PathInits inits) {
        this(Order.class, metadata, inits);
    }

    public QOrder(Class<? extends Order> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.buyer = inits.isInitialized("buyer") ? new bc1.gream.domain.user.entity.QUser(forProperty("buyer")) : null;
        this.product = inits.isInitialized("product") ? new bc1.gream.domain.product.entity.QProduct(forProperty("product")) : null;
        this.seller = inits.isInitialized("seller") ? new bc1.gream.domain.user.entity.QUser(forProperty("seller")) : null;
    }

}

