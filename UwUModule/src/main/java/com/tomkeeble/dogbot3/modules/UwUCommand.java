package com.tomkeeble.dogbot3.modules;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;
import com.tomkeeble.dogbot3.CommandManagerBean;
import com.tomkeeble.dogbot3.Commands.Argument;
import com.tomkeeble.dogbot3.Commands.Command;
import com.tomkeeble.dogbot3.Commands.MessageContext;
import com.tomkeeble.dogbot3.MessageConverter;
import com.tomkeeble.dogbot3.MessageConverterFactory;
import com.tomkeeble.dogbot3.exceptions.MessageSelectionOutOfRangeException;
import com.tomkeeble.dogbot3.messageproviders.facebook.FacebookMessageProvider;
import com.tomkeeble.dogbot3.messages.Message;
import org.jboss.logging.Logger;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.Random;

public class UwUCommand {

    private static Logger logger = Logger.getLogger(UwUCommand.class);

    @PersistenceContext(unitName = "persistence")
    EntityManager entityManager;

    @Inject
    FacebookMessageProvider msg_provider;

//    @Command(name="uwu")
//    public void uwu(@MessageContext Message command, @Argument Message target){
//
//        logger.info("running uwu command");
//        Message message = null;
//        try {
//            message = command.getThread().getNMessagesBack(entityManager, message_id);
//        } catch (MessageSelectionOutOfRangeException e) {
//            message.getThread().sendMessage(msg_provider, new Message(e.getMessage()));
//                return;
//        }
//        logger.info("target message: " + message.getMessage());
//
//        String uwuText = message.getMessage();
//
//        uwuText=uwuText.replaceAll("[rl]","w");
//        uwuText=uwuText.replaceAll("[RL]","W");
//        uwuText=uwuText.replaceAll("(?i)youw","ur");
//        uwuText=uwuText.replaceAll("(?i)you","u");
//        uwuText=uwuText.replaceAll("(?i)awe(?![a-z])","r");
//        uwuText=uwuText.replaceAll("(?i)ove","uv");
//        uwuText=uwuText.replaceAll("(?i)(n)([aeiou])","$1y$2");
//
//        if(faced) {
//            String[] faces={":3","(^³^)","uwu","OwO",":////",";-;;;",">.<"};
//            int facecount=Math.max(1,(int)(Math.round(uwuText.length()/10)*Math.random()));
//            for(int i=facecount;i>0; i--){
//                int pivot=new Random().nextInt(uwuText.length());
//                uwuText=uwuText.substring(0,pivot)+(uwuText.substring(pivot)+" ").replaceFirst(" "," "+faces[new Random().nextInt(faces.length)]+" ");
//            }
//        }
//
//        message.getThread().sendMessage(msg_provider, new Message(uwuText));
//
//
//    }
//
//    @Override
//    public String getCommand() {
//        return "uwu";
//    }
}
