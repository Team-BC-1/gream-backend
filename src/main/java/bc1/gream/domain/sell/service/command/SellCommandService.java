package bc1.gream.domain.sell.service.command;

import static bc1.gream.global.common.ResultCase.SELL_BID_PRODUCT_NOT_FOUND;

import bc1.gream.domain.sell.entity.Sell;
import bc1.gream.domain.sell.repository.SellRepository;
import bc1.gream.domain.user.entity.User;
import bc1.gream.global.exception.GlobalException;
import java.time.LocalDateTime;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Transactional
public class SellCommandService {

    private final SellRepository sellRepository;

    /**
     * 판매입찰아이디와 판매입찰자에 대한 판매입찰을 삭제, 삭제된 판매입찰 반환
     *
     * @param sellId 판매입찰아이디
     * @param seller 판매입찰자
     * @return 삭제된 판매입찰
     */
    public Sell deleteSellByIdAndUser(Long sellId, User seller) {
        Sell sell = sellRepository.findByIdAndUser(sellId, seller).orElseThrow(
            () -> new GlobalException(SELL_BID_PRODUCT_NOT_FOUND)
        );
        sellRepository.delete(sell);
        return sell;
    }

    public void delete(Sell sell) {
        sellRepository.delete(sell);
    }

    public void deleteSellsOfDeadlineBefore(LocalDateTime dateTime) {
        sellRepository.deleteSellsOfDeadlineBefore(dateTime);
    }
}
