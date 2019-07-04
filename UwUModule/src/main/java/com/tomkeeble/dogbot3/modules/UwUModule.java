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
                uwuText=messages.get(messages.size()-2).getMessage();
            }
            catch (Exception ex){
                uwuText=message.getMessage();
            }
            uwuText=uwuText.replaceAll("/[rl]/gi","w");
            uwuText=uwuText.replaceAll("/youw/gi","ur");
            uwuText=uwuText.replaceAll("/you/gi","u");
            uwuText=uwuText.replaceAll("/awe /gi","r ");
            uwuText=uwuText.replaceAll("/ove/gi","uv");
            uwuText=uwuText.replaceAll("/(n)([aeiou])/gi","$1y$2");
            message.getThread().sendMessage(msg_provider, new Message(uwuText));
        }
    }

    @Override
    public void processHistoricMessage(Message message) {
        //Do nothing
    }
}
