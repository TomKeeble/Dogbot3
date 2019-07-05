package com.tomkeeble.dogbot3.modules;

import com.tomkeeble.dogbot3.Dogbot3;
import com.tomkeeble.dogbot3.Module;
import com.tomkeeble.dogbot3.messageproviders.facebook.FacebookMessageProvider;
import com.tomkeeble.dogbot3.messages.Message;
import com.tomkeeble.dogbot3.messages.Thread;

import javax.ejb.EJB;
import javax.ejb.Startup;
import javax.ejb.Stateful;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.inject.Named;
import java.util.List;

@Stateless
@Named("UwUModule")
public class UwUModule implements Module {

    @Inject
    FacebookMessageProvider msg_provider;

    @Override
    public String getClassName() {
        return UwUModule.class.getName();
    }

    @Override
    public void processMessage(Message message) {
        System.out.println("Message!");
//        message.getThread().sendMessage(msg_provider, new Message("Hello World from " + this.getClassName()));
        if (message.getMessage().toLowerCase().contains("!uwu")) {
            List<Message> messages=message.getThread().getMessages();
            String uwuText;
            try {
                //uwuText=messages.get(messages.size()-2).getMessage();
                uwuText=messages.get(1).getMessage();
            }
            catch (Exception ex){
                uwuText=message.getMessage() + " (" + ex.getMessage() + ")";
            }
            uwuText=uwuText.replaceAll("[rl]","w");
            uwuText=uwuText.replaceAll("[RL]","W");
            uwuText=uwuText.replaceAll("(?i)youw","ur");
            uwuText=uwuText.replaceAll("(?i)you","u");
            uwuText=uwuText.replaceAll("(?i)awe ","r ");
            uwuText=uwuText.replaceAll("(?i)ove","uv");
            uwuText=uwuText.replaceAll("(?i)(n)([aeiou])","$1y$2");
            message.getThread().sendMessage(msg_provider, new Message(uwuText));
        }
    }

    @Override
    public void processHistoricMessage(Message message) {
        //Do nothing
    }
}
