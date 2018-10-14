package com.app;

import java.net.URISyntaxException;
import java.nio.file.Files;
import java.nio.file.Path;

import com.app.task.RefreshDBTask;
import com.github.lalyos.jfiglet.FigletFont;
import org.apache.catalina.WebResourceRoot;
import org.apache.catalina.WebResourceSet;
import org.apache.catalina.core.StandardContext;
import org.apache.catalina.startup.Tomcat;
import org.apache.catalina.webresources.DirResourceSet;
import org.apache.catalina.webresources.EmptyResourceSet;
import org.apache.catalina.webresources.StandardRoot;
import org.apache.tomcat.util.scan.StandardJarScanner;
import org.slf4j.*;
import org.hibernate.SessionFactory;

import java.io.*;
import java.util.List;
import java.util.concurrent.*;


public class TomcatStarter {
    private static Logger log = LoggerFactory.getLogger(TomcatStarter.class);

    public static int port=8080;
    private static Tomcat tomcat;

    private static File getRootFolder() {
        try {
            File root;
            String runningJarPath = TomcatStarter.class.getProtectionDomain().getCodeSource().getLocation().toURI().getPath().replaceAll("\\\\", "/");
            int lastIndexOf = runningJarPath.lastIndexOf("/target/");
            if (lastIndexOf < 0) {
                root = new File("");
            }
            else {
                root = new File(runningJarPath.substring(0, lastIndexOf));
            }
            //System.out.println("application resolved root folder: " + root.getAbsolutePath());
            return root;
        } catch (URISyntaxException ex) {
            throw new RuntimeException(ex);
        }
    }

    public static void main(String[] args) throws Exception {
        log.info("\n\n\nStarting Tomcat...\n ");
        File root = getRootFolder();
        System.setProperty("org.apache.catalina.startup.EXIT_ON_INIT_FAILURE", "true");
        System.setProperty("java.util.logging.SimpleFormatter.format", "%1$tF %1$tT %4$s %2$s %5$s%6$s%n");
        System.setProperty("java.util.logging.ConsoleHandler.level", "WARNING");
        System.setProperty("java.util.logging.FileHandler.level", "WARNING");

        tomcat = new Tomcat();
        Path tempPath = Files.createTempDirectory("tomcat-base-dir");
        tomcat.setBaseDir(tempPath.toString());

        File webContentFolder = new File(root.getAbsolutePath(), "src/main/webapp/");
        if (!webContentFolder.exists()) {
            webContentFolder = Files.createTempDirectory("default-doc-base").toFile();
        }
        StandardContext ctx = (StandardContext) tomcat.addWebapp("", webContentFolder.getAbsolutePath());

        //Disable Jar Scanner
        StandardJarScanner scanner=new StandardJarScanner();
        scanner.setScanClassPath(false);
        scanner.setScanManifest(false);
        scanner.setScanAllDirectories(false);
        scanner.setScanAllFiles(false);

        ctx.setJarScanner(scanner);

        // Declare an alternative location for your "WEB-INF/classes" dir
        // Servlet 3.0 annotation will work
        File additionWebInfClassesFolder = new File(root.getAbsolutePath(), "target/classes");
        WebResourceRoot resources = new StandardRoot(ctx);

        WebResourceSet resourceSet;
        if (additionWebInfClassesFolder.exists()) {
            resourceSet = new DirResourceSet(resources, "/WEB-INF/classes", additionWebInfClassesFolder.getAbsolutePath(), "/");
            //System.out.println("loading WEB-INF resources from as '" + additionWebInfClassesFolder.getAbsolutePath() + "'");
        }
        else {
            resourceSet = new EmptyResourceSet(resources);
        }
        resources.addPreResources(resourceSet);
        ctx.setResources(resources);

        // ASCII ART Banner...
        String asciiArt = FigletFont.convertOneLine("Mrin >>>") + " Version 1.0.0";
        log.info(asciiArt);


        log.info("\n\n *** System Variables ***");
        log.info(" user.home     :" + System.getProperty("user.home"));
        log.info(" user.dir      :" + System.getProperty("user.dir"));
        log.info(" catalina.home :" + System.getProperty("catalina.home"));
        log.info(" catalina.base :" + System.getProperty("catalina.base") +"\n *** *** *** *** *** \n");


        //Schedule Refresh DB Task
        ScheduledExecutorService scheduledThreadPool = Executors.newScheduledThreadPool(1);
        RefreshDBTask refreshDBTask = new RefreshDBTask(); //DatabaseService.initDB();
        long period = 1;
        TimeUnit timeUnit = TimeUnit.HOURS;
        Future futureTask = scheduledThreadPool.scheduleAtFixedRate(refreshDBTask, 0, period, timeUnit);
        log.info(String.format("\n\nRefreshDB Task Scheduled (The task refreshes the Database every %s %s)", period, timeUnit.toString()));





        //Start Web API Server
        tomcat.setPort(port);
        tomcat.start();
        tomcat.getConnector(); // Trigger the creation of the default connector

        tomcat.getServer().await();

    }

    
}
