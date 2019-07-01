package com.tomkeeble.dogbot3;

import com.rabbitmq.client.DeliverCallback;
import com.rabbitmq.client.Delivery;
import com.tomkeeble.dogbot3.messages.Thread;
import com.tomkeeble.dogbot3.messages.Message;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONObject;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.io.IOException;

public class MessageReciever implements DeliverCallback {

    private static final Logger logger = LogManager.getLogger(MessageReciever.class);

    public static Thread getGroupByFid(String fid) {
        EntityManager session = Dogbot3.getEntityManager();
        Query q = session.createQuery("FROM Group G WHERE G.id = " + fid);
        int result = q.getFirstResult();
//        q.setParameter("name", fid);

        if (result != 0) {
            return session.find(Thread.class, result);
        }
        logger.info(q.getFirstResult());
        return null;
    }

    @Override
    public void handle(String consumerTag, Delivery message) throws IOException {
        String msgbody = new String(message.getBody(), "UTF-8");
        logger.info(msgbody);
        JSONObject obj = new JSONObject(msgbody);



//        Dogbot3.getEntityManager().getTransaction().begin();
//        Dogbot3.getEntityManager();


        Message msg = new Message(obj.getString("message"));
        logger.info(getGroupByFid(obj.getString("thread_id")).getName());
//        msg.setActor();

//        Dogbot3.getEntityManager().getTransaction().commit();


    }
}
