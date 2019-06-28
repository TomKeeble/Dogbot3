package com.tomkeeble.dogbot3;

import com.rabbitmq.client.ConnectionFactory;
import com.tomkeeble.dogbot3.messageproviders.facebook.FacebookMessageProvider;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.persistence.EntityManager;

public class Dogbot3 {

    private static final Logger logger = LogManager.getLogger(Main.class);


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

    public Dogbot3(EntityManager em) {
        entityManager = em;
        messageProvider = new FacebookMessageProvider();
    }

    public void start(){
        logger.info("Dogbot3");
        logger.info("Starting Dogbot3...");
    }
}
