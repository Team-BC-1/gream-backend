package bc1.gream.domain.coupon.mapper;

import bc1.gream.domain.coupon.dto.response.CouponListResponseDto;
import bc1.gream.domain.coupon.dto.response.CouponUsedListResponseDto;
import bc1.gream.domain.coupon.entity.Coupon;
import bc1.gream.domain.user.dto.response.UserPointResponseDto;
import bc1.gream.domain.user.entity.User;
import java.util.List;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CouponMapper {
    CouponMapper INSTANCE = Mappers.getMapper(CouponMapper.class);
    CouponListResponseDto toCouponListResponseDto(Coupon coupon);
    CouponUsedListResponseDto toCouponUsedListResponseDto(Coupon coupon);
}
