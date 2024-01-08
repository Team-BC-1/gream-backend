package bc1.gream.domain.product.service.query.impl;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import bc1.gream.domain.product.service.query.ProductQueryService;
import bc1.gream.domain.product.service.query.unit.ProductCondition;
import bc1.gream.global.common.ResultCase;
import bc1.gream.global.exception.GlobalException;
import java.util.List;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductQueryServiceImpl implements ProductQueryService {

    private final ProductRepository productRepository;

    @Override
    public List<Product> findAll() {
        return productRepository.findAll();
    }

    @Override
    public List<Product> findAllBy(ProductCondition condition) {
        return productRepository.findAllBy(condition);
    }

    @Override
    public Page<Product> findAllByPaging(ProductCondition condition, Pageable pageable) {
        return productRepository.findAllByPaging(condition, pageable);
    }

    @Override
    public Product findBy(Long id) {
        return productRepository.findById(id)
            .orElseThrow(() -> new GlobalException(ResultCase.PRODUCT_NOT_FOUND));
    }
}