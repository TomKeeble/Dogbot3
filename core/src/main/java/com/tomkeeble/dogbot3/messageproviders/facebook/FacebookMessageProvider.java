package com.tomkeeble.dogbot3.messageproviders.facebook;

import com.tomkeeble.dogbot3.MessageProvider;
import com.tomkeeble.dogbot3.messages.Message;
import com.tomkeeble.dogbot3.messages.Thread;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.JMSConnectionFactory;
import javax.jms.JMSContext;
import javax.jms.JMSException;
import javax.jms.Queue;

@LocalBean
@Stateless
//@ResourceAdapter("dogbot_mq")
//@JMSConnectionFactory("java:/jms/dogbot_mq")
public class FacebookMessageProvider implements MessageProvider {

    private static Logger logger = Logger.getLogger(FacebookMessageProvider.class);

    @Resource(mappedName = "java:jboss/exported/jms/queue/msg_send")
    private Queue queue;

    @Inject
    @JMSConnectionFactory("java:/jms/dogbot_mq")
    JMSContext context;

    @Override
    public void sendMessage(Thread thread, Message message) {
        //TODO implement this
        logger.info("sendmessage: " + message.getMessage());

//        context.createQueue("msg_send")
        javax.jms.Message msg = context.createTextMessage(message.getMessage());
        try {
            logger.info("GroupID: " + thread.getGroupId());
            msg.setStringProperty("g_id", thread.getGroupId());
        } catch (JMSException e) {
            e.printStackTrace();
        }
        context.createProducer().send(queue, msg);




    }


}
