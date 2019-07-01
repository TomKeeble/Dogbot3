package com.tomkeeble.dogbot3;

import com.tomkeeble.dogbot3.messages.Person;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Hibernate;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) {

        logger.info("creating persistance manager");

        EntityManagerFactory entityManagerFactory = Persistence.createEntityManagerFactory("persistence");
        EntityManager entityManager = entityManagerFactory.createEntityManager();

//        Configuration config = new Configuration();
//        SessionFactory sessionFactory = config.configure().buildSessionFactory();


        Dogbot3 dogbot = new Dogbot3(entityManager);
        dogbot.start();

    }
}
