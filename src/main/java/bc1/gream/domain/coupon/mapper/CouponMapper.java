package bc1.gream.domain.coupon.mapper;

import bc1.gream.domain.admin.dto.response.AdminCreateCouponResponseDto;
import bc1.gream.domain.coupon.dto.response.CouponAvailableResponseDto;
import bc1.gream.domain.coupon.dto.response.CouponUnavailableResponseDto;
import bc1.gream.domain.coupon.entity.Coupon;
import org.mapstruct.Mapper;
import org.mapstruct.factory.Mappers;

@Mapper
public interface CouponMapper {

    CouponMapper INSTANCE = Mappers.getMapper(CouponMapper.class);

    CouponAvailableResponseDto toCouponListResponseDto(Coupon coupon);

    CouponUnavailableResponseDto toCouponUsedListResponseDto(Coupon coupon);

    AdminCreateCouponResponseDto toAdminCreateCouponResponseDto(Coupon coupon);
}
