package com.tomkeeble.dogbot3;

import com.tomkeeble.dogbot3.messages.Thread;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Instance;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.HashSet;
import java.util.Set;

@Singleton
@Startup
public class Dogbot3 {

    private static Logger logger = Logger.getLogger(Dogbot3.class);


    public static MessageProvider getMessageProvider() {
        return messageProvider;
    }

    private static MessageProvider messageProvider;


    @PersistenceContext(unitName = "persistence")
    private EntityManager entityManager;

    private final Set<Module> modules = new HashSet<>();

    @Inject
    private Instance<Module> discovered_modules;


    @PostConstruct
    public void start(){
        System.out.print("Hello World");
        logger.info("Dogbot3");
        logger.info("Starting Dogbot3...");

        logger.info(entityManager.find(Thread.class, Long.valueOf(1)).getName());

        for (Module plugin : discovered_modules) {
            modules.add(plugin);
            logger.info("Found module: " + plugin.getClassName());
        }

//        messageProvider = new FacebookMessageProvider();

    }

    public Set<Module> getModules(){
        return modules;
    }
}
