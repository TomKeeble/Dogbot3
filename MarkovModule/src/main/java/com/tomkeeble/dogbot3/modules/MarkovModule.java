package com.tomkeeble.dogbot3.modules;

import com.tomkeeble.dogbot3.Dogbot3;
import com.tomkeeble.dogbot3.Module;
import com.tomkeeble.dogbot3.exceptions.MessageSelectionOutOfRangeException;
import com.tomkeeble.dogbot3.messageproviders.facebook.FacebookMessageProvider;
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
        System.out.println("Message!");
//        message.getThread().sendMessage(msg_provider, new Message("Hello World from " + this.getClassName()));
        if (message.getMessage().toLowerCase().startsWith("!simulate")) {
            Person sender = message.getActor().getPerson();
            List<String> history = new ArrayList<String>();
            for(Message pastMessage:message.getThread().getMessages()){
                if(pastMessage.getActor().getPerson().equals(sender)){
                    if(!pastMessage.getMessage().startsWith("!")) {
                        history.add(pastMessage.getMessage());
                    }
                }
            }
            message.getThread().sendMessage(msg_provider, new Message(new Text(history).make_sentence()));
        }
    }

    @Override
    public void processHistoricMessage(Message message) {
        //Do nothing
    }
}