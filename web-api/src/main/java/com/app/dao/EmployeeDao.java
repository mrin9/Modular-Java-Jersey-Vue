package com.app.dao;

import com.app.model.employee.EmployeeModel;
import org.hibernate.*;
import jakarta.validation.ConstraintViolationException;

public class EmployeeDao {
    public static EmployeeModel getById(Session hbrSession, Integer employeeId){
        String hql = "from EmployeeModel where id = :employeeId";
        Query q = hbrSession.createQuery(hql);
        q.setParameter("employeeId", employeeId);
        return  (EmployeeModel)q.uniqueResult();
    }

    public static void delete(Session hbrSession, Integer employeeId)  throws HibernateException, ConstraintViolationException {
        String sqlDeleteUser     = "delete from northwind.users where employee_id = :employeeId";
        String sqlDeleteEmployee = "delete from northwind.employees where id = :employeeId";

        Query queryDeleteUser = hbrSession.createSQLQuery(sqlDeleteUser);
        queryDeleteUser.setParameter("employeeId", employeeId);

        Query queryDeleteEmployee = hbrSession.createSQLQuery(sqlDeleteEmployee);
        queryDeleteEmployee.setParameter("employeeId", employeeId);

        queryDeleteUser.executeUpdate();
        queryDeleteEmployee.executeUpdate();
    }
}
