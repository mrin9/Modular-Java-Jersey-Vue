package com.app.dao;

import com.app.model.customer.CustomerModel;
import org.hibernate.*;
import javax.validation.ConstraintViolationException;

public class CustomerDao {

    public static CustomerModel getById(Session hbrSession, Integer customerId){
        String hql = "from CustomerModel where id = :customerId";
        Query q = hbrSession.createQuery(hql);
        q.setParameter("customerId", customerId);
        return  (CustomerModel)q.uniqueResult();
    }

    public static void delete(Session hbrSession, Integer customerId)  throws HibernateException, ConstraintViolationException {
        // Remove customer from all the related tables (do not change the sequence of deleteion else it may give a referal intigrity error)
        String sqlDeleteOrderItems = "delete from northwind.order_items where order_id in (select id from northwind.orders where customer_id = :customerId)";
        String sqlDeleteOrders   = "delete from northwind.orders where customer_id = :customerId" ;
        String sqlDeleteCart     = "delete from northwind.cart where user_id in (select user_id from northwind.users where customer_id = :customerId)";
        String sqlDeleteUser     = "delete from northwind.users where customer_id = :customerId";
        String sqlDeleteCustomer = "delete from northwind.customers where id = :customerId";

        Query queryDeleteOrderItems = hbrSession.createSQLQuery(sqlDeleteOrderItems);
        queryDeleteOrderItems.setParameter("customerId", customerId);

        Query queryDeleteOrders = hbrSession.createSQLQuery(sqlDeleteOrders);
        queryDeleteOrders.setParameter("customerId", customerId);

        Query queryDeleteCart = hbrSession.createSQLQuery(sqlDeleteCart);
        queryDeleteCart.setParameter("customerId", customerId);

        Query queryDeleteUser = hbrSession.createSQLQuery(sqlDeleteUser);
        queryDeleteUser.setParameter("customerId", customerId);

        Query queryDeleteCustomer = hbrSession.createSQLQuery(sqlDeleteCustomer);
        queryDeleteCustomer.setParameter("customerId", customerId);

        queryDeleteOrderItems.executeUpdate();
        queryDeleteOrders.executeUpdate();
        queryDeleteCart.executeUpdate();
        queryDeleteUser.executeUpdate();
        queryDeleteCustomer.executeUpdate();
    }
}
