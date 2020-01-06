package com.tomkeeble.dogbot3.modules;

import com.tomkeeble.dogbot3.Command;
import com.tomkeeble.dogbot3.Dogbot3;
import com.tomkeeble.dogbot3.Module;
import com.tomkeeble.dogbot3.exceptions.MessageSelectionOutOfRangeException;
import com.tomkeeble.dogbot3.messageproviders.facebook.FacebookMessageProvider;
import com.tomkeeble.dogbot3.messages.Actor;
import com.tomkeeble.dogbot3.messages.Message;
import com.tomkeeble.dogbot3.messages.Person;
import com.tomkeeble.dogbot3.messages.Thread;

import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Root;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Stateful
@Named("MarkovModule")
public class MarkovModule implements Module {

    @PersistenceContext(unitName = "persistence")
    EntityManager entityManager;

    @Inject
    FacebookMessageProvider msg_provider;

    @Override
    public String getClassName() {
        return MarkovModule.class.getName();
    }

    @Override
    public void processMessage(Message message) {
//        System.out.println("Message!");
//        message.getThread().sendMessage(msg_provider, new Message("Hello World from " + this.getClassName()));
        if (message.getMessage() != null && message.getMessage().toLowerCase().startsWith("!simulate")) {
            Person subject = message.getActor().getPerson();
            Actor a;
            if (message.getMessage().length()>10) {
                a = message.getThread().searchActorInThread(entityManager, message.getMessage().substring(10));
                if (a != null) {
                    subject = a.getPerson();
                }
            }


            List<String> history = new ArrayList<String>();
            for(Message pastMessage:message.getThread().getAllMessages(entityManager)){
//                if(pastMessage.getActor().getPerson().equals(sender)){
                if(pastMessage.getActor().getPerson().equals(subject)){
                    if(pastMessage.getMessage() != null && !pastMessage.getMessage().startsWith("!")) {
                        history.add(pastMessage.getMessage());
                    }
                }
            }
            String newtext;
            try {
                newtext = new Text(history).make_sentence();
            } catch (Exception e) { //TODO fix this
                newtext = "Error: " + e.getMessage();
            }
            if(newtext.equals("Not enough source data.")){
                newtext=newtext+" ("+history.size()+" messages)";
            }
            message.getThread().sendMessage(msg_provider, new Message(newtext));
        }
    }

    @Override
    public void processDeadMessage(Message message) {
        //Do nothing
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }
}
