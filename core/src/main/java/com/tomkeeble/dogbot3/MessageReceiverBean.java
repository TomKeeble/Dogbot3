package com.tomkeeble.dogbot3;

import org.jboss.ejb3.annotation.ResourceAdapter;
import org.jboss.logging.Logger;

import javax.ejb.ActivationConfigProperty;
import javax.ejb.MessageDriven;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;
import javax.jms.Message;
import javax.jms.MessageListener;

@ResourceAdapter("dogbot_mq")
//@TransactionAttribute(TransactionAttributeType.REQUIRED)
@TransactionAttribute(value = TransactionAttributeType.NOT_SUPPORTED)
@MessageDriven(activationConfig = {
        @ActivationConfigProperty(propertyName = "acknowledgeMode",
                propertyValue = "Auto-acknowledge"),
        @ActivationConfigProperty(propertyName = "destination",
                propertyValue = "msg_recv"),
        @ActivationConfigProperty(propertyName = "destinationType", propertyValue = "javax.jms.Queue"),
        @ActivationConfigProperty(propertyName = "useJNDI", propertyValue = "false"),
})
public class MessageReceiverBean implements MessageListener {

    private static Logger logger = Logger.getLogger(MessageReceiverBean.class);

    @Inject
    MessageProcessorBean mpb;


    @Override
    public void onMessage(Message message) {
        logger.info("Live MEssage");
        mpb.handleMessage(message, true);

    }


}
