package indra.avitechcom.mapper;

import indra.avitechcom.entity.User;
import indra.avitechcom.model.UserBO;
import org.mapstruct.Builder;
import org.mapstruct.Mapper;

import java.util.List;


@Mapper(builder = @Builder(disableBuilder = true))
public interface UserMapper {

    UserBO map(User source);

    User map(UserBO source);

    List<UserBO> mapUserBOList(List<User> source);
}
