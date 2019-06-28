package com.tomkeeble.dogbot3;

import com.tomkeeble.dogbot3.messages.Group;
import com.tomkeeble.dogbot3.messages.Message;

public interface MessageProvider {

    public void sendMessage(Group group, Message message);
}
