package com.app.dao;

import com.app.model.order.OrderWithNestedDetailModel;
import com.app.model.stats.DailySaleModel;
import com.app.util.HibernateUtil;
import org.hibernate.Criteria;
import org.hibernate.SQLQuery;
import org.hibernate.Session;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

public class StatsDao {

    public static List<DailySaleModel> getDailySales(Session hbrSession) {
        String sql = "select sum( (unit_price * quantity) - discount) as count, order_date as date from  NORTHWIND.ORDER_DETAILS "
            + " where order_date > DATEADD(DAY, -100 , CURDATE()) "
            + " group by DAY_OF_YEAR (order_date) ";

        SQLQuery q = HibernateUtil.getSession().createSQLQuery(sql);
        List rowList = q.setResultTransformer(Criteria.ALIAS_TO_ENTITY_MAP).list();
        List<DailySaleModel> dailySaleList = new ArrayList<>();

        for (Object object : rowList) {
            Map row =  (Map) object;
            DailySaleModel dailyRow = new DailySaleModel((Date) row.get("DATE"), (BigDecimal) row.get("COUNT"));
            dailySaleList.add(dailyRow);
        }
        return dailySaleList;
    }
}
