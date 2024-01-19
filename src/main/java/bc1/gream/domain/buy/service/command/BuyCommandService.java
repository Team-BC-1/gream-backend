package bc1.gream.domain.buy.service.command;

import static bc1.gream.global.common.ResultCase.NOT_AUTHORIZED;

import bc1.gream.domain.buy.entity.Buy;
import bc1.gream.domain.buy.repository.BuyRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.exception.GlobalException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class BuyCommandService {

    private final BuyRepository buyRepository;

    public void deleteBuyByIdAndUser(Buy buy, User buyer) {
        if (!isBuyerLoggedInUser(buy, buyer)) {
            throw new GlobalException(NOT_AUTHORIZED);
        }

        buyRepository.delete(buy);
    }

    public boolean isBuyerLoggedInUser(Buy buy, User user) {
        return buy.getUser().getLoginId().equals(user.getLoginId());
    }

    @Transactional
    public void delete(Buy buy) {
        buyRepository.delete(buy);
    }

    @Transactional
    public void deleteBuysOfDeadlineBefore(LocalDateTime dateTime) {
        buyRepository.deleteBuysOfDeadlineBefore(dateTime);
    }
}