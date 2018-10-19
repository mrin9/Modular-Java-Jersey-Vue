package com.app;

import java.io.File;
import java.io.InputStream;
import java.sql.*;
import org.h2.tools.Server;
import org.slf4j.*;

public class DatabaseService {
    private static Logger log = LoggerFactory.getLogger(DatabaseService.class);
    //private static final java.util.logging.Logger log = java.util.logging.Logger.getLogger(DatabaseService.class.getName());

    private static final String DB_DRIVER = "org.h2.Driver";
    private static final String DB_CONNECTION = "jdbc:h2:mem:test;INIT=RUNSCRIPT FROM 'classpath:schema.sql'\\;runscript from 'classpath:data.sql'";
    private static final String DB_USER = "sa";
    private static final String DB_PASSWORD = "sa";
    private static Server dbWebServer = null;

    public static void initDB() {

        try {
            getDBConnection();
            dbWebServer = Server.createWebServer("-webAllowOthers","-webPort","9000").start();
        }
        catch (SQLException e) {
            e.printStackTrace();
        }
        log.info("\n\n *** Database Initiated  ***"+
            "\n DB Console : " + dbWebServer.getURL()  +
            "\n JDBC URL   : jdbc:h2:mem:test" +
            "\n User       : "+DB_USER +
            "\n Password   : "+DB_PASSWORD +
            "\n *** *** *** *** *** \n"
        );

    }

    private static Connection getDBConnection() {
        Connection dbConnection = null;
        try {
            Class.forName(DB_DRIVER);
        }
        catch (ClassNotFoundException e) {
            log.error(e.getMessage());
        }

        try {
            dbConnection = DriverManager.getConnection(DB_CONNECTION, DB_USER, DB_PASSWORD);
            return dbConnection;
        }
        catch (SQLException e) {
            log.error(e.getMessage());
        }

        return dbConnection;
    }

    public static File getSqlScriptResourceAsFile( String fileName){
        File file = new File(DatabaseService.class.getClassLoader().getResource(fileName).getFile());
        return file;
    }

    public static InputStream getSqlScriptResourceAsStream(String fileName){
        InputStream is = DatabaseService.class.getClassLoader().getResourceAsStream(fileName);
        return is;
    }


}