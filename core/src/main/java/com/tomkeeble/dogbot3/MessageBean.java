package com.tomkeeble.dogbot3;

import org.jboss.ejb3.annotation.ResourceAdapter;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.MessageDrivenContext;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@ResourceAdapter("dogbot_mq")
@MessageDriven(activationConfig =  {
        @ActivationConfigProperty(propertyName = "acknowledgeMode",
                propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destination",
                propertyValue = "msg_recv")
})
public class MessageBean implements MessageListener {

    private static Logger logger = Logger.getLogger(MessageBean.class);

    @Resource
    private MessageDrivenContext mdc;

    @Override
    public void onMessage(Message message) {
        TextMessage msg = null;

        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                logger.info("MESSAGE BEAN: Message received: " +
                        msg.getText());
            } else {
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
