package com.mada.springdemo.dao;

import com.mada.springdemo.entity.hibernate.AddTime;
import com.mada.springdemo.utils.HibernateUtil;
import lombok.extern.log4j.Log4j2;
import org.hibernate.Query;
import org.hibernate.Session;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madali on 2017/4/27.
 */
@Log4j2
@Repository
public class AddTimeDao extends BaseDao {

    public List<AddTime> list() {
        List<AddTime> list = null;
        Session session = HibernateUtil.getSession();

        try {
            session.beginTransaction();
            Query query = session.createQuery("from AddTime");
            list = query.list();
            session.getTransaction().commit();
        } catch (Exception e) {
            session.getTransaction().rollback();
            log.error("Fail to list AddTime", e);
        } finally {
            if (list == null) {
                list = new ArrayList<>();
            }
            session.close();
        }

        return list;
    }
}
