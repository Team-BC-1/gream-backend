package bc1.gream.domain.product.service;

import bc1.gream.domain.product.entity.Product;
import bc1.gream.domain.product.repository.ProductRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ProductService {

    private final ProductRepository productRepository;

    public void createProduct() {
        Product product = new Product();

        productRepository.save(product);
    }
}
