package com.tomkeeble.dogbot3;

import com.tomkeeble.dogbot3.messageproviders.facebook.FacebookMessageProvider;
import org.jboss.ejb3.annotation.ResourceAdapter;
import org.jboss.logging.Logger;

import javax.annotation.Resource;
import javax.ejb.*;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.MessageListener;
import javax.jms.TextMessage;

@ResourceAdapter("dogbot_mq")
@TransactionAttribute(TransactionAttributeType.REQUIRED)
@MessageDriven(activationConfig =  {
        @ActivationConfigProperty(propertyName = "acknowledgeMode",
                propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destination",
                propertyValue = "msg_recv"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "false"),
})
public class MessageReceiverBean implements MessageListener {

    private static Logger logger = Logger.getLogger(MessageReceiverBean.class);

    @Resource
    private MessageDrivenContext mdc;

    @Inject
    private MessageBean messageBean;

    @Inject
    FacebookMessageProvider msg_provider;

    @Inject
    private Dogbot3 dogbot3;


    @Override
    public void onMessage(Message message) {
        TextMessage msg = null;

        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;
                logger.info("MESSAGE BEAN: Message received: " +
                        msg.getText());
                callMessageHandlers(messageBean.processMessage(msg.getText()));
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

    private void callMessageHandlers(com.tomkeeble.dogbot3.messages.Message message) {
        if (message == null){
            return;
        }
        for (Module m: dogbot3.getModules()) {
            try {
                m.processMessage(message);
            } catch (Exception e) {
                logger.error("Error in module whilst processing message", e);
            }


        }
    }


}
