package indra.avitechcom.service;

import indra.avitechcom.entity.User;
import indra.avitechcom.mapper.UserMapper;
import indra.avitechcom.model.UserDTO;
import indra.avitechcom.repository.UserRepository;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;

import java.util.List;

@Slf4j
public class CommandService {

    private static final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    public static volatile CommandService INSTANCE;

    private final UserRepository repository;

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

        User entity = mapper.map(user);

        repository.save(entity);
    }

    public void printAll() {
        log.info("Printing all users:");

        List<User> entities = repository.readAll();

        List<UserDTO> users = mapper.mapUserBOList(entities);

        if (users.isEmpty()) {
            log.info(" - No users found");
        } else {
            for (int i = 0; i < users.size(); i++) {
                log.info(" {}: {}", (i + 1), users.get(i));
            }
        }
    }

    public void deleteAll() {
        log.info("Deleting all users");

        repository.deleteAll();

        log.info("All users deleted");
    }

}
