package com.tomkeeble.dogbot3;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tomkeeble.dogbot3.DTO.UpdateDTO;
import org.jboss.logging.Logger;

import javax.ejb.Stateful;
import javax.inject.Inject;
import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.TextMessage;
import java.sql.Date;
import java.time.Instant;

@Stateful
public class MessageProcessorBean {

    private static Logger logger = Logger.getLogger(MessageProcessorBean.class);

    @Inject
    private Dogbot3 dogbot3;

    @Inject
    MetaDataUpdaterBean metaDataUpdaterBean;

    @Inject
    MessageBean messageBean;

    @Inject
    CommandManagerBean commandManagerBean;

    public void handleMessage(Message message, boolean isLive) {
        TextMessage msg = null;

        try {
            if (message instanceof TextMessage) {
                msg = (TextMessage) message;

                if (message.getStringProperty("system").equals("true")){
                    ObjectMapper mapper = new ObjectMapper();
                    UpdateDTO update = mapper.readValue(msg.getText(), UpdateDTO.class);
                    logger.info(update.getUsers().get(1).getName());
                    metaDataUpdaterBean.doUpdate(update);
                    return;
                } else {

                    logger.info("MESSAGE BEAN: Message received: " +
                            msg.getText());
                    com.tomkeeble.dogbot3.messages.Message messageObj = messageBean.processMessage(msg.getText());
                    if (messageObj.getTimestamp().after(Date.from(Instant.now().minusSeconds(30)))) {
                        isLive = true;
                    }
                    if (isLive) {
                    callMessageHandlers(messageObj);
                    } else{
                        callDeadMessageHandlers(messageObj);
                    }
                }
            } else {
                logger.warn("Message of wrong type: " +
                        message.getClass().getName());
            }
        } catch (JMSException e) {
            e.printStackTrace();
//            mdc.setRollbackOnly();
        } catch (Throwable te) {
            te.printStackTrace();
        }
    }

    private void callMessageHandlers(com.tomkeeble.dogbot3.messages.Message message) {
        if (message == null){
            return;
        }
        callDeadMessageHandlers(message);
        if (message.getActor().getPerson().getId() == 669 || message.getActor().getPerson().getUserID().equals("100026388580882")) {
            return;
        }
//        commandManagerBean.processCommand(message);
        for (Module m: dogbot3.getModules()) {
            try {
                m.processMessage(message);
            } catch (Exception e) {
                logger.error("Error in module whilst processing message", e);
            }


        }
    }

    private void callDeadMessageHandlers(com.tomkeeble.dogbot3.messages.Message message) {
        for (Module m: dogbot3.getModules()) {
            try {
                m.processDeadMessage(message);
            } catch (Exception e) {
                logger.error("Error in module whilst processing dead message", e);
            }


        }
    }
}
