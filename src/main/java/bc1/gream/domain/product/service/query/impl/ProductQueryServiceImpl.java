package bc1.gream.domain.product.service.query.impl;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.product.service.query.ProductQueryService;
import bc1.gream.domain.product.service.query.unit.ProductCondition;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAllBy() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findAllBy(ProductCondition condition) {
        return null;
    }

    @Override
    public Page<Product> findAllByPaging(ProductCondition condition) {
        return null;
    }

    @Override
    public Product findBy(Long id) {
        return null;
    }
}
