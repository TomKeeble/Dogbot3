package com.tomkeeble.dogbot3;

import com.tomkeeble.dogbot3.messages.Person;
import com.tomkeeble.dogbot3.messages.Thread;
import org.jboss.logging.Logger;

import javax.annotation.PostConstruct;
import javax.ejb.Singleton;
import javax.ejb.Startup;
import javax.enterprise.inject.Instance;
import javax.enterprise.inject.Produces;
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


    @Produces
    @PersistenceContext(unitName = "persistence")
    private static EntityManager entityManager;

    private final Set<Module> modules = new HashSet<>();

    private final Set<Class<? extends Module>> commands = new HashSet<>();

    @Inject
    private Instance<Module> discovered_modules;


    public Set<Class<? extends Module>> getCommands() {
        return commands;
    }

    @PostConstruct
    public void start(){
        System.out.print("Hello World");
        logger.info("Dogbot3");
        logger.info("Starting Dogbot3...");

//        logger.info(entityManager.find(Thread.class, Long.valueOf(1)).getName());
//        Person p = new Person();
//        p.setUserID("100026388580882");
//        p.setName("Dogbot");
//        entityManager.persist(p);
//        entityManager.flush();

        for (Module plugin : discovered_modules) {
            modules.add(plugin);
            logger.info("Found module: " + plugin.getClassName());

            commands.add(plugin.getClass());

        }

//        messageProvider = new FacebookMessageProvider();

    }

    public Set<Module> getModules(){
        return modules;
    }
}
