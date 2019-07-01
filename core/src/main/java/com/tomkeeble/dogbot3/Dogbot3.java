package com.tomkeeble.dogbot3;

import com.rabbitmq.client.ConnectionFactory;

import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.EJB;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.Persistence;

@Singleton
@Startup
public class Dogbot3 {

    private static Logger logger = Logger.getLogger(Dogbot3.class);


    public static MessageProvider getMessageProvider() {
        return messageProvider;
    }

    private static MessageProvider messageProvider;

    public static EntityManager getEntityManager() {
        return entityManager;
    }

    private static EntityManager entityManager;

    private void connectMQ(){
        ConnectionFactory factory = new ConnectionFactory();
    }

//    public Dogbot3() {
//        entityManager = null;
//        messageProvider = new FacebookMessageProvider();
//    }

    @PostConstruct
    public void start(){
        System.out.print("Hello World");
        logger.info("Dogbot3");
        logger.info("Starting Dogbot3...");

//        Person p = new Person();
//        p.setName("James");
//        getEntityManager().getTransaction().begin();
//        getEntityManager().persist(p);
//        getEntityManager().getTransaction().commit();
//        logger.info("added test person");
        Worker w = new Worker();
        try {
            w.work();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
