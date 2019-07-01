package com.tomkeeble.dogbot3.messageproviders.facebook;

import com.tomkeeble.dogbot3.MessageProvider;
import com.tomkeeble.dogbot3.messages.Thread;
import com.tomkeeble.dogbot3.messages.Message;

public class FacebookMessageProvider implements MessageProvider {
    @Override
    public void sendMessage(Thread thread, Message message) {
        //TODO implement this
        System.out.println("sendmessage");
    }
}
