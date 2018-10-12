package com.app.dao;

import com.app.model.order.OrderModel;
import com.app.model.order.OrderWithNestedDetailModel;
import com.app.model.order.OrderWithNestedDetailResponse;
import com.app.util.HibernateUtil;
import org.apache.commons.lang3.StringUtils;
import org.hibernate.*;

import javax.validation.ConstraintViolationException;
import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class OrderDao {

    public static OrderModel getById(Session hbrSession, Integer orderId) {
        String hql = "from OrderModel where id = :orderId";
        Query q = hbrSession.createQuery(hql);
        q.setParameter("orderId", orderId);
        return  (OrderModel)q.uniqueResult();
    }


    private static String createSqlWhereString(Integer orderId, Integer customerId, String paymetType, String orderStatus){

        String sqlWhere = " where  1 = 1 ";

        if (orderId > 0)   { sqlWhere = sqlWhere + " and id = :orderId "; }
        if (customerId >0){ sqlWhere = sqlWhere + " and customer_id = :customerId "; }
        if (StringUtils.isNotBlank(paymetType)) { sqlWhere = sqlWhere + " and payment_type = :paymetType "; }
        if (StringUtils.isNotBlank(orderStatus)){ sqlWhere = sqlWhere + " and order_status = :orderStatus "; }

        return " from northwind.orders " + sqlWhere;

    }

    public static BigInteger getOrderCount(Session hbrSession, Integer orderId, Integer customerId, String paymetType, String orderStatus)  throws HibernateException{

        String sqlOrders = createSqlWhereString(orderId, customerId, paymetType, orderStatus);
        String countSql = "select count(*) " + sqlOrders ;
        SQLQuery q = HibernateUtil.getSession().createSQLQuery(countSql);
        if (orderId >0)   { q.setParameter("orderId", orderId); }
        if (customerId >0){ q.setParameter("customerId", customerId); }
        if (StringUtils.isNotBlank(paymetType)) { q.setParameter("paymetType", paymetType);   }
        if (StringUtils.isNotBlank(orderStatus)){ q.setParameter("orderStatus", orderStatus); }

        return (BigInteger)q.uniqueResult();
    }

    public static List<OrderWithNestedDetailModel> getWithOrderLines(Session hbrSession, int from, int limit, Integer orderId, Integer customerId, String paymetType, String orderStatus)  throws HibernateException, ConstraintViolationException {

        String sqlOrders = createSqlWhereString(orderId, customerId, paymetType, orderStatus);
        String sqlLimit = "";
        if ( limit <= 0 || limit > 1000 ){
            sqlLimit = " limit 1000 ";
        }
        else{
            sqlLimit = " limit " +  limit;
        }
        if (from <= 0) {
            from = 0;
        }
        else{
            from = (from - 1) * limit;
        }

        sqlLimit = sqlLimit + " offset " + from;


        String finalSql = "select order_id, product_id   , customer_id   , order_date, order_status  , shipped_date    , payment_type, paid_date, "
            + " ship_name            , ship_address1, ship_address2 , ship_city , ship_state    , ship_postal_code, ship_country, "
            + " product_code         , product_name , category      , quantity  , unit_price    , discount        , date_allocated, order_item_status, "
            + " shipping_fee         , customer_name, customer_email, customer_company "
            + " from northwind.order_details where order_id in ";

        finalSql = finalSql + "( select id " + sqlOrders + sqlLimit + ") order by order_id, product_id ";

        SQLQuery q = HibernateUtil.getSession().createSQLQuery(finalSql);
        if (orderId >0)   { q.setParameter("orderId", orderId); }
        if (customerId >0){ q.setParameter("customerId", customerId); }
        if (StringUtils.isNotBlank(paymetType)) { q.setParameter("paymetType", paymetType); }
        if (StringUtils.isNotBlank(orderStatus)){ q.setParameter("orderStatus", orderStatus); }

        q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP);
        List rowList = q.list();
        long prevOrderId = -1, newOrderId=0;
        BigDecimal orderTotal = new BigDecimal(0);


        List<OrderWithNestedDetailModel> orderDetailList = new ArrayList<>();
        OrderWithNestedDetailModel orderDetail = new OrderWithNestedDetailModel();

        for (Object object : rowList) {
            Map row =  (Map) object;
            newOrderId = (int) row.get("ORDER_ID");
            if (prevOrderId != newOrderId) {
                if (prevOrderId != -1){
                    orderDetail.setOrderTotal(orderTotal);
                }
                orderDetail = new OrderWithNestedDetailModel(
                    (int) row.get("ORDER_ID"),
                    (Date) row.get("ORDER_DATE"),
                    (String) row.get("ORDER_STATUS"),
                    (Date) row.get("SHIPPED_DATE"),
                    (String) row.get("SHIP_NAME"),
                    (String) row.get("SHIP_ADDRESS1"),
                    (String) row.get("SHIP_ADDRESS2"),
                    (String) row.get("SHIP_CITY"),
                    (String) row.get("SHIP_STATE"),
                    (String) row.get("SHIP_POSTAL_CODE"),
                    (String) row.get("SHIP_COUNTRY"),
                    (BigDecimal) row.get("SHIPPING_FEE"),
                    (Integer) row.get("CUSTOMER_ID"),
                    (String) row.get("CUSTOMER_NAME"),
                    (String) row.get("CUSTOMER_EMAIL"),
                    (String) row.get("COMPANY"),
                    (String) row.get("PAYMENT_TYPE"),
                    (Date) row.get("PAID_DATE")
                );
                orderDetail.addOrderLine(
                    (int) row.get("PRODUCT_ID"),
                    (String) row.get("PRODUCT_CODE"),
                    (String) row.get("PRODUCT_NAME"),
                    (String) row.get("CATEGORY"),
                    (BigDecimal) row.get("QUANTITY"),
                    (BigDecimal) row.get("UNIT_PRICE"),
                    (BigDecimal) row.get("DISCOUNT"),
                    (Date) row.get("DATE_ALLOCATED"),
                    (String) row.get("ORDER_ITEM_STATUS")
                );
                orderDetailList.add(orderDetail);
                prevOrderId = newOrderId;
                BigDecimal lineTotal = ((BigDecimal)row.get("UNIT_PRICE")).multiply((BigDecimal)row.get("QUANTITY")).subtract((BigDecimal)row.get("DISCOUNT"));
                orderTotal = orderTotal.add(lineTotal);
            }
            else {
                orderDetail.addOrderLine(
                    (int) row.get("PRODUCT_ID"),
                    (String) row.get("PRODUCT_CODE"),
                    (String) row.get("CATEGORY"),
                    (String) row.get("PRODUCT_NAME"),
                    (BigDecimal) row.get("QUANTITY"),
                    (BigDecimal) row.get("UNIT_PRICE"),
                    (BigDecimal) row.get("DISCOUNT"),
                    (Date) row.get("DATE_ALLOCATED"),
                    (String) row.get("ORDER_ITEM_STATUS")
                );
                BigDecimal lineTotal = ((BigDecimal)row.get("UNIT_PRICE")).multiply((BigDecimal)row.get("QUANTITY")).subtract((BigDecimal)row.get("DISCOUNT"));
                orderTotal = orderTotal.add(lineTotal);
            }
        }
        // Set the last ones order total;
        orderDetail.setOrderTotal(orderTotal);
        
        return orderDetailList;
    }


    public static int delete(Session hbrSession, Integer orderId)  throws HibernateException, ConstraintViolationException {
        String hql1 = "delete OrderItemModel where orderId = :orderId";
        String hql2 = "delete OrderModel where id = :orderId";

        Query q1 = hbrSession.createQuery(hql1).setParameter("orderId", orderId);
        Query q2 = hbrSession.createQuery(hql2).setParameter("orderId", orderId);

        int rowsEffected1 = q1.executeUpdate();
        int rowsEffected2 = q2.executeUpdate();

        return (rowsEffected1+rowsEffected2);
    }

    public static int deleteOrderLine(Session hbrSession, Integer orderId, Integer productId)  throws HibernateException, ConstraintViolationException {
        String hql = "delete OrderItemModel where orderId = :orderId and productId = :productId";

        Query q = hbrSession.createQuery(hql);
        q.setParameter("orderId"  , orderId);
        q.setParameter("productId", productId);

        int rowsEffected = q.executeUpdate();
        return rowsEffected;
    }


}
