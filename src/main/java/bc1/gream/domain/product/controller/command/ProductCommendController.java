package bc1.gream.domain.product.controller.command;

import bc1.gream.domain.product.service.command.ProductCommendService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Controller;

@Controller
@RequiredArgsConstructor
public class ProductCommendController {

    private final ProductCommendService productCommendService;
}
