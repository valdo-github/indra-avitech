package indra.avitechcom;

import indra.avitechcom.entity.User;
import indra.avitechcom.hibernate.HibernateUtil;
import indra.avitechcom.model.UserBO;
import indra.avitechcom.repository.UserRepository;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.UUID;

public class IntegrationTest {

    @Test
    public void happyCaseTest() {
        UserBO user = new UserBO();
        user.setName("David");
        user.setGuid(UUID.randomUUID());

        UserRepository userRepository = UserRepository.getInstance();

        userRepository.save(user);

        System.out.println("Employee ID="+user.getId());

        user = userRepository.read(user.getId());

        System.out.println("Employee ID="+user.getId());

        List<UserBO> users = userRepository.readAll();

        System.out.println("Employees="+users);

        userRepository.closeSessionFactory();
    }

}
