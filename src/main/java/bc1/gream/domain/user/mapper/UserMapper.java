package bc1.gream.domain.user.mapper;

import bc1.gream.domain.user.dto.response.UserPointResponseDto;
import bc1.gream.domain.user.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.factory.Mappers;


@Mapper
public interface UserMapper {

    UserMapper INSTANCE = Mappers.getMapper(UserMapper.class);

    @Mapping(source = "point", target = "userPoint")
    UserPointResponseDto toUserPointResponseDto(User user);

}
