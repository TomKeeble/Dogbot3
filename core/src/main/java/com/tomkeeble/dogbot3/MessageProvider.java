package com.tomkeeble.dogbot3;

import com.tomkeeble.dogbot3.messages.Thread;
import com.tomkeeble.dogbot3.messages.Message;

public interface MessageProvider {

    public void sendMessage(Thread thread, Message message);
}
