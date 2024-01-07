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

    public final NumberPath<java.math.BigInteger> buyPrice = createNumber("buyPrice", java.math.BigInteger.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final BooleanPath isInstantTrade = createBoolean("isInstantTrade");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final bc1.gream.domain.product.entity.QProduct product;

    public final EnumPath<OrderState> state = createEnum("state", OrderState.class);

    public final DateTimePath<java.time.LocalDateTime> tradeDate = createDateTime("tradeDate", java.time.LocalDateTime.class);

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
        this.product = inits.isInitialized("product") ? new bc1.gream.domain.product.entity.QProduct(forProperty("product")) : null;
    }

}

