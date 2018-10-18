package com.app.dao;

import com.app.model.order.OrderWithNestedDetailModel;
import com.app.model.stats.CategoryCountModel;
import com.app.model.stats.DailyOrderCountModel;
import com.app.model.stats.DailySaleModel;
import com.app.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.math.BigInteger;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StatsDao {

    public static List<DailySaleModel> getDailySales(Session hbrSession) {
        String sql = "select sum( (unit_price * quantity) - discount) as sale_amount, sum(discount) as discount, order_date as date from  NORTHWIND.ORDER_DETAILS "
            + " where order_date > DATEADD(DAY, -100 , CURDATE()) "
            + " group by DAY_OF_YEAR (order_date) order by order_date desc limit 100";

        SQLQuery q = hbrSession.createSQLQuery(sql);
        List rowList = q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
        List<DailySaleModel> dailySaleList = new ArrayList<>();

        for (Object object : rowList) {
            Map row =  (Map) object;
            DailySaleModel dailyRow = new DailySaleModel((Date)row.get("DATE"), (BigDecimal)row.get("SALE_AMOUNT"), (BigDecimal)row.get("DISCOUNT"));
            dailySaleList.add(dailyRow);
        }
        return dailySaleList;
    }

    public static List<DailyOrderCountModel> getDailyOrderCount(Session hbrSession) {
        String sql = "select count(*) as order_count, order_date as date from  NORTHWIND.ORDER_DETAILS "
                + " where order_date > DATEADD(DAY, -100 , CURDATE()) "
                + " group by DAY_OF_YEAR (order_date) order by order_date desc limit 100";

        SQLQuery q = hbrSession.createSQLQuery(sql);
        List rowList = q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
        List<DailyOrderCountModel> dailyOrderCount = new ArrayList<>();

        for (Object object : rowList) {
            Map row =  (Map) object;
            DailyOrderCountModel dailyRow = new DailyOrderCountModel((Date)row.get("DATE"), (BigInteger)row.get("ORDER_COUNT"));
            dailyOrderCount.add(dailyRow);
        }
        return dailyOrderCount;
    }



    public static List<CategoryCountModel> getOrdersByPaymentType(Session hbrSession) {
        String sql = "select count(*) as count, payment_type as category from NORTHWIND.orders where order_date > DATEADD(DAY, -100 , CURDATE()) group by payment_type" ;

        SQLQuery q = hbrSession.createSQLQuery(sql);
        List rowList = q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
        List<CategoryCountModel> categoryCountList = new ArrayList<>();

        for (Object object : rowList) {
            Map row =  (Map) object;
            CategoryCountModel categoryAndCount = new CategoryCountModel((String)row.get("CATEGORY"), (BigInteger)row.get("COUNT"));
            categoryCountList.add(categoryAndCount);
        }
        return categoryCountList;
    }

    public static List<CategoryCountModel> getOrdersByStatus(Session hbrSession) {
        String sql = "select count(*) as count, order_status as category from NORTHWIND.orders where order_date > DATEADD(DAY, -100 , CURDATE()) group by order_status" ;

        SQLQuery q = hbrSession.createSQLQuery(sql);
        List rowList = q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
        List<CategoryCountModel> categoryCountList = new ArrayList<>();

        for (Object object : rowList) {
            Map row =  (Map) object;
            CategoryCountModel categoryAndCount = new CategoryCountModel((String)row.get("CATEGORY"), (BigInteger)row.get("COUNT"));
            categoryCountList.add(categoryAndCount);
        }
        return categoryCountList;
    }


}
