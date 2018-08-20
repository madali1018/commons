package com.common.util.hibernate;

import org.hibernate.FlushMode;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

/**
 * Created by madali on 2017/4/27.
 */
public class HibernateUtil {

    private static final SessionFactory SESSION_FACTORY;

    static {
        Configuration configuration = new Configuration().configure();
        ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
        SESSION_FACTORY =  configuration.buildSessionFactory(serviceRegistry);
    }

    public static Session getSession() {

        Session session = SESSION_FACTORY.openSession();

        //数据库事务提交时会刷新缓存
        session.setFlushMode(FlushMode.COMMIT);

        return session;
    }

}
