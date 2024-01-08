package bc1.gream.domain.product.entity;

import static com.querydsl.core.types.PathMetadataFactory.*;

import com.querydsl.core.types.dsl.*;

import com.querydsl.core.types.PathMetadata;
import javax.annotation.processing.Generated;
import com.querydsl.core.types.Path;
import com.querydsl.core.types.dsl.PathInits;


/**
 * QProduct is a Querydsl query type for Product
 */
@Generated("com.querydsl.codegen.DefaultEntitySerializer")
public class QProduct extends EntityPathBase<Product> {

    private static final long serialVersionUID = 570104007L;

    public static final QProduct product = new QProduct("product");

    public final bc1.gream.domain.model.QBaseEntity _super = new bc1.gream.domain.model.QBaseEntity(this);

    public final StringPath brand = createString("brand");

    //inherited
    public final DateTimePath<java.time.LocalDateTime> createdAt = _super.createdAt;

    public final StringPath description = createString("description");

    public final NumberPath<Long> id = createNumber("id", Long.class);

    public final StringPath imageUrl = createString("imageUrl");

    public final ListPath<LikeProduct, QLikeProduct> likeProducts = this.<LikeProduct, QLikeProduct>createList("likeProducts", LikeProduct.class, QLikeProduct.class, PathInits.DIRECT2);

    //inherited
    public final DateTimePath<java.time.LocalDateTime> modifiedAt = _super.modifiedAt;

    public final StringPath name = createString("name");

    public final SetPath<bc1.gream.domain.order.entity.Order, bc1.gream.domain.order.entity.QOrder> orders = this.<bc1.gream.domain.order.entity.Order, bc1.gream.domain.order.entity.QOrder>createSet("orders", bc1.gream.domain.order.entity.Order.class, bc1.gream.domain.order.entity.QOrder.class, PathInits.DIRECT2);

    public final NumberPath<Long> price = createNumber("price", Long.class);

    public QProduct(String variable) {
        super(Product.class, forVariable(variable));
    }

    public QProduct(Path<? extends Product> path) {
        super(path.getType(), path.getMetadata());
    }

    public QProduct(PathMetadata metadata) {
        super(Product.class, metadata);
    }

}

