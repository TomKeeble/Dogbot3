package com.tomkeeble.dogbot3;

import com.tomkeeble.dogbot3.messages.Actor;
import com.tomkeeble.dogbot3.messages.Message;
import com.tomkeeble.dogbot3.messages.Person;
import com.tomkeeble.dogbot3.messages.Thread;
import org.jboss.logging.Logger;
import org.json.JSONObject;

import javax.ejb.Stateful;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;
import java.util.List;

@Stateful
public class MessageBean {

    private static Logger logger = Logger.getLogger(MessageBean.class);

    @PersistenceContext(unitName = "persistence", type = PersistenceContextType.EXTENDED)
    private EntityManager entityManager;

    public Message processMessage(String msgbody){
        if (msgbody == null){
            logger.warn("Null message");
            return null;
        }
        JSONObject obj = new JSONObject(msgbody);
        if (obj.isNull("message")){
            logger.warn("Null message");
            return null;
        }

        com.tomkeeble.dogbot3.messages.Message msg = new com.tomkeeble.dogbot3.messages.Message(obj.getString("message"));

        Thread group = Thread.getGroupByFid(entityManager,obj.getString("thread_id"));
        logger.info(group.getName());
        msg.setThread(group);
        msg.setActor(Thread.getActorInGroup(entityManager, obj.getString("user_id"), group));
        entityManager.persist(msg);
        entityManager.flush();

        return msg;
    }







}
