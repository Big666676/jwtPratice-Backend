package tw.pers.jwt.demo.converter;

import org.mapstruct.Mapper;
import tw.pers.jwt.demo.dto.UserLoginDto;
import tw.pers.jwt.demo.entity.UserDetail;

@Mapper(componentModel="spring")
public interface UserDtoConverter{
    
    UserDetail to(UserLoginDto userLoginDto);
}
