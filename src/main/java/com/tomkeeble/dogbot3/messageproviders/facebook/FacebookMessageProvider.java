package com.tomkeeble.dogbot3.messageproviders.facebook;

import com.tomkeeble.dogbot3.MessageProvider;
import com.tomkeeble.dogbot3.messages.Group;
import com.tomkeeble.dogbot3.messages.Message;

public class FacebookMessageProvider implements MessageProvider {
    @Override
    public void sendMessage(Group group, Message message) {
        //TODO implement this
        System.out.println("sendmessage");
    }
}
