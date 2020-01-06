package com.tomkeeble.dogbot3.modules;

import com.tomkeeble.dogbot3.Command;
import com.tomkeeble.dogbot3.Dogbot3;
import com.tomkeeble.dogbot3.Module;
import com.tomkeeble.dogbot3.messageproviders.facebook.FacebookMessageProvider;
import com.tomkeeble.dogbot3.messages.Actor;
import com.tomkeeble.dogbot3.messages.Message;

import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import java.util.*;

@Stateless
@Named("ExampleModule")
public class ExampleModule implements Module {


    @PersistenceContext(unitName = "persistence")
    EntityManager entityManager;

    @Inject
    FacebookMessageProvider msg_provider;

    @Override
    public String getClassName() {
        return ExampleModule.class.getName();
    }

    @Override
    public void processMessage(Message message) {
//        System.out.println("Message!");
//        message.getThread().sendMessage(msg_provider, new Message("Hello World from " + this.getClassName()));
        if (message != null && message.getMessage().startsWith("!goodboi")) {
            message.getThread().sendMessage(msg_provider, new Message(message.getActor().getNickname() + " is a goodboi 😊"));
        } else if (message.getMessage().startsWith("!leaderboard")) {
            StringBuilder sb = new StringBuilder();
            sb.append("Leaderboard\n");
            HashMap<Actor, Long> leaderboard = new HashMap<>();
            for (Actor actor:message.getThread().getActors()) {
                Query q = entityManager.createQuery("select count(*) from Message M where M.actor=:actor");
                q.setParameter("actor", actor);
                Long count = (Long)q.getSingleResult();
                leaderboard.put(actor, count);

            }

//            LinkedHashMap<Actor, Long> sortedLeaderboard = new LinkedHashMap<>();
            leaderboard.entrySet().stream().sorted(Map.Entry.comparingByValue(Comparator.reverseOrder())).limit(15).forEach(actorLongEntry -> sb.append(actorLongEntry.getKey().getNickname() + ": " + actorLongEntry.getValue().toString() + "\n"));

//            output = output + actor.getNickname() + ": " + count.toString() + "\n";
            message.getThread().sendMessage(msg_provider, new Message(sb.toString()));
        }

    }

    @Override
    public void processDeadMessage(Message message) {
        //do nothing
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }

}
