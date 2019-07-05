package com.tomkeeble.dogbot3.modules;

import com.tomkeeble.dogbot3.Dogbot3;
import com.tomkeeble.dogbot3.Module;
import com.tomkeeble.dogbot3.exceptions.MessageSelectionOutOfRangeException;
import com.tomkeeble.dogbot3.messageproviders.facebook.FacebookMessageProvider;
import com.tomkeeble.dogbot3.messages.Message;
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
import java.util.List;
import java.util.Random;

@Stateful
@Named("UwUModule")
public class UwUModule implements Module {

    @PersistenceContext(unitName = "persistence")
    EntityManager entityManager;

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
        if (message.getMessage().toLowerCase().startsWith("!uwu")) {
            String uwuText = message.getMessage().toLowerCase();
            int n = 1;
            boolean faced=uwuText.matches(".*:3.*");
            if(uwuText.matches("!uwu(:3)? *[+-]?[0-9]+.*")) {
                try {
                    n = Integer.parseInt(uwuText.replaceAll("!uwu(:3)? *([+-]?[0-9]+).*","$2"));
                } catch (Exception e) {
                    message.getThread().sendMessage(msg_provider, new Message(e.getMessage()));
                }
            }
            if (n<=0) {
                n = 1;
            }
            try {
                uwuText = message.getThread().getNMessagesBack(entityManager, n).getMessage();
            } catch (MessageSelectionOutOfRangeException e) {
                message.getThread().sendMessage(msg_provider, new Message(e.getMessage()));
                return;
            }
            uwuText=uwuText.replaceAll("[rl]","w");
            uwuText=uwuText.replaceAll("[RL]","W");
            uwuText=uwuText.replaceAll("(?i)youw","ur");
            uwuText=uwuText.replaceAll("(?i)you","u");
            uwuText=uwuText.replaceAll("(?i)awe(?![a-z])","r");
            uwuText=uwuText.replaceAll("(?i)ove","uv");
            uwuText=uwuText.replaceAll("(?i)(n)([aeiou])","$1y$2");

            if(faced) {
                String[] faces={":3","(^³^)","uwu","OwO",":////",";-;;;",">.<"};
                int facecount=Math.max(1,(int)(Math.round(uwuText.length()/10)*Math.random()));
                for(int i=facecount;i>0; i--){
                    int pivot=new Random().nextInt(uwuText.length());
                    uwuText=uwuText.substring(0,pivot)+(uwuText.substring(pivot)+" ").replaceFirst(" "," "+faces[new Random().nextInt(faces.length)]+" ");
                }
            }

            message.getThread().sendMessage(msg_provider, new Message(uwuText));
        }
    }

    @Override
    public void processHistoricMessage(Message message) {
        //Do nothing
    }
}
