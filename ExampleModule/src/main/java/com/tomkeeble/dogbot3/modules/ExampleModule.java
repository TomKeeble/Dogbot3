package com.tomkeeble.dogbot3.modules;

import com.tomkeeble.dogbot3.Dogbot3;
import com.tomkeeble.dogbot3.Module;
import com.tomkeeble.dogbot3.messageproviders.facebook.FacebookMessageProvider;
import com.tomkeeble.dogbot3.messages.Message;

import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;

@Stateless
@Named("ExampleModule")
public class ExampleModule implements Module {

    @Inject
    FacebookMessageProvider msg_provider;

    @Override
    public String getClassName() {
        return ExampleModule.class.getName();
    }

    @Override
    public void processMessage(Message message) {
        System.out.println("Message!");
//        message.getThread().sendMessage(msg_provider, new Message("Hello World from " + this.getClassName()));
        if (message.getMessage().startsWith("!goodboi")) {
            message.getThread().sendMessage(msg_provider, new Message(message.getActor().getNickname() + " is a goodboi 😊"));
        }
    }

    @Override
    public void processHistoricMessage(Message message) {
        //Do nothing
    }
}
