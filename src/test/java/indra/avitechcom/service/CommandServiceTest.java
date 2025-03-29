package indra.avitechcom.service;

import indra.avitechcom.entity.User;
import indra.avitechcom.model.UserDTO;
import indra.avitechcom.repository.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoMoreInteractions;

@ExtendWith(MockitoExtension.class)
public class CommandServiceTest {

    private static final PodamFactory factory = new PodamFactoryImpl();

    private static UserRepository repository = Mockito.mock(UserRepository.class);

    @BeforeEach
    void before() {
        UserRepository.setINSTANCE(repository);
    }

    @Captor
    private ArgumentCaptor<User> userArgumentCaptor = ArgumentCaptor.forClass(User.class);

    @Test
    public void testSave() {

        UserDTO user = factory.manufacturePojo(UserDTO.class);

        CommandService.getInstance().save(user);

        verify(repository).save(userArgumentCaptor.capture());
        verifyNoMoreInteractions(repository);

        User result = userArgumentCaptor.getValue();
        assertThat(result).isNotNull();
        assertThat(result.getId()).isEqualTo(user.getId());
        assertThat(result.getGuid()).isEqualTo(user.getGuid());
        assertThat(result.getName()).isEqualTo(user.getName());
    }

    @Test
    public void testPrintAll() {

        CommandService.getInstance().printAll();

        verify(repository).readAll();
        verifyNoMoreInteractions(repository);
    }

    @Test
    public void testDeleteAll() {

        CommandService.getInstance().deleteAll();

        verify(repository).deleteAll();
        verifyNoMoreInteractions(repository);
    }

}
