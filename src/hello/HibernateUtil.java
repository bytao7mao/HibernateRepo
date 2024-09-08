package hello;

import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class HibernateUtil {
    public static SessionFactory sessionFactory;
    static {
        try{
            sessionFactory = new Configuration()
                    .configure()
                    .buildSessionFactory();
        } catch (Throwable e){throw new ExceptionInInitializerError(e);}

    }
    public static SessionFactory getSessionFactory(){
        //alternatively, you could look up in JNDI here
        return sessionFactory;
    }
    public static void shutdown(){
        //close caches and connection pools
        getSessionFactory().close();
    }
}
