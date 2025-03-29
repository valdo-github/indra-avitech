package indra.avitechcom.repository;

import indra.avitechcom.entity.User;
import indra.avitechcom.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;

public class UserRepository {

    private static volatile UserRepository INSTANCE;

    private static final ThreadLocal<Session> threadLocalSession = new ThreadLocal<>();

    private static final SessionFactory sessionFactory = HibernateUtil.getSessionAnnotationFactory();

    public static UserRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserRepository();
        }
        return INSTANCE;
    }

    public void save(User entity) {

        inTxVoid((session) ->
            session.save(entity));
    }

    public User read(int id) {

        return inTxReturn((session) ->
                (User) session.get(User.class, id));
    }

    public List<User> readAll() {

        return inTxReturn((session) ->
                session.createCriteria(User.class).list()
        );
    }

    public void deleteAll() {

        inTxVoid((session) -> {
            List<User> entities = session.createCriteria(User.class).list();
            for (User entity : entities) {
                session.delete(entity);
            }
        });
    }

    private <T> T inTxReturn(Function<Session, T> tx) {
        Session session = getSession();
        session.beginTransaction();

        T result = tx.apply(session);

        session.flush();
        session.getTransaction().commit();

        return result;
    }

    private void inTxVoid(Consumer<Session> tx) {
        Session session = getSession();
        session.beginTransaction();

        tx.accept(session);

        session.flush();
        session.getTransaction().commit();
    }

    public static Session getSession() {
        Session session = threadLocalSession.get();
        if (session == null || !session.isOpen()) {
            session = sessionFactory.openSession();
            threadLocalSession.set(session);
        }
        return session;
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
