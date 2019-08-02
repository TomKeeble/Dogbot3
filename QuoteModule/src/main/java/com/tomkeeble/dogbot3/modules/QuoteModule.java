package com.tomkeeble.dogbot3.modules;

import com.tomkeeble.dogbot3.Command;
import com.tomkeeble.dogbot3.Dogbot3;
import com.tomkeeble.dogbot3.Module;
import com.tomkeeble.dogbot3.messageproviders.facebook.FacebookMessageProvider;
import com.tomkeeble.dogbot3.messages.Message;
import com.tomkeeble.dogbot3.messages.Thread;
import com.tomkeeble.dogbot3.modules.model.Quote;

import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Stateless
@Named("QuoteModule")
public class QuoteModule implements Module {

    @PersistenceContext(unitName = "persistence")
    EntityManager entityManager;

    @Inject
    FacebookMessageProvider msg_provider;

    @Override
    public String getClassName() {
        return QuoteModule.class.getName();
    }

    @Override
    public void processMessage(Message message) {

    }

    @Override
    public void processDeadMessage(Message message) {
//        System.out.println("Message!");
        if(message.getMessage() == null) {
            return;
        }
//        message.getThread().sendMessage(msg_provider, new Message("Hello World from " + this.getClassName()));
        if (message.getMessage().matches(".*\"(.*)\" *-+ *(.*) *\\(.*\\).*")) {
            String quote=message.getMessage().replaceAll(".*(\".*\" *-+ *.* *\\(.*\\)).*","$1");

            Pattern pattern = Pattern.compile(".*\"(.*)\" *-+ *(.*) *\\(.*\\).*");
            Matcher matcher = pattern.matcher(message.getMessage());

            Quote q = new Quote();
            matcher.find();
            q.setQuote(matcher.group(1));
            q.setTarget(matcher.group(2));
            q.setSender(message.getActor());

            entityManager.persist(q);
            entityManager.flush();
//            message.getThread().sendMessage(msg_provider, new Message(quote));
        }
    }

    @Override
    public List<Command> getCommands() {
        return null;
    }


}
