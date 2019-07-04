package com.tomkeeble.dogbot3.messageproviders.facebook;

import com.tomkeeble.dogbot3.Dogbot3;
import com.tomkeeble.dogbot3.MessageProvider;
import com.tomkeeble.dogbot3.messages.Thread;
import com.tomkeeble.dogbot3.messages.Message;
import org.jboss.ejb3.annotation.ResourceAdapter;
import org.jboss.jca.common.api.metadata.spec.Messageadapter;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.LocalBean;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.jms.*;

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
            context.createProducer().send(queue, "sendmessage: " + message.getMessage());




    }


}
