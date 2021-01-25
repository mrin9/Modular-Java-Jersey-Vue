package com.app.dao;

import com.app.model.user.UserViewModel;
import org.hibernate.*;

public class UserDao {

    public static UserViewModel getById(Session hbrSession, String userId){
        String hql = "from UserViewModel where userId = :userId";
        Query q = hbrSession.createQuery(hql);
        q.setParameter("userId", userId);
        return  (UserViewModel)q.uniqueResult();
    }

    public static void delete(Session hbrSession, UserViewModel user, boolean deleteRelatedData ){

       /* To cleanly remove an user
          1. First delete the order-items and order that belong to the associated customer
          2. Then delete the cart that belong to the user
          3. Then delete the user
          4. Finally delete the associated customer
        */

        String sqlDeleteOrderItems = "delete from northwind.order_items where order_id " +
                " in (select id from northwind.orders where customer_id " +
                "     in (select id from northwind.customers where id " +
                "         in (Select customer_id from northwind.users where user_id = :userId ) " +
                "   )" +
                ")";

        String sqlDeleteOrders = "delete from northwind.orders where customer_id " +
                " in (select id  from northwind.customers where id " +
                "     in (Select customer_id from northwind.users where user_id = :userId) " +
                ")";

        String sqlDeleteCart = "delete from northwind.cart where  user_id = :userId";
        String sqlUser = "delete from northwind.users where user_id = :userId";
        String sqlDeleteCustomer = "delete from northwind.customers where id = :customerId";
        String sqlDeleteEmployee = "delete from northwind.employees where id = :employeeId";

        Query queryDeleteOrderItems = hbrSession.createSQLQuery(sqlDeleteOrderItems);
        queryDeleteOrderItems.setParameter("userId", user.getUserId());

        Query queryDeleteOrders = hbrSession.createSQLQuery(sqlDeleteOrders);
        queryDeleteOrders.setParameter("userId", user.getUserId());

        Query queryDeleteCart = hbrSession.createSQLQuery(sqlDeleteCart);
        queryDeleteCart.setParameter("userId", user.getUserId());

        Query queryDeleteUser = hbrSession.createSQLQuery(sqlUser);
        queryDeleteUser.setParameter("userId", user.getUserId());

        Query queryDeleteCustomer = hbrSession.createSQLQuery(sqlDeleteCustomer);
        if (deleteRelatedData && user.getCustomerId() != null) {
            queryDeleteCustomer.setParameter("customerId", user.getCustomerId());
        }

        Query queryDeleteEmployee = hbrSession.createSQLQuery(sqlDeleteEmployee);
        if (deleteRelatedData && user.getEmployeeId() != null) {
            queryDeleteEmployee.setParameter("employeeId", user.getEmployeeId());
        }
        if (deleteRelatedData && user.getCustomerId() != null) {
            queryDeleteOrderItems.executeUpdate();
            queryDeleteOrders.executeUpdate();
        }
        queryDeleteCart.executeUpdate();
        queryDeleteUser.executeUpdate();

        // Delete associated customer
        if (deleteRelatedData && user.getCustomerId() != null) {
            queryDeleteCustomer.executeUpdate();
        }
        // Delete associated employee
        if (deleteRelatedData && user.getEmployeeId() != null) {
            queryDeleteEmployee.executeUpdate();
        }
    }
}
