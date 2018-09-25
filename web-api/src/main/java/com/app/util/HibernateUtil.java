package com.app.util;

import com.app.model.order.OrderItemModel;
import com.app.model.user.UserViewModel;
import org.hibernate.FlushMode;
import org.hibernate.SessionFactory;
//import org.hibernate.boot.Metadata;
//import org.hibernate.boot.MetadataSources;
import org.hibernate.boot.registry.StandardServiceRegistry;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry; // Hibernate 4

import com.app.model.user.User;
import com.app.model.customer.CustomerModel;
import com.app.model.employee.EmployeeModel;
import com.app.model.product.ProductModel;
import com.app.model.order.OrderModel;
import com.app.model.order.OrderInfoModel;


public class HibernateUtil {

    private static SessionFactory sessionFactory;
    private static StandardServiceRegistry registry;

    //Hibernate 4 Style
    public static SessionFactory getSessionFactory(){
        // setup the session factory
        if (sessionFactory == null) {
            Configuration configuration = new Configuration();

            //Connection Props
            configuration.setProperty("hibernate.dialect", "org.hibernate.dialect.H2Dialect");
            configuration.setProperty("hibernate.connection.driver_class", "org.h2.Driver");
            configuration.setProperty("hibernate.connection.url", "jdbc:h2:mem:test;");
            configuration.setProperty("hibernate.connection.release_mode", "auto");
            configuration.setProperty("hibernate.default_schema", "NORTHWIND");
            configuration.setProperty("hibernate.connection.username", "sa");
            configuration.setProperty("hibernate.connection.password", "sa");

            //Class Mappings
            configuration.addAnnotatedClass(User.class);
            configuration.addAnnotatedClass(UserViewModel.class);
            configuration.addAnnotatedClass(EmployeeModel.class);
            configuration.addAnnotatedClass(CustomerModel.class);
            configuration.addAnnotatedClass(ProductModel.class);
            configuration.addAnnotatedClass(OrderModel.class);
            configuration.addAnnotatedClass(OrderItemModel.class);
            configuration.addAnnotatedClass(OrderInfoModel.class);

            ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
            sessionFactory = configuration.buildSessionFactory(serviceRegistry);
        }
        return sessionFactory;
    }

    /*
    //Hibernate 5 Style
    public static SessionFactory getSessionFactory() {
        if (sessionFactory == null) {

            try {
                // Create registry ( DB Connection Properties from hibernate.cfg.bak)
                registry = new StandardServiceRegistryBuilder().configure().build();

                // Create MetadataSources and MetaData  ( Entity Class Mappings from hibernate.cfg.bak)
                MetadataSources sources = new MetadataSources(registry);
                Metadata metadata = sources.getMetadataBuilder().build();

                // Create SessionFactory
                sessionFactory = metadata.getSessionFactoryBuilder().build();

            }
            catch (Exception e) {
                e.printStackTrace();
                if (registry != null) {
                    StandardServiceRegistryBuilder.destroy(registry);
                }
            }
        }
        return sessionFactory;
    }
    */

    public static void shutdown() {
        if (registry != null) {
            StandardServiceRegistryBuilder.destroy(registry);
        }
    }

}