package fashionmanager.park.develop.mapper;

import fashionmanager.park.develop.menu.DTO.BadgeDTO;
import fashionmanager.park.develop.menu.DTO.UserDTO;
import fashionmanager.park.develop.menu.Entity.Badge;
import fashionmanager.park.develop.menu.Entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserStructMapper {

    UserDTO toDto(User user);
    BadgeDTO toDto(Badge badge);

}