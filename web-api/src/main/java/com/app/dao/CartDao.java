package com.app.dao;

import com.app.model.cart.CartModel;
import com.app.model.cart.CartViewModel;
import org.hibernate.HibernateException;
import org.hibernate.Query;
import org.hibernate.Session;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.util.List;

public class CartDao {


    public static CartModel getProductsInCart(Session hbrSession, String userId, Integer productId) throws HibernateException, ConstraintViolationException {
        Query q = hbrSession.createQuery( "From CartModel where userId = :userId and productId = :productId");
        q.setParameter("userId", userId);
        q.setParameter("productId", productId);

        CartModel cartItems = (CartModel)q.uniqueResult();  // can throw org.hibernate.NonUniqueResultException
        return cartItems;
    }

    public static List<CartModel> getProductsInCart(Session hbrSession, String userId) throws HibernateException, ConstraintViolationException {
        Query q = hbrSession.createQuery("From CartModel where userId = :userId");
        q.setParameter("userId", userId);
        return q.list();
    }


    public static int updateProductQuantityInCart(Session hbrSession, String userId, Integer productId, Long quantity)  throws HibernateException, ConstraintViolationException {
        Query q = hbrSession.createQuery("Update CartModel set quantity = :quantity where userId = :userId and productId = :productId");
        q.setParameter("userId", userId);
        q.setParameter("productId", productId);
        q.setParameter("quantity", quantity);
        return q.executeUpdate();
    }

    public static int removeFromCart(Session hbrSession, String userId, Integer productId)  throws HibernateException, ConstraintViolationException {
        String hql = "";
        if (productId != null){
            hql = "delete CartModel where userId = :userId and productId = :productId";
        }
        else{
            hql = "delete CartModel where userId = :userId";
        }
        Query q = hbrSession.createQuery(hql);
        q.setParameter("userId", userId);
        if (productId != null) {
            q.setParameter("productId", productId);
        }
        return q.executeUpdate();
    }

    public static List<CartViewModel> listCartItemsOfUser(Session hbrSession, String userId) throws HibernateException, ConstraintViolationException {
        Query q = hbrSession.createQuery("From CartViewModel where userId = :userId");
        q.setParameter("userId", userId);
        return q.list();
    }


}
