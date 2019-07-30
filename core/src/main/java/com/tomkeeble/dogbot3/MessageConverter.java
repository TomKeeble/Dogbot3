package com.tomkeeble.dogbot3;

import com.beust.jcommander.IStringConverter;
import com.tomkeeble.dogbot3.exceptions.MessageSelectionOutOfRangeException;
import com.tomkeeble.dogbot3.messageproviders.facebook.FacebookMessageProvider;
import com.tomkeeble.dogbot3.messages.Message;

import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

public class MessageConverter implements IStringConverter<Message> {

    @PersistenceContext(unitName = "persistence")
    EntityManager entityManager;

    @Inject
    CommandManagerBean commandManagerBean;

    @Inject
    FacebookMessageProvider msg_provider;

    @Override
    public Message convert(String value) {
        int n = 1;
        try {
            n = Integer.parseInt(value);
        } catch (Exception e) {
            commandManagerBean.getForMessage().getThread().sendMessage(msg_provider, new Message(e.getMessage()));
        }

        try {
            return commandManagerBean.getForMessage().getThread().getNMessagesBack(entityManager, n);
        } catch (MessageSelectionOutOfRangeException e) {
            commandManagerBean.getForMessage().getThread().sendMessage(msg_provider, new Message(e.getMessage()));
        }

        return new Message("Null Message");
    }
}
