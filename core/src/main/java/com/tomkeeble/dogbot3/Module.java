package com.tomkeeble.dogbot3;

import com.tomkeeble.dogbot3.messages.Message;

import java.util.List;

public interface Module {

    public String getClassName();

    public void processMessage(Message message);

    public void processDeadMessage(Message message);

    public List<Command> getCommands();
}
