package com.tomkeeble.dogbot3;

import com.tomkeeble.dogbot3.messages.Message;
import com.tomkeeble.dogbot3.messages.Thread;

public interface MessageProvider {

    public void sendMessage(Thread thread, Message message);
}
