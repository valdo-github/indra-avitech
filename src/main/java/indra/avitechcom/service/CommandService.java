package indra.avitechcom.service;

import indra.avitechcom.entity.User;
import indra.avitechcom.hibernate.HibernateUtil;
import indra.avitechcom.mapper.UserMapper;
import indra.avitechcom.model.UserBO;
import indra.avitechcom.repository.UserRepository;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.mapstruct.factory.Mappers;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

@Slf4j
public class CommandService {

    private static final UserMapper mapper = Mappers.getMapper(UserMapper.class);

    private static volatile CommandService INSTANCE;

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

    public void save(UserBO user) {
        log.info("Saving user: {}", user);

        User entity = mapper.map(user);

        repository.save(entity);
    }

    public void printAll() {
        log.info("Printing all users:");

        List<User> entities = repository.readAll();

        List<UserBO> users = mapper.mapUserBOList(entities);

        for (int i = 0; i < users.size(); i++) {
            log.info(" {}: {}", (i + 1), users.get(i));
        }
    }

    public void deleteAll() {
        log.info("Deleting all users");

        repository.deleteAll();

        log.info("All users deleted");
    }

}
