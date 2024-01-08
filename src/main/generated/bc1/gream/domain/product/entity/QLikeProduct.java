package bc1.gream.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QLikeProduct is a Querydsl query type for LikeProduct
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QLikeProduct extends EntityPathBase<LikeProduct> {

    private static final long serialVersionUID = -1434063568L;

    private static final PathInits INITS = PathInits.DIRECT2;

    public static final QLikeProduct likeProduct = new QLikeProduct("likeProduct");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final QProduct product;

    public final bc1.gream.domain.user.entity.QUser user;

    public QLikeProduct(String variable) {
        this(LikeProduct.class, forVariable(variable), INITS);
    }

    public QLikeProduct(Path<? extends LikeProduct> path) {
        this(path.getType(), path.getMetadata(), PathInits.getFor(path.getMetadata(), INITS));
    }

    public QLikeProduct(PathMetadata metadata) {
        this(metadata, PathInits.getFor(metadata, INITS));
    }

    public QLikeProduct(PathMetadata metadata, PathInits inits) {
        this(LikeProduct.class, metadata, inits);
    }

    public QLikeProduct(Class<? extends LikeProduct> type, PathMetadata metadata, PathInits inits) {
        super(type, metadata, inits);
        this.product = inits.isInitialized("product") ? new QProduct(forProperty("product")) : null;
        this.user = inits.isInitialized("user") ? new bc1.gream.domain.user.entity.QUser(forProperty("user")) : null;
    }

}

