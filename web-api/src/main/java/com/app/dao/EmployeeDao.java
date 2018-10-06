package com.app.dao;

import com.app.model.customer.CustomerModel;
import com.app.model.employee.EmployeeModel;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;

public class EmployeeDao {


    public static EmployeeModel getById(Session hbrSession, Integer employeeId){
        String hql = "from EmployeeModel where id = :employeeId";
        Query q = hbrSession.createQuery(hql);
        q.setParameter("employeeId", employeeId);
        return  (EmployeeModel)q.uniqueResult();
    }

    public static int delete(Session hbrSession, Integer employeeId)  throws HibernateException, ConstraintViolationException {
        Query q = hbrSession.createQuery("delete EmployeeModel where id = :employeeId");
        q.setParameter("employeeId", employeeId);
        return q.executeUpdate();
    }


}
