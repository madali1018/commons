package com.common.dao;

import com.common.util.db.hibernate.HibernateUtil;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by madl on 2017/4/27.
 */
@Repository
public abstract class BaseDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(BaseDao.class);

    public void save(Object object) {

        Session session = HibernateUtil.getSession();

        try {
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOGGER.warn("Fail to save (Object: {}), e: ", object, e);
        } finally {
            session.close();
        }
    }

    public <T> T get(Class<T> cls, Serializable id ) {

        T t = null;
        Session session = HibernateUtil.getSession();

        try {
            session.beginTransaction();
            t = (T) session.get(cls, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOGGER.warn("Fail to get (T: {}), e: ", cls, e);
        } finally {
            session.close();
        }

        return t;
    }

    public void update(Object object) {

        Session session = HibernateUtil.getSession();

        try {
            session.beginTransaction();
            session.update(object);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            LOGGER.warn("Fail to update (Object: {}), e: ", object, e);
        } finally {
            session.close();
        }
    }
}
