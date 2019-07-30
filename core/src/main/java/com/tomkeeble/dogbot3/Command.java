package com.tomkeeble.dogbot3;

import com.tomkeeble.dogbot3.messages.Message;

public interface Command {

    public void run(Message command_message);

    public String getCommand();
}
