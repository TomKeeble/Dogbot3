package com.tomkeeble.dogbot3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomkeeble.dogbot3.DTO.MessageDTO;
import com.tomkeeble.dogbot3.DTO.UpdateDTO;
import com.tomkeeble.dogbot3.exceptions.MessageDoesNotExistException;
import com.tomkeeble.dogbot3.messageproviders.facebook.FacebookMessageProvider;
import com.tomkeeble.dogbot3.messages.Actor;
import com.tomkeeble.dogbot3.messages.Message;
import com.tomkeeble.dogbot3.messages.Person;
import com.tomkeeble.dogbot3.messages.Thread;
import org.hibernate.SessionFactory;
import org.jboss.logging.Logger;
import org.json.JSONObject;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.io.IOException;
import java.time.Instant;
import java.util.Date;
import java.util.List;

@Stateful
public class MessageBean {

    private static Logger logger = Logger.getLogger(MessageBean.class);

    @Inject
    private EntityManager entityManager;

    @Inject
    FacebookMessageProvider messageProvider;


    public Message processMessage(String msgbody) {
        try {
            if (msgbody == null) {
                logger.warn("Null message");
                return null;
            }

            ObjectMapper mapper = new ObjectMapper();
            MessageDTO messageDTO;
            try {
                messageDTO = mapper.readValue(msgbody, MessageDTO.class);
            } catch (IOException e) {
                e.printStackTrace();
                logger.warn("Null message");
                return null;
            }

            com.tomkeeble.dogbot3.messages.Message msg = new com.tomkeeble.dogbot3.messages.Message(messageDTO.getMessage());

            Thread group = Thread.getGroupByFid(entityManager, messageDTO.getThread_id());
//            logger.info(group.getName());
            msg.setThread(group);
            msg.setActor(Thread.getActorInGroup(entityManager, messageDTO.getUser_id(), group));
            msg.setId(messageDTO.getMsg_id());
            msg.setTimestamp(Date.from(Instant.ofEpochMilli(Long.parseLong(messageDTO.getTimestamp()))));

            if (messageDTO.getReplied_to() != null) {
                try {
                    msg.setReplied_to(Message.getMessageByFid(entityManager, messageDTO.getReplied_to()));
                } catch (MessageDoesNotExistException e) {
//                msg.getThread().sendMessage(messageProvider, new Message("Error whilst processing message: " + e.toString()));
//                e.printStackTrace();
                }
            }

            entityManager.persist(msg);
            entityManager.flush();

            return msg;
        } catch (Exception e) {
            logger.error("Error in Messagebean", e);
            return new Message("Error");
        }
    }

    public void deleteMessage(String mid) {
        try {
            logger.info("Deleting message");
            Message m = Message.getMessageByFid(entityManager, mid);
            m.deleteMessage();

            entityManager.getEntityManagerFactory().getCache().evict(Message.class, mid);

            entityManager.flush();
        } catch (MessageDoesNotExistException e) {
            e.printStackTrace();
            logger.error("Message marked for deletion does not exist.");
        }
    }





}
