package indra.avitechcom.mapper;

import indra.avitechcom.entity.User;
import indra.avitechcom.model.UserDTO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(builder = @Builder(disableBuilder = true))
public interface UserMapper {

    UserDTO map(User source);

    User map(UserDTO source);

    List<UserDTO> mapUserBOList(List<User> source);
}
