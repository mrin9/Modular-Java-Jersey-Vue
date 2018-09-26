package com.app.dao;

import com.app.model.customer.CustomerModel;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;

public class CustomerDao {


    public static BigDecimal getReferenceCount(Session hbrSession, Integer customerId){
        String sql = "Select sum(cnt) from ("+
                "   select count(*) as cnt from northwind.users where customer_id = :customerId "+
                "   union "+
                "   select count(*) as cnt from northwind.orders where customer_id = :customerId "+
                " )";

        Query q = hbrSession.createSQLQuery(sql);
        q.setParameter("customerId", customerId);
        return (BigDecimal) q.uniqueResult();
    }

    public static CustomerModel getById(Session hbrSession, Integer customerId){
        String hql = "from CustomerModel where id = :customerId";
        Query q = hbrSession.createQuery(hql);
        q.setParameter("customerId", customerId);
        return  (CustomerModel)q.uniqueResult();
    }

    public static int delete(Session hbrSession, Integer customerId)  throws HibernateException, ConstraintViolationException {
        Query q = hbrSession.createQuery("delete CustomerModel where id = :customerId");
        q.setParameter("customerId", customerId);
        return q.executeUpdate();
    }


}
