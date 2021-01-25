package com.app.dao;

import com.app.model.product.ProductModel;
import org.hibernate.*;
import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;

public class ProductDao {

    public static BigDecimal getReferenceCount(Session hbrSession, Integer productId){
        String sql = "Select sum(cnt) from ("+
                "   select count(*) as cnt from northwind.cart where product_id = :productId "+
                "   union "+
                "   select count(*) as cnt from northwind.order_items where product_id = :productId "+
                " )";

        Query q = hbrSession.createSQLQuery(sql);
        q.setParameter("productId", productId);
        return (BigDecimal) q.uniqueResult();
    }

    public static ProductModel getById(Session hbrSession, Integer productId){
        String hql = "from ProductModel where id = :productId";
        Query q = hbrSession.createQuery(hql);
        q.setParameter("productId", productId);
        return  (ProductModel)q.uniqueResult();
    }

    public static int delete(Session hbrSession, Integer productId)  throws HibernateException, ConstraintViolationException {
        Query q = hbrSession.createQuery("delete ProductModel where id = :productId");
        q.setParameter("productId", productId);
        return q.executeUpdate();
    }
}
