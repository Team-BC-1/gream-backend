package bc1.gream.domain.sell.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QSell is a Querydsl query type for Sell
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QSell extends EntityPathBase<Sell> {

    private static final long serialVersionUID = -778110683L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QSell sell = new QSell("sell");

    public final bc1.gream.domain.model.QBaseEntity _super = new bc1.gream.domain.model.QBaseEntity(this);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final DateTimePath<java.time.LocalDateTime> deadlineAt = createDateTime("deadlineAt", java.time.LocalDateTime.class);

    public final NumberPath<Long> id = createNumber("id", Long.class);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public final bc1.gream.domain.product.entity.QProduct product;

    public final bc1.gream.domain.user.entity.QUser user;

    public QSell(String variable) {
        this(Sell.class, forVariable(variable), INITS);
    }

    public QSell(Path<? extends Sell> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QSell(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QSell(PathMetadata metadata, PathInits inits) {
        this(Sell.class, metadata, inits);
    }

    public QSell(Class<? extends Sell> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new bc1.gream.domain.product.entity.QProduct(forProperty("product")) : null;
        this.user = inits.isInitialized("user") ? new bc1.gream.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

