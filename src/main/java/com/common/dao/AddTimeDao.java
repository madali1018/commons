package com.common.dao;

import com.common.model.AddTime;
import com.common.util.hibernate.HibernateUtil;
import org.hibernate.Query;
import org.hibernate.Session;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by madl on 2017/4/27.
 */
@Repository
public class AddTimeDao extends BaseDao {

    private static final Logger LOGGER = LoggerFactory.getLogger(AddTimeDao.class);

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
            LOGGER.error("Fail to list AddTime", e);
        } finally {
            if (list == null)
                list = new ArrayList<>();

            session.close();
        }

        return list;
    }
}
