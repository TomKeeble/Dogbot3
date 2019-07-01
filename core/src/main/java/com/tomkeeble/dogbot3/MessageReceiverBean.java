package com.tomkeeble.dogbot3;

import com.tomkeeble.dogbot3.messages.Thread;
import org.apache.activemq.artemis.jms.client.compatible1X.ActiveMQBytesCompatibleMessage;
import org.jboss.ejb3.annotation.ResourceAdapter;
import org.jboss.logging.Logger;
import org.json.JSONObject;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import javax.persistence.Query;

@ResourceAdapter("dogbot_mq")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@MessageDriven(activationConfig =  {
//        @ActivationConfigProperty(propertyName = "acknowledgeMode",
//                propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destination",
                propertyValue = "msg_recv")
})
public class MessageReceiverBean implements MessageListener {

    private static Logger logger = Logger.getLogger(MessageReceiverBean.class);

    @Resource
    private MessageDrivenContext mdc;

    @Inject
    private MessageBean messageBean;


    @Override
    public void onMessage(Message message) {
        TextMessage msg = null;

        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                logger.info("MESSAGE BEAN: Message received: " +
                        msg.getText());
                messageBean.processMessage(msg.getText());
            } else if (message instanceof ActiveMQBytesCompatibleMessage){
                ActiveMQBytesCompatibleMessage msgb = (ActiveMQBytesCompatibleMessage) message;
                messageBean.processMessage(msgb.readUTF());
            }
            else {
                logger.warn("Message of wrong type: " +
                        message.getClass().getName());
            }
        } catch (JMSException e) {
            e.printStackTrace();
            mdc.setRollbackOnly();
        } catch (Throwable te) {
            te.printStackTrace();
        }
    }


}
