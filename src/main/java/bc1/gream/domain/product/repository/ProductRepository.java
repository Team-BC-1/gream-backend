package bc1.gream.domain.product.repository;

import bc1.gream.domain.product.entity.Product;
import java.util.List;
import org.springframework.data.repository.RepositoryDefinition;

@RepositoryDefinition(domainClass = Product.class, idClass = Long.class)
public interface ProductRepository extends ProductRepositoryCustom {

    List<Product> findAll();
}