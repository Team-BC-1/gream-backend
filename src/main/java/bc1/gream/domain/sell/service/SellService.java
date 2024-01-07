package bc1.gream.domain.sell.service;

import bc1.gream.domain.sell.dto.request.SellRequestDto;
import bc1.gream.domain.sell.dto.response.SellResponseDto;
import bc1.gream.domain.user.entity.User;

public interface SellService {

    SellResponseDto sellNowProduct(User user, SellRequestDto requestDto, Long productId);
}
