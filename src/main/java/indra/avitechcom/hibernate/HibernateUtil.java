package indra.avitechcom.hibernate;

import lombok.extern.slf4j.Slf4j;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

@Slf4j
public class HibernateUtil {

	//Annotation based configuration
	private static SessionFactory sessionAnnotationFactory;
	
    private static SessionFactory buildSessionAnnotationFactory() {
    	try {
            // Create the SessionFactory from hibernate.cfg.xml
        	Configuration configuration = new Configuration();
        	configuration.configure("hibernate-annotation.cfg.xml");
        	log.debug("Hibernate Annotation Configuration loaded");
        	
        	ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
			log.debug("Hibernate Annotation serviceRegistry created");
        	
        	return configuration.buildSessionFactory(serviceRegistry);
        }
        catch (Throwable ex) {
            // Make sure you log the exception, as it might be swallowed
			log.error("Initial SessionFactory creation failed.", ex);
            throw new ExceptionInInitializerError(ex);
        }
	}

	public static SessionFactory getSessionAnnotationFactory() {
		if(sessionAnnotationFactory == null) {
			sessionAnnotationFactory = buildSessionAnnotationFactory();
		}
        return sessionAnnotationFactory;
    }
	
}
