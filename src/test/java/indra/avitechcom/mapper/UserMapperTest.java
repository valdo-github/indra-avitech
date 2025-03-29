package indra.avitechcom.mapper;

import indra.avitechcom.entity.User;
import indra.avitechcom.model.UserDTO;
import org.junit.jupiter.api.Test;
import org.mapstruct.factory.Mappers;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.assertj.core.api.Assertions.assertThat;

public class UserMapperTest {

    private static final PodamFactory factory = new PodamFactoryImpl();

    private static final UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    @Test
    public void testUserDTOMapping() {

        User source = factory.manufacturePojo(User.class);

        UserDTO target = MAPPER.map(source);

        assertThat(target).isNotNull();
        assertThat(target.getId()).isEqualTo(source.getId());
        assertThat(target.getGuid()).isEqualTo(source.getGuid());
        assertThat(target.getName()).isEqualTo(source.getName());

    }

    @Test
    public void testUserMapping() {

        UserDTO source = factory.manufacturePojo(UserDTO.class);

        User target = MAPPER.map(source);

        assertThat(target).isNotNull();
        assertThat(target.getId()).isEqualTo(source.getId());
        assertThat(target.getGuid()).isEqualTo(source.getGuid());
        assertThat(target.getName()).isEqualTo(source.getName());

    }

}
