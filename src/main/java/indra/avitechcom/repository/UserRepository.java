package indra.avitechcom.repository;

import indra.avitechcom.entity.User;
import indra.avitechcom.hibernate.HibernateUtil;
import indra.avitechcom.mapper.UserMapper;
import indra.avitechcom.model.UserBO;
import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.mapstruct.factory.Mappers;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class UserRepository {

    private static SessionFactory sessionFactory;
    private static UserMapper MAPPER;

    private UserRepository() {
        if (sessionFactory == null) {
            sessionFactory = HibernateUtil.getSessionAnnotationFactory();
        }
        MAPPER = Mappers.getMapper(UserMapper.class);
    }

    public static UserRepository getInstance() {
        return new UserRepository();
    }

    public void save(UserBO user) {

        User entity = MAPPER.map(user);

        inTx((tx) ->
                tx.save(entity));

        user.setId(entity.getId());
    }

    public UserBO read(int id) {

        final User result = inTx((session) ->
                (User) session.get(User.class, id));

        return MAPPER.map(result);
    }

    public List<UserBO> readAll() {

        final List<User> result = inTx((session) ->
                session.createCriteria(User.class).list());

        return MAPPER.mapUserBOList(result);
    }

    private <T> T inTx(Function<Session, T> tx) {
        Session session = sessionFactory.getCurrentSession();
        session.beginTransaction();

        T result = tx.apply(session);

        session.getTransaction().commit();

        return result;
    }

    /**
     * Terminate session factory, otherwise program won't end
     */
    public void closeSessionFactory() {
        if (!sessionFactory.isClosed()) {
            sessionFactory.close();
        }
    }
}
