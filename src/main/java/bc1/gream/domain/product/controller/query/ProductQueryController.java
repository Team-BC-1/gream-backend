package bc1.gream.domain.product.controller.query;

import bc1.gream.domain.product.service.query.ProductQueryService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ProductQueryController {

    private final ProductQueryService productQueryService;

}
