package indra.avitechcom.service;

import indra.avitechcom.entity.User;
import indra.avitechcom.mapper.UserMapper;
import indra.avitechcom.model.UserDTO;
import indra.avitechcom.repository.UserRepository;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Slf4j
public class CommandService {

    private static final UserMapper MAPPER = Mappers.getMapper(UserMapper.class);

    private static volatile CommandService INSTANCE;

    @Setter
    private UserRepository repository;

    public static CommandService getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new CommandService();
        }
        return INSTANCE;
    }

    private CommandService() {
        repository = UserRepository.getInstance();
    }

    public void save(UserDTO user) {
        log.info("Saving user: {}", user);

        User entity = MAPPER.map(user);

        repository.save(entity);
    }

    public void printAll() {
        log.info("Printing all users:");

        List<User> entities = repository.readAll();

        List<UserDTO> users = MAPPER.mapUserBOList(entities);

        printUsers(users);
    }

    public void deleteAll() {
        log.info("Deleting all users");

        repository.deleteAll();

        log.info("All users deleted");
    }

    private static void printUsers(List<UserDTO> users) {
        if (users.isEmpty()) {
            log.info(" - No users found");
        } else {
            for (int i = 0; i < users.size(); i++) {
                log.info(" {}: {}", (i + 1), users.get(i));
            }
        }
    }

    /**
     * For tests only
     */
    @Deprecated
    public static void setINSTANCE(CommandService testValue) {
        INSTANCE = testValue;
    }
}
