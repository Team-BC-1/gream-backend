package bc1.gream.domain.sell.service;

import bc1.gream.domain.sell.dto.request.SellNowRequestDto;
import bc1.gream.domain.sell.dto.response.SellNowResponseDto;
import bc1.gream.domain.user.entity.User;

public interface SellService {

    SellNowResponseDto sellNowProduct(User user, SellNowRequestDto requestDto, Long productId);
}
