package com.mada.commons.dao;

import com.mada.utils.hibernate.HibernateUtil;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.io.Serializable;

/**
 * Created by madali on 2017/4/27.
 */
@Log4j2
@Repository
public abstract class BaseDao {

    public void save(Object object) {
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            session.save(object);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.warn("Fail to save (Object: {}), e: ", object, e);
        } finally {
            session.close();
        }
    }

    public <T> T get(Class<T> cls, Serializable id) {
        T t = null;
        Session session = HibernateUtil.getSession();
        try {
            session.beginTransaction();
            t = (T) session.get(cls, id);
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.warn("Fail to get (T: {}), e: ", cls, e);
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
            log.warn("Fail to update (Object: {}), e: ", object, e);
        } finally {
            session.close();
        }
    }
}
